package com.glos.databaseAPIService.domain.exceptions;

import java.util.Map;

public class ResourceAlreadyExistsException extends RuntimeException {

    private final Map.Entry<String, String> field;

    public ResourceAlreadyExistsException(Map.Entry<String, String> field) {
        this.field = field;
    }

    public ResourceAlreadyExistsException(String message, Map.Entry<String, String> field) {
        super(message);
        this.field = field;
    }

    public ResourceAlreadyExistsException(String message, Throwable cause, Map.Entry<String, String> field) {
        super(message, cause);
        this.field = field;
    }

    public ResourceAlreadyExistsException(Throwable cause, Map.Entry<String, String> field) {
        super(cause);
        this.field = field;
    }

    public ResourceAlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Map.Entry<String, String> field) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.field = field;
    }

    public Map.Entry<String, String> getField() {
        return field;
    }
}
