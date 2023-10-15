package com.app.lms.modules.admin_area.account.requests;

import lombok.Data;

@Data
public class GetAccountRequest {

    private String accountUuid;
    private String accountName;
    private String accountUsername;
}
