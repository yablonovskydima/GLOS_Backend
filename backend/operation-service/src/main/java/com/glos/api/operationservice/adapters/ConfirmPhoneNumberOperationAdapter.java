package com.glos.api.operationservice.adapters;

import com.glos.api.operationservice.Action;
import com.glos.api.operationservice.Operation;
import com.glos.api.operationservice.OperationAdapterRegister;
import com.glos.api.operationservice.client.UserAPIClient;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.sql.SQLOutput;
import java.util.Map;

@Component
public class ConfirmPhoneNumberOperationAdapter extends AbstractOperationAdapter {

    private final UserAPIClient userClient;

    public ConfirmPhoneNumberOperationAdapter(UserAPIClient userClient) {
        this.userClient = userClient;
    }

    @PostConstruct
    public void init() {
        OperationAdapterRegister.appendAdapter(Action.CONFIRM_PHONE_NUMBER, this);
    }

    @Override
    public void process(Operation operation) {
        final Map<String, String> data = operation.getData();
        final String username = data.get("username");
        userClient.enableUser(username);
    }
}
