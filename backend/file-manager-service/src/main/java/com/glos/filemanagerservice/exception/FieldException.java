package com.glos.filemanagerservice.exception;

public class FieldException extends IllegalArgumentException {
    private final String field;

    public FieldException(String field) {
        this.field = field;
    }

    public FieldException(String s, String field) {
        super(s);
        this.field = field;
    }

    public FieldException(String message, Throwable cause, String field) {
        super(message, cause);
        this.field = field;
    }

    public FieldException(Throwable cause, String field) {
        super(cause);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
