package com.app.lms.security.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.security.dtos.JwtAuthenticationDTO;
import com.app.lms.security.request.LoginRequest;
import com.app.lms.security.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<JwtAuthenticationDTO>> login(@RequestBody LoginRequest request) throws NotFoundException {
        return new HttpResponseDTO<>(authenticationService.login(request))
                .toResponse("Successfully Logged In");
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponseDTO<JwtAuthenticationDTO>> register(@RequestBody LoginRequest request) throws NotFoundException {
        return new HttpResponseDTO<>(authenticationService.register(request))
                .toResponse("Fetch Student by Pagination from Server");
    }
}
