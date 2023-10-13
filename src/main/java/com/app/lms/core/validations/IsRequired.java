package com.app.lms.core.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = IsRequiredValidator.class)
public @interface IsRequired {

    public String message() default "Input should be a blank";

    public Class<?>[] groups() default {};

    public Class<? extends Payload>[] payload() default {};
}
