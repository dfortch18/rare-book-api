package com.dfortch.rarebook.validation;

import com.dfortch.rarebook.persistence.repository.CategoryRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExistentCategorySlugValidator implements ConstraintValidator<ExistentCategorySlug, String> {

    private final CategoryRepository categoryRepository;

    private boolean allowNull;

    @Override
    public void initialize(ExistentCategorySlug constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return allowNull;
        }

        return categoryRepository.existsBySlug(value);
    }
}
