package com.app.lms.modules.admin_area.account.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.enums.AccountTypeEnum;
import com.app.lms.modules.admin_area.account.dtos.AccountDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface AccountController {
    ResponseEntity<HttpResponseDTO<AccountDTO>> attachAccount(AccountDTO accountData, AccountTypeEnum accountType, UUID userUuid) throws Exception;

    ResponseEntity<HttpResponseDTO<AccountDTO>> detachAccount(AccountDTO accountData, AccountTypeEnum accountType);

    ResponseEntity<HttpResponseDTO<String>> test();
}
