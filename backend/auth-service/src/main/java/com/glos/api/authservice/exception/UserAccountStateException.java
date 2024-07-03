package com.glos.api.authservice.exception;

public class UserAccountStateException extends RuntimeException {
    public UserAccountStateException() {
        super();
    }

    public UserAccountStateException(String message) {
        super(message);
    }

    public UserAccountStateException(Throwable cause) {
        super(cause);
    }
}
