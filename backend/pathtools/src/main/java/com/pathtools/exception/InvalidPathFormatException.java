package com.pathtools.exception;

public class InvalidPathFormatException extends RuntimeException {

    private String path;

    public InvalidPathFormatException(String path) {
        this.path = path;
    }

    public InvalidPathFormatException(String message, String path) {
        super(message);
        this.path = path;
    }

    public InvalidPathFormatException(String message, Throwable cause, String path) {
        super(message, cause);
        this.path = path;
    }

    public InvalidPathFormatException(Throwable cause, String path) {
        super(cause);
        this.path = path;
    }

    public InvalidPathFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String path) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
