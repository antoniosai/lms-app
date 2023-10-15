package com.app.lms.modules.admin_area.account.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.enums.AccountTypeEnum;
import com.app.lms.modules.admin_area.account.dtos.AccountDTO;
import com.app.lms.modules.admin_area.account.entities.AccountEntity;
import com.app.lms.modules.admin_area.account.requests.GetAccountRequest;

import java.util.UUID;

public interface AccountService {
    AccountDTO attachAccount(AccountDTO accountData, AccountTypeEnum accountType, UUID userUuid) throws NotFoundException;

    void deleteAccountByUuid(UUID accountUuid);
}
