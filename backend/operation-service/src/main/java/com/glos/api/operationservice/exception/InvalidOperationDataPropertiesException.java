package com.glos.api.operationservice.exception;

import java.util.Map;

public class InvalidOperationDataPropertiesException extends RuntimeException {

    private Map<String, String> propertyErrors;


    public InvalidOperationDataPropertiesException() {
        super();
    }

    public InvalidOperationDataPropertiesException(String message) {
        super(message);
    }

    public InvalidOperationDataPropertiesException(String message, Map<String, String> errors) {
        super(message);
        this.propertyErrors = errors;
    }

    public InvalidOperationDataPropertiesException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOperationDataPropertiesException(Throwable cause) {
        super(cause);
    }

    public InvalidOperationDataPropertiesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public Map<String, String> getPropertyErrors() {
        return propertyErrors;
    }
}
