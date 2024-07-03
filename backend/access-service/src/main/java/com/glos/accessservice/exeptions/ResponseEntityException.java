package com.glos.accessservice.exeptions;

import org.springframework.http.ResponseEntity;

public class ResponseEntityException extends RuntimeException{

    private final ResponseEntity<?> response;

    public ResponseEntityException(ResponseEntity<?> response) {
        this.response = response;
    }

    public ResponseEntityException(String message, ResponseEntity<?> response) {
        super(message);
        this.response = response;
    }

    public ResponseEntityException(String message, Throwable cause, ResponseEntity<?> response) {
        super(message, cause);
        this.response = response;
    }

    public ResponseEntityException(Throwable cause, ResponseEntity<?> response) {
        super(cause);
        this.response = response;
    }

    public ResponseEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ResponseEntity<?> response) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.response = response;
    }

    public ResponseEntity<?> getResponse() {
        return response;
    }

}
