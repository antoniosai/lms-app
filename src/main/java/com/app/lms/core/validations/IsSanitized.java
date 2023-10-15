package com.app.lms.core.validations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NoHtmlValidator.class)
@Documented
public @interface IsSanitized {
    // TODO use a better message, look up ValidationMEssages.properties
    String message() default "Not Allowed";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}