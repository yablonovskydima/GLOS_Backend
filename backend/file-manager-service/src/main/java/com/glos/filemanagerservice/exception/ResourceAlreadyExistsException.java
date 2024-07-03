package com.glos.filemanagerservice.exception;

public class ResourceAlreadyExistsException extends FieldException {

    public ResourceAlreadyExistsException(String field) {
        super(field);
    }

    public ResourceAlreadyExistsException(String s, String field) {
        super(s, field);
    }

    public ResourceAlreadyExistsException(String message, Throwable cause, String field) {
        super(message, cause, field);
    }

    public ResourceAlreadyExistsException(Throwable cause, String field) {
        super(cause, field);
    }
}
