package com.app.lms.core.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class NotFoundError extends AppSubError {
    private String object;
    private String message;
    private Object rejectedValue;

    public NotFoundError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
