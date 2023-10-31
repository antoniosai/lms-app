package com.app.lms.core.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IsRequiredValidator.class)
public @interface IsRequired {

    String message() default "Input should be a blank";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
