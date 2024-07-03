package com.glos.api.operationservice;

import com.glos.api.operationservice.exception.ExecutionOperationException;
import com.glos.api.operationservice.exception.InvalidOperationDataPropertiesException;
import com.glos.api.operationservice.exception.OperationExpiredException;
import com.glos.api.operationservice.exception.OperationNotFoundException;
import com.glos.api.operationservice.repository.OperationRepository;
import com.glos.api.operationservice.util.VerificationCodeGenerator;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class OperationService {

    private final VerificationCodeGenerator codeGenerator = VerificationCodeGenerator.getInstance();
    private final OperationExecutor executor = OperationExecutor.getInstance();
    private final NotificationService notificationService;
    private final OperationRepository operationRepository;

    public OperationService(
            NotificationService notificationService,
            OperationRepository operationRepository
    ) {
        this.notificationService = notificationService;
        this.operationRepository = operationRepository;
    }

    public Operation create(String action, Map<String, String> data, Integer expired) {
        collectAllExpired();

        if (expired == null) {
            expired = Operations.DEFAULT_EXPIRED_SECONDS;
        }

        Action actionObj = Action.valueOfIgnoreCase(action);

        if (!actionObj.checkProperties(data, "code")) {
            throw new InvalidOperationDataPropertiesException("Invalid data properties");
        }

        String user = data.get("username");
        Optional<Operation> operationOpt = operationRepository.findByUsernameAndAction(user, action);
        operationOpt.ifPresent(operationRepository::delete);

        final LocalDateTime now = LocalDateTime.now();
        Operation operation = new Operation();
        operation.setId(UUID.randomUUID());
        operation.setCode(codeGenerator.generateVerificationCode());
        operation.setAction(action);
        operation.setData(data);
        operation.setCreatedDatetime(now);
        operation.setExpiredDatetime(now.plusSeconds(expired));
        operation.getData().put("code", operation.getCode());

        operationRepository.save(operation);

        sendMessage(data.get("email"), operation);

        return operation;
    }

    private void sendMessage(String email, Operation operation) {
        final Action action = Action.valueOfIgnoreCase(operation.getAction());
        final Map<String, String> data = operation.getData();
        notificationService.send(email, action, data);
    }

    public boolean execute(Operation operation) {
        return execute(operation.getCode());
    }

    public boolean execute(String code) {
        collectAllExpired();
        validateVerificationCode(code);
        Optional<Operation> operationOpt = operationRepository.findByCode(code);

        Operation operation = operationOpt.orElseThrow(
                () -> new OperationNotFoundException("Operation not found", code)
        );

        if (operation.isExpired()) {
            final int expiredSeconds = getSecondsExpiredOperation(operation);
            throw new OperationExpiredException(String.format(
                    "Operation %s is expired %d seconds", code, expiredSeconds
            ), operation);
        }
        if (!executor.execute(operation)) {
            throw new ExecutionOperationException("Operation was not complate", code);
        }
        operation.setExpiredDatetime(LocalDateTime.now());
        operationRepository.save(operation);
        return true;
    }

    private void validateVerificationCode(String code) {

    }

    private void collectAllExpired() {
        List<Operation> operationsExpired = operationRepository.findAll().stream()
                .filter(x -> x.isExpired() &&
                        getSecondsExpiredOperation(x) > Operations.DEFAULT_EXPIRED_SECONDS)
                .toList();
        if (!operationsExpired.isEmpty()) {
            operationRepository.deleteAll(operationsExpired);
        }
    }

    private int getSecondsExpiredOperation(Operation operation) {
        if (!operation.isExpired())
            return 0;
        final int nowSeconds = LocalDateTime.now().getSecond();
        final int expiredDateSecond = operation.getExpiredDatetime().getSecond();
        return nowSeconds - expiredDateSecond;
    }

    private void validateDataOperation(String action, Map<String, String> data) {
        try {
            Action actions = Action.valueOf(action);
            Set<String> properties = actions.getProperties();
            properties.forEach(x -> {
                if (!data.containsKey(x)) {
                    throw new RuntimeException(String.format("required property %s not found", x));
                }
            });
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(String.format("action %s not found", action));
        }
    }

}
