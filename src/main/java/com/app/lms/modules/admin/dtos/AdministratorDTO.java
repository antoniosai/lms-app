package com.app.lms.modules.admin.dtos;

import com.app.lms.modules.account.dtos.AccountDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdministratorDTO {

    @JsonProperty("uuid")
    private UUID adminUuid;

    @JsonProperty("identificationNumber")
    private String adminIdentificationNumber;

    @JsonProperty("name")
    private String adminName;

    @JsonProperty("birthPlace")
    private String adminBirthPlace;

    @JsonProperty("birthDate")
    private Date adminBirthDate;

    @JsonProperty("address")
    private String adminAddress;

    @JsonProperty("profilePicture")
    private String adminProfilePicture;

    @JsonProperty("account")
    private AccountDTO adminAccount;
}
