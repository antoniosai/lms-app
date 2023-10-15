package com.app.lms.security.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.enums.AccountTypeEnum;
import com.app.lms.modules.admin_area.account.entities.AccountEntity;
import com.app.lms.modules.admin_area.account.repositories.AccountMainRepository;
import com.app.lms.security.dtos.JwtAuthenticationDTO;
import com.app.lms.security.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final AccountMainRepository accountMainRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationDTO login(LoginRequest request) throws NotFoundException {
        log.info(passwordEncoder.encode(request.getPassword()));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        log.info("Looking for User Account");
        AccountEntity account = accountMainRepository.findByAccountUsername(request.getUsername());

        if(account == null) throw new NotFoundException("Account Not Found");

        String jwt = jwtService.generateToken(account);

        return JwtAuthenticationDTO
            .builder()
            .token(jwt)
            .build();
    }

    @Override
    public JwtAuthenticationDTO register(LoginRequest request) {
        var user = AccountEntity.builder()
                .accountUsername(request.getUsername())
                .accountPassword(passwordEncoder.encode(request.getPassword()))
                .accountType(AccountTypeEnum.STUDENT)
                .build();
        accountMainRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationDTO.builder().token(jwt).build();
    }
}
