package com.glos.api.authservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

@Component
public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirm, Object> {

    private String password;
    private String passwordConfirm;
    private String message;

    @Override
    public void initialize(PasswordConfirm passwordConfirm) {
        this.password = passwordConfirm.password();
        this.passwordConfirm = passwordConfirm.confirmPassword();
        this.message = passwordConfirm.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(password);
        Object passwordConfirmValue = new BeanWrapperImpl(value).getPropertyValue(passwordConfirm);

        boolean isValid = (passwordValue != null && passwordValue.equals(passwordConfirmValue))
                || (passwordValue == null && passwordConfirmValue == null);
        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

