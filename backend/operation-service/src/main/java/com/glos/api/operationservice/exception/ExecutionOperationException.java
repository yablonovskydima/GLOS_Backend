package com.glos.api.operationservice.exception;

public class ExecutionOperationException extends RuntimeException {
    private String operationCode;
    public ExecutionOperationException() {
        super();
    }

    public ExecutionOperationException(String message) {
        super(message);
    }
    public ExecutionOperationException(String message, String operationCode) {
        super(message);
        this.operationCode = operationCode;
    }

    public ExecutionOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExecutionOperationException(Throwable cause) {
        super(cause);
    }

    public ExecutionOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getOperationCode() {
        return operationCode;
    }
}
