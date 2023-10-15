package com.app.lms.security.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.security.dtos.JwtAuthenticationDTO;
import com.app.lms.security.request.LoginRequest;

public interface AuthService {

    JwtAuthenticationDTO login(LoginRequest request) throws NotFoundException;
    JwtAuthenticationDTO register(LoginRequest request) throws NotFoundException;
}
