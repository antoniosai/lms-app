package com.app.lms.core.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class IsDateValidator implements ConstraintValidator<IsDate, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are considered valid
        }

        try {
            LocalDate.parse(value, DateTimeFormatter.ISO_DATE);
            return true; // Valid date format
        } catch (Exception e) {
            return false; // Invalid date format
        }
    }
}
