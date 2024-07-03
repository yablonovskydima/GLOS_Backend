package com.glos.api.authservice.util;

import com.glos.api.authservice.dto.OperationCreateRequest;

import java.util.Map;

public final class OperationRequests {

    public static OperationCreateRequest changePassword(Integer expired, Map<String, String> data) {
        return new OperationCreateRequest("change-password", data, expired);
    }

    public static OperationCreateRequest changeEmail(Integer expired, Map<String, String> data) {
        return new OperationCreateRequest("change-email", data, expired);
    }

    public static OperationCreateRequest changePhoneNumber(Integer expired, Map<String, String> data) {
        return new OperationCreateRequest("change-phone-number", data, expired);
    }

    public static OperationCreateRequest changeUsername(Integer expired, Map<String, String> data) {
        return new OperationCreateRequest("change-username", data, expired);
    }

    public static OperationCreateRequest confirmEmail(Integer expired, Map<String, String> data) {
        return new OperationCreateRequest("confirm-email", data, expired);
    }

    public static OperationCreateRequest confirmPhoneNumber(Integer expired, Map<String, String> data) {
        return new OperationCreateRequest("confirm-phone-number", data, expired);
    }

    public static OperationCreateRequest restoreAccount(Integer expired, Map<String, String> data) {
        return new OperationCreateRequest("restore-account", data, expired);
    }

    public static OperationCreateRequest dropAccount(Integer expired, Map<String, String> data) {
        return new OperationCreateRequest("drop-account", data, expired);
    }

}
