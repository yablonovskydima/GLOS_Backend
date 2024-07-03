package com.glos.filemanagerservice.exception;

public class ResourceNotFoundException extends FieldException {


    public ResourceNotFoundException(String field) {
        super(field);
    }

    public ResourceNotFoundException(String s, String field) {
        super(s, field);
    }

    public ResourceNotFoundException(String message, Throwable cause, String field) {
        super(message, cause, field);
    }

    public ResourceNotFoundException(Throwable cause, String field) {
        super(cause, field);
    }

}
