package com.app.lms.core.exceptions;


import com.app.lms.core.errors.AppSubError;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UnauthorizedException extends AppException {

    private List<AppSubError> details = new ArrayList<>();

    public UnauthorizedException() {
        super("Access Denied", HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

    public UnauthorizedException(String message, List<AppSubError> details) {
        super(message, HttpStatus.UNAUTHORIZED);
        this.details = details;
    }

    public UnauthorizedException(String message, AppSubError details) {
        super(message, HttpStatus.UNAUTHORIZED);
        this.details.add(details);
    }
}
