package com.glos.databaseAPIService.domain.exceptions;

import java.util.Map;

public record InternalExceptionBody(
        Class<? extends Throwable> exceptionType,
        String message,
        Map<String, String> errors
) { }
