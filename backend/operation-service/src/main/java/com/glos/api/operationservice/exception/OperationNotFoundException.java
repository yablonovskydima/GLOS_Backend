package com.glos.api.operationservice.exception;


public class OperationNotFoundException extends RuntimeException {

    private String operationCode;

    public OperationNotFoundException() {
        super();
    }

    public OperationNotFoundException(String message, String operationCode) {
        super(message);
        this.operationCode = operationCode;
    }

    public OperationNotFoundException(String message, Throwable cause, String operationCode) {
        super(message, cause);
        this.operationCode = operationCode;
    }

    public OperationNotFoundException(Throwable cause, String operationCode) {
        super(cause);
        this.operationCode = operationCode;
    }

    public OperationNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String operationCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.operationCode = operationCode;
    }

    public String getOperationCode() {
        return operationCode;
    }
}
