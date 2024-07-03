package com.glos.api.operationservice.dto.validation;

import com.glos.api.operationservice.Action;
import com.glos.api.operationservice.dto.validation.annotation.ValidOperationData;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Map;

public class ValidOperationDataValidator
        implements ConstraintValidator<ValidOperationData, Object> {

    private String[] ignores;

    @Override
    public void initialize(ValidOperationData validData) {
        this.ignores = validData.ignoreProperties();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        final Action actionValue = extractAction(value);
        final Map<String, String> dataValue = extractData(value);

        if (actionValue == null || dataValue == null) {
            return false;
        }

        return actionValue.checkProperties(dataValue, ignores);
    }

    private Action extractAction(Object value) {
        final Object actionValue = extract(value, "action");
        if (actionValue instanceof Action a) {
            return a;
        } else if (actionValue instanceof String s) {
            return Action.valueOfIgnoreCase(s);
        }
        return null;
    }

    private Map<String, String> extractData(Object value) {
        if (value instanceof Map map) {
            return map;
        }
        return (Map<String, String>) extract(value, "data");
    }

    private Object extract(Object value, String property) {
        return new BeanWrapperImpl(value)
                .getPropertyValue(property);
    }
}
