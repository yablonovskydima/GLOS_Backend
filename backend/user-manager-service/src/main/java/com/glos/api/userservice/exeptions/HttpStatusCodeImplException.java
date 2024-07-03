package com.glos.api.userservice.exeptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;

import java.nio.charset.Charset;

public class HttpStatusCodeImplException extends org.springframework.web.client.HttpStatusCodeException {

    public HttpStatusCodeImplException(HttpStatusCode statusCode) {
        super(statusCode);
    }

    public HttpStatusCodeImplException(HttpStatusCode statusCode, String statusText) {
        super(statusCode, statusText);
    }

    public HttpStatusCodeImplException(HttpStatusCode statusCode, String statusText, byte[] responseBody, Charset responseCharset) {
        super(statusCode, statusText, responseBody, responseCharset);
    }

    public HttpStatusCodeImplException(HttpStatusCode statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(statusCode, statusText, responseHeaders, responseBody, responseCharset);
    }

    public HttpStatusCodeImplException(String message, HttpStatusCode statusCode, String statusText, HttpHeaders responseHeaders, byte[] responseBody, Charset responseCharset) {
        super(message, statusCode, statusText, responseHeaders, responseBody, responseCharset);
    }
}
