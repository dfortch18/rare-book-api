package com.dfortch.rarebook.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SlugValidator implements ConstraintValidator<ValidSlug, String> {

    private static final String SLUG_PATTERN = "^[a-z0-9]+(?:-[a-z0-9]+)*$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }

        return value.matches(SLUG_PATTERN);
    }
}
