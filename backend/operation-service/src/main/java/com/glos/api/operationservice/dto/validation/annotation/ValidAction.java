package com.glos.api.operationservice.dto.validation.annotation;

import com.glos.api.operationservice.dto.validation.ValidActionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidActionValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAction {

    String message() default "Incorrect data properties for specified action";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
