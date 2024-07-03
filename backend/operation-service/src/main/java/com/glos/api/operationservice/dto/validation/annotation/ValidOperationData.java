package com.glos.api.operationservice.dto.validation.annotation;

import com.glos.api.operationservice.dto.validation.ValidOperationDataValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidOperationDataValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOperationData {
    String[] ignoreProperties() default {};

    String message() default "Incorrect data properties for specified action";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
