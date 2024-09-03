package com.dfortch.rarebook.validation;

import com.dfortch.rarebook.persistence.entity.RareBook;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class BookConditionValidator implements ConstraintValidator<ValidBookCondition, String> {

    private boolean allowNull;

    @Override
    public void initialize(ValidBookCondition constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return allowNull;
        }

        return Arrays.stream(RareBook.Condition.values())
                .anyMatch(condition -> condition.name().equals(value));
    }
}
