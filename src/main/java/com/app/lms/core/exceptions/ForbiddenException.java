package com.app.lms.core.exceptions;


import com.app.lms.core.errors.AppSubError;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ForbiddenException extends AppException {

    private List<AppSubError> details = new ArrayList<>();

    public ForbiddenException() {
        super("Access Denied", HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message, List<AppSubError> details) {
        super(message, HttpStatus.FORBIDDEN);
        this.details = details;
    }

    public ForbiddenException(String message, AppSubError details) {
        super(message, HttpStatus.FORBIDDEN);
        this.details.add(details);
    }
}
