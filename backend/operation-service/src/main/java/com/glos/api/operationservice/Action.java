package com.glos.api.operationservice;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public enum Action {

    NONE(),
    CONFIRM_EMAIL("username", "email", "code"),
    CONFIRM_PHONE_NUMBER("username", "phoneNumber", "code"),
    CHANGE_USERNAME("username" , "email", "code", "newUsername"),
    CHANGE_PASSWORD("username", "email", "code", "oldPassword", "newPassword"),
    CHANGE_EMAIL("username", "email", "code", "oldEmail", "newEmail"),
    CHANGE_PHONE_NUMBER("username", "email", "code", "oldPhoneNumber", "newPhoneNumber"),
    DROP_ACCOUNT("username", "email", "code"),
    RESTORE_ACCOUNT("username", "email", "code");

    private Set<String> properties;

    Action(String...properties) {
        this.properties = Arrays.stream(properties)
                .collect(Collectors.toSet());
    }

    public static Action valueOfIgnoreCase(String name) {
        return valueOf(name.toUpperCase().replace("-", "_"));
    }

    public Set<String> getProperties() {
        return properties;
    }

    public String getNameLowercase() {
        return name().toLowerCase();
    }

    public String getNameUppercase() {
        return name().toUpperCase();
    }

    public boolean checkProperties(Map<String, String> data, String...ignoreProperties) {
        if (data == null)
            return false;
        else if (ignoreProperties == null) {

            return properties.stream().allMatch(data::containsKey);
        }
        List<String> ignores = Arrays.asList(ignoreProperties);
        return properties.stream()
                .allMatch(x -> ignores.contains(x) || data.containsKey(x));
    }
}
