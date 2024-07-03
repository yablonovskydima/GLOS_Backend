package com.glos.api.apigateway.domain.exception;

public class InvalidAccessTokenException extends RuntimeException {

    private final String token;

    public InvalidAccessTokenException(String token) {
        this.token = token;
    }

    public InvalidAccessTokenException(String message, String token) {
        super(message);
        this.token = token;
    }

    public InvalidAccessTokenException(String message, Throwable cause, String token) {
        super(message, cause);
        this.token = token;
    }

    public InvalidAccessTokenException(Throwable cause, String token) {
        super(cause);
        this.token = token;
    }

    public InvalidAccessTokenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String token) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
