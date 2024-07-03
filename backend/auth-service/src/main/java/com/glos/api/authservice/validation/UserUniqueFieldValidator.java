package com.glos.api.authservice.validation;

import com.glos.api.authservice.client.UserAPIClient;
import com.glos.api.authservice.entities.User;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserUniqueFieldValidator implements ConstraintValidator<UserUniqueField, Object> {

    private final UserAPIClient userClient;
    private String field;

    public UserUniqueFieldValidator(UserAPIClient userClient) {
        this.userClient = userClient;
    }

    @Override
    public void initialize(UserUniqueField annotation) {
        this.field = annotation.name();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        final String fieldValue = (String)value;

        return switch (field) {
            case "username" -> getByUsername(fieldValue).isEmpty();
            case "email" -> getByEmail(fieldValue).isEmpty();
            case "phoneNumber" -> getByPhoneNumber(fieldValue).isEmpty();
            default -> true;
        };
    }

    private Optional<User> getByUsername(String username) {
        try {
            return Optional.ofNullable(userClient.getByUsername(username).getBody());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    private Optional<User> getByEmail(String email) {
        try {
            return Optional.ofNullable(userClient.getByEmail(email).getBody());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    private Optional<User> getByPhoneNumber(String phoneNumber) {
        try {
            return Optional.ofNullable(userClient.getByPhoneNumber(phoneNumber).getBody());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

}
