package com.glos.api.userservice.controllers;

import com.glos.api.userservice.exeptions.*;
import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(UnsupportedDeleteResourceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleUnsupportedOperationException(
            UnsupportedDeleteResourceException e
    ) {
        return new SimpleExceptionBody("Unsupported operation", Map.of(
                "group", e.getMessage()
        ));
    }

    @ExceptionHandler(value = {
            ResourceNotFoundException.class,
            NoResourceFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(
            Exception e
    ) {
        return new SimpleExceptionBody(e.getMessage(), new HashMap<>());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ExceptionBody> handleFeignClient(FeignException ex)
    {

        return ResponseEntity.status(ex.status()).body(new SimpleExceptionBody(ex.getMessage(), new HashMap<>()));
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            InvocationTargetException.class,
            MethodArgumentNotValidException.class,
            InvalidLoginException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleBadRequest(
            Exception e
    ) {
        HashMap<String, String> map = new HashMap<>();
        if (e instanceof InvalidLoginException ex) {
            map.put("login", e.getMessage());
        }
        return new SimpleExceptionBody("Bad request", map);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionBody handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        return new SimpleExceptionBody(e.getMessage(), new HashMap<>());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleConstraintViolationException(ConstraintViolationException ex) {
        ExceptionBody exceptionBody = new SimpleExceptionBody();
        ex.getConstraintViolations().forEach(violation -> {
            exceptionBody.appendError(violation.getPropertyPath().toString(), violation.getMessage());
        });
        return exceptionBody;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleResourceNotFound(Throwable throwable) {
        return new SimpleExceptionBody(throwable.getMessage(), new HashMap<>());
    }
}

