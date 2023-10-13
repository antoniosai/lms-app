package com.app.lms.core.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NoHandlerError extends AppSubError {
    private String object;
    private String message;
}
