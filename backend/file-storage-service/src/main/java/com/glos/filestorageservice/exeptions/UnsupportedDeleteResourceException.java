package com.glos.filestorageservice.exeptions;

public class UnsupportedDeleteResourceException extends RuntimeException {
    public UnsupportedDeleteResourceException() {
    }

    public UnsupportedDeleteResourceException(String message) {
        super(message);
    }

    public UnsupportedDeleteResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedDeleteResourceException(Throwable cause) {
        super(cause);
    }

    public UnsupportedDeleteResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
