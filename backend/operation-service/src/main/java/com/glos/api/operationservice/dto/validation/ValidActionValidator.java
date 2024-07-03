package com.glos.api.operationservice.dto.validation;

import com.glos.api.operationservice.Action;
import com.glos.api.operationservice.dto.validation.annotation.ValidAction;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidActionValidator
        implements ConstraintValidator<ValidAction, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return Action.valueOfIgnoreCase((String)value) != null;
    }

}
