package com.app.lms.core.validations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IsDateValidator.class)
public @interface IsDate {

    public String message() default "Invalid Date";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
