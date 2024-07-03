package com.glos.api.authservice.exception;

public class NoSuchClaimException extends RuntimeException {

    private String token;

    public NoSuchClaimException(String token) {
        this.token = token;
    }

    public NoSuchClaimException(String message, String token) {
        super(message);
        this.token = token;
    }

    public NoSuchClaimException(String message, Throwable cause, String token) {
        super(message, cause);
        this.token = token;
    }

    public NoSuchClaimException(Throwable cause, String token) {
        super(cause);
        this.token = token;
    }

    public NoSuchClaimException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String token) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
