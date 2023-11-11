package com.app.lms.modules.account.dtos;

import com.app.lms.enums.AccountTypeEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    @JsonProperty("uuid")
    private UUID accountUuid;

    @JsonProperty("username")
    private String accountUsername;

    @JsonProperty("type")
    private AccountTypeEnum accountType;

    @JsonProperty("password")
    private String accountPassword;

}
