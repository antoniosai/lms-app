package com.app.lms.modules.admin_area.account.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.enums.AccountTypeEnum;
import com.app.lms.modules.admin_area.account.dtos.AccountDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    UserDetailsService userDetailsService();

    AccountDTO attachAccount(AccountDTO accountData, AccountTypeEnum accountType, UUID userUuid) throws Exception;

    AccountDTO findOneAccountByAccountUsername(String username) throws NotFoundException;

    List<AccountDTO> findAccountsByAccountUsername(String username);

    void detachAccount(UUID accountUuid) throws NotFoundException;

    void deleteAccountByUuid(UUID accountUuid);

    void deleteAccountByUsername(String username);
}
