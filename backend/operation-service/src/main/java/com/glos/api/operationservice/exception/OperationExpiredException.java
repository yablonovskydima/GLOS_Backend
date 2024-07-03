package com.glos.api.operationservice.exception;

import com.glos.api.operationservice.Operation;

public class OperationExpiredException extends RuntimeException {
    private Operation operation;

    public OperationExpiredException() {
        super();
    }

    public OperationExpiredException(String message) {
        super(message);
    }
    public OperationExpiredException(String message, Operation operation) {
        super(message);
        this.operation = operation;
    }

    public OperationExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public OperationExpiredException(Throwable cause) {
        super(cause);
    }

    public OperationExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Operation getOperation() {
        return operation;
    }
}
