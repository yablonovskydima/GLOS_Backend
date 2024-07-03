package com.glos.api.authservice.controller;

import com.glos.api.authservice.exception.*;
import feign.FeignException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleMethodArgumentNotValid(
            MethodArgumentNotValidException e
    ) {
        ExceptionBody exceptionBody = new SimpleExceptionBody("Validation failed.", new HashMap<>());
        List<FieldError> errors = appendPasswordConfirm(e.getBindingResult());
        exceptionBody.setErrors(errors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
        );
        System.out.println(exceptionBody.getErrors());
        return exceptionBody;
    }

    private List<FieldError> appendPasswordConfirm(BindingResult bindingResult) {
        List<FieldError> passwordConfirmErrors = new ArrayList<>(bindingResult.getFieldErrors());

        for (ObjectError error : bindingResult.getGlobalErrors()) {
            if (error.getObjectName().equals("userDTO") && error.getCode().equals("PasswordConfirm")) {
                passwordConfirmErrors.add(new FieldError("userDTO", "passwordConfirm", error.getDefaultMessage()));
            }
        }

        return passwordConfirmErrors;
    }

    @ExceptionHandler(value = {
            NoResourceFoundException.class,
            UsernameNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionBody handleResourceNotFound(
            Exception e
    ) {
        return new SimpleExceptionBody(e.getMessage(), new HashMap<>());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ExceptionBody handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException e
    ) {
        return new SimpleExceptionBody(e.getMessage(), new HashMap<>());
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ExceptionBody> handleFeignClient(FeignException ex)
    {

        return ResponseEntity.status(ex.status()).body(new SimpleExceptionBody(ex.getMessage(), new HashMap<>()));
    }

    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<ExceptionBody> handleHttpStatusCodeException(
            HttpStatusCodeException e
    ) {
        return ResponseEntity.status(e.getStatusCode()).body(new SimpleExceptionBody(e.getMessage(), new HashMap<>()));
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            InvocationTargetException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleBadRequest(
            Exception e
    ) {
        return new SimpleExceptionBody(e.getMessage(), new HashMap<>());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleConstraintViolationException(ConstraintViolationException ex) {
        ExceptionBody exceptionBody = new SimpleExceptionBody();
        ex.getConstraintViolations().forEach(violation -> {
            exceptionBody.appendError(violation.getPropertyPath().toString(), violation.getMessage());
        });
        return exceptionBody;
    }

    @ExceptionHandler(ResponseEntityException.class)
    public ExceptionBody handleResponseEntityException(
            ResponseEntityException e
    ) {
        ExceptionBody exceptionBody = new SimpleExceptionBody();
        exceptionBody.setMessage(e.getMessage());
        return exceptionBody;
    }

    @ExceptionHandler({
            AccessDeniedException.class,
            org.springframework.security.access.AccessDeniedException.class
    })
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAccessDenied(
            Exception e,
            AccessDeniedException e1,
            org.springframework.security.access.AccessDeniedException e2
    ) {
        if (e instanceof AccessDeniedException) {
            return new SimpleExceptionBody("Access denied.", new HashMap<>());
        } else if (e instanceof org.springframework.security.access.AccessDeniedException) {
            return new SimpleExceptionBody(e.getMessage(), new HashMap<>());
        }
        return new SimpleExceptionBody("Access denied", new HashMap<>());
    }

    @ExceptionHandler(UserAccountStateException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SimpleExceptionBody handleUserAccountStateException(
            UserAccountStateException e
    ) {
        return new SimpleExceptionBody(e.getMessage(), new HashMap<>());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionBody handleAuthentication(final AuthenticationException e) {
        return new SimpleExceptionBody("Authentication failed.", new HashMap<>());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionBody handleExpiredJwtException(
            ExpiredJwtException e
    ) {
        return new SimpleExceptionBody("JWT expired", new HashMap<>());
    }

    @ExceptionHandler({
            Throwable.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionBody handleResourceNotFound(Throwable throwable) {
        return new SimpleExceptionBody(throwable.getMessage(), new HashMap<>());
    }
}

