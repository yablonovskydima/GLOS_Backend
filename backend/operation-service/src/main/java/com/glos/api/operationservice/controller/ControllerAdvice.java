package com.glos.api.operationservice.controller;

import com.glos.api.operationservice.exception.*;
import feign.FeignException;
import jakarta.ws.rs.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ExceptionBody handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(
            final NotFoundException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(OperationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleOperationNotFound(
            final OperationNotFoundException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalState(
            final IllegalStateException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleIllegalArgument(
            final IllegalArgumentException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionBody handleValidationExceptions(MethodArgumentNotValidException ex) {
        var errorBuilder = ExceptionBody.builder();
        errorBuilder.setMessage("Validation failed");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errorBuilder.appendError(fieldName, errorMessage);
            } else if (error instanceof ObjectError) {
                String errorMessage = error.getDefaultMessage();
                errorBuilder.setMessage(errorMessage);
            }
        });
        return errorBuilder.build();
    }

    @ExceptionHandler(InvalidOperationDataPropertiesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleOperationDataPropertiesException(
            InvalidOperationDataPropertiesException ex
    ) {
        var errorBuilder = ExceptionBody.builder();
        errorBuilder.setMessage("Validation failed");
        ex.getPropertyErrors().forEach(errorBuilder::appendError);
        return errorBuilder.build();
    }

    @ExceptionHandler(OperationExpiredException.class)
    @ResponseStatus(HttpStatus.GONE)
    public ExceptionBody handleOperationExpiredException(
            OperationExpiredException e
    ) {
        return new ExceptionBody(e.getMessage());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ExceptionBody> handleFeignClient(FeignException ex)
    {
        ExceptionBody exceptionBody = new ExceptionBody(ex.getMessage());
        return ResponseEntity.status(ex.status()).body(exceptionBody);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleException(
            Exception e
    ) {
        e.printStackTrace();
        return new ExceptionBody("Internal error.");
    }
}
