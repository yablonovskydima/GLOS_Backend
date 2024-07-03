package com.accesstools;

public class InvalidAccessPatternException extends RuntimeException {

    private final String pattern;

    public InvalidAccessPatternException(String pattern) {
        this.pattern = pattern;
    }

    public InvalidAccessPatternException(String message, String pattern) {
        super(message);
        this.pattern = pattern;
    }

    public InvalidAccessPatternException(String message, Throwable cause, String pattern) {
        super(message, cause);
        this.pattern = pattern;
    }

    public InvalidAccessPatternException(Throwable cause, String pattern) {
        super(cause);
        this.pattern = pattern;
    }

    public InvalidAccessPatternException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String pattern) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.pattern = pattern;
    }

    public String getPattern() {
        return pattern;
    }
}
