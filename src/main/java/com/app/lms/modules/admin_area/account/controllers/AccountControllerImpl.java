package com.app.lms.modules.admin_area.account.controllers;

import com.app.lms.modules.admin_area.account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account")
@Validated
public class AccountControllerImpl implements AccountController {
    @Autowired
    private AccountService accountService;
}
