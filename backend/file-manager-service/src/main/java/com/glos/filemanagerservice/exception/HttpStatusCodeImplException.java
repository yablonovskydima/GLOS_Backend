package com.glos.filemanagerservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpStatusCodeImplException extends org.springframework.web.client.HttpStatusCodeException {

    private Map<String, String> errors = new HashMap<>();

    public HttpStatusCodeImplException(HttpStatusCode statusCode) {
        super(statusCode);
    }

    public HttpStatusCodeImplException(HttpStatusCode statusCode, String message) {
        super(statusCode, message);
    }

    public HttpStatusCodeImplException(HttpStatusCode statusCode, Map<String, String> errors) {
        super(statusCode);
        this.errors = errors;
    }

    public HttpStatusCodeImplException(HttpStatusCode statusCode, String statusText, Map<String, String> errors) {
        super(statusCode, statusText);
        this.errors = errors;
    }

    public HttpStatusCodeImplException(HttpStatusCode statusCode, String statusText, byte[] responseBody, Charset responseCharset, Map<String, String> errors) {
        super(statusCode, statusText, responseBody, responseCharset);
        this.errors = errors;
    }

    public HttpStatusCodeImplException(HttpStatusCode statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset, Map<String, String> errors) {
        super(statusCode, statusText, responseHeaders, responseBody, responseCharset);
        this.errors = errors;
    }

    public HttpStatusCodeImplException(String message, HttpStatusCode statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset, Map<String, String> errors) {
        super(message, statusCode, statusText, responseHeaders, responseBody, responseCharset);
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
