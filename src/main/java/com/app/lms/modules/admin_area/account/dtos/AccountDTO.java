package com.app.lms.modules.admin_area.account.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    @JsonProperty("uuid")
    private String accountUuid;

    @JsonProperty("fullName")
    private String accountName;

    @JsonProperty("userName")
    private String userName;
}
