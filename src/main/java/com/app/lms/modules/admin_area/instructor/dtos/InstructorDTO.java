package com.app.lms.modules.admin_area.instructor.dtos;

import com.app.lms.modules.admin_area.account.dtos.AccountDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDTO {

    @JsonProperty("uuid")
    private UUID instructorUuid;

    @JsonProperty("identificationNumber")
    private String instructorIdentificationNumber;

    @JsonProperty("name")
    private String instructorName;

    @JsonProperty("birthPlace")
    private String instructorBirthPlace;

    @JsonProperty("birthDate")
    private Date instructorBirthDate;

    @JsonProperty("address")
    private String instructorAddress;

    @JsonProperty("profilePicture")
    private String instructorProfilePicture;

    @JsonProperty("account")
    private AccountDTO instructorAccount;
}
