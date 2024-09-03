package com.dfortch.rarebook.validation;

import com.dfortch.rarebook.persistence.repository.RareBookRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NonExistentBookIsbnValidator implements ConstraintValidator<NonExistentBookIsbn, String> {

    private final RareBookRepository rareBookRepository;

    private boolean allowNull;

    @Override
    public void initialize(NonExistentBookIsbn constraintAnnotation) {
        allowNull = constraintAnnotation.allowNull();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) {
            return allowNull;
        }

        return !rareBookRepository.existsByIsbn(s);
    }
}
