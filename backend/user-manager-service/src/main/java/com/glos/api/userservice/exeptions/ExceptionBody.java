package com.glos.api.userservice.exeptions;


import java.util.Map;

public interface ExceptionBody {
    String getMessage();
    void setMessage(String message);
    void appendError(String key, String value);
    void removeError(String key);
    void setErrors(Map<String, String> errors);
    Map<String, String> getErrors();
}
