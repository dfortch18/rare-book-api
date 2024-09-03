package com.dfortch.rarebook.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SlugValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSlug {
    String message() default "{ValidSlug.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
