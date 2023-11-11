package com.app.lms.modules.account.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.enums.AccountTypeEnum;
import com.app.lms.modules.account.dtos.AccountDTO;
import com.app.lms.modules.account.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/administrator-area/accounts")
public class AccountControllerImpl implements AccountController {
    @Autowired
    private AccountService accountService;

    @Override
    @PutMapping(value = "/{userUuid}/{accountType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<AccountDTO>> attachAccount(@Valid @RequestBody AccountDTO accountData, @PathVariable AccountTypeEnum accountType, @PathVariable UUID userUuid) throws Exception {

        return new HttpResponseDTO<>(accountService.attachAccount(accountData, accountType, userUuid), HttpStatus.CREATED)
                .setResponseHeaders("accountData", accountData)
                .setResponseHeaders("accountType", accountType)
                .toResponse("Successfully Attach a Student By UUID from Server");
    }

    @Override
    public ResponseEntity<HttpResponseDTO<AccountDTO>> detachAccount(AccountDTO accountData, AccountTypeEnum accountType) {
        return null;
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<String>> test() {
        return new HttpResponseDTO<>("Test", HttpStatus.CREATED)
                .toResponse("Successfully Attach a Student By UUID from Server");
    }
}
