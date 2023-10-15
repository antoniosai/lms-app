package com.app.lms.security.controllers;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.security.dtos.JwtAuthenticationDTO;
import com.app.lms.security.request.LoginRequest;
import com.app.lms.security.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthenticationDTO> login(@RequestBody LoginRequest request) throws NotFoundException {
        return ResponseEntity.ok(authenticationService.login(request));
    }
    @PostMapping("/register")
    public ResponseEntity<JwtAuthenticationDTO> register(@RequestBody LoginRequest request) throws NotFoundException {
        return ResponseEntity.ok(authenticationService.register(request));
    }
}
