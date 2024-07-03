package com.glos.filemanagerservice.exception;

public class InvalidLoginException extends RuntimeException {

    private final String login;

    public InvalidLoginException(String login) {
        this.login = login;
    }

    public InvalidLoginException(String message, String login) {
        super(message);
        this.login = login;
    }

    public InvalidLoginException(String message, Throwable cause, String login) {
        super(message, cause);
        this.login = login;
    }

    public InvalidLoginException(Throwable cause, String login) {
        super(cause);
        this.login = login;
    }

    public InvalidLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String login) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.login = login;
    }

    public String getLogin() {
        return login;
    }
}
