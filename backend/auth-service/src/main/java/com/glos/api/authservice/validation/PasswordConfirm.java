package com.glos.api.authservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordConfirmValidator.class)
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConfirm {
    String password() default "password";
    String confirmPassword() default "confirmPassword";

    String message() default "Passwords not matches";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
