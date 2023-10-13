package com.app.lms.core.validations;

import org.owasp.encoder.Encode;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoHtmlValidator implements ConstraintValidator<IsSanitized, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        String sanitized = Encode.forHtml(value);
        return sanitized.equals(value);
        // return sanitized.equals(value);
    }
}