package com.dfortch.rarebook.validation;

import com.dfortch.rarebook.persistence.entity.RareBook;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class BookRarityValidator implements ConstraintValidator<ValidBookRarity, String> {

    private boolean allowNull;

    @Override
    public void initialize(ValidBookRarity constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return allowNull;
        }

        return Arrays.stream(RareBook.Rarity.values())
                .anyMatch(rarity -> rarity.name().equals(value));
    }
}
