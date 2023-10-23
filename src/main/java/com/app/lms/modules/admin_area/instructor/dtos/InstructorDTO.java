package com.app.lms.modules.admin_area.instructor.dtos;

import com.app.lms.core.validations.IsDate;
import com.app.lms.modules.admin_area.account.dtos.AccountDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorDTO {

    @JsonProperty("uuid")
    private UUID instructorUuid;

    @JsonProperty("identificationNumber")
    @NotBlank(message = "instructorIdentificationNumber Shouldn't Be Blank")
    private String instructorIdentificationNumber;

    @JsonProperty("name")
    @NotBlank(message = "instructorName Shouldn't Be Blank")
    private String instructorName;

    @JsonProperty("birthPlace")
    @NotBlank(message = "instructorBirthPlace Shouldn't Be Blank")
    private String instructorBirthPlace;

    @JsonProperty("birthDate")
    @NotBlank(message = "instructorBirthDate Shouldn't Be Blank")
    @IsDate
    private String instructorBirthDate;

    @JsonProperty("address")
    @NotBlank(message = "Address Shouldn't Be Blank")
    private String instructorAddress;

    @JsonProperty("profilePicture")
    private String instructorProfilePicture;

    @JsonProperty("account")
    private AccountDTO instructorAccount;
}
