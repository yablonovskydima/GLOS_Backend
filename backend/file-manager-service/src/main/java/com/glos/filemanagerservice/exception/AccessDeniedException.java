package com.glos.filemanagerservice.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super();
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}