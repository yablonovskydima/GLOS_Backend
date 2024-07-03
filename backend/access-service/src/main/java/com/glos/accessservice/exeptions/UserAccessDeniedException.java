package com.glos.accessservice.exeptions;

import com.glos.accessservice.entities.User;

public class UserAccessDeniedException extends RuntimeException {

    private final User user;

    public UserAccessDeniedException(User user) {
        this.user = user;
    }

    public UserAccessDeniedException(String message) {
        super(message);
        this.user = null;
    }

    public UserAccessDeniedException(String message, User user) {
        super(message);
        this.user = user;
    }

    public UserAccessDeniedException(String message, Throwable cause, User user) {
        super(message, cause);
        this.user = user;
    }

    public UserAccessDeniedException(Throwable cause, User user) {
        super(cause);
        this.user = user;
    }

    public UserAccessDeniedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, User user) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.user = user;
    }
}
