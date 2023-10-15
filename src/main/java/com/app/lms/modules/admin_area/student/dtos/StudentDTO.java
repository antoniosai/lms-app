package com.app.lms.modules.admin_area.student.dtos;

import com.app.lms.modules.admin_area.account.dtos.AccountDTO;
import com.app.lms.modules.admin_area.account.entities.AccountEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDTO {

    @JsonProperty("uuid")
    private UUID studentUuid;

    @JsonProperty("identificationNumber")
    @NotBlank(message = "studentIdentificationNumber Shouldn't Be Blank")
    private String studentIdentificationNumber;

    @JsonProperty("name")
    @NotBlank(message = "studentName Shouldn't Be Blank")
    private String studentName;

    @JsonProperty("birthPlace")
    @NotBlank(message = "studentBirthPlace Shouldn't Be Blank")
    private String studentBirthPlace;

    @JsonProperty("birthDate")
    @NotBlank(message = "studentBirthDate Shouldn't Be Blank")
    private String studentBirthDate;

    @JsonProperty("email")
    @NotBlank(message = "Email Shouldn't Be Blank")
    @Email(message = "Should be Valid Email")
    private String studentEmail;

    @JsonProperty("address")
    private String studentAddress;

    @JsonProperty("profilePicture")
    private String studentProfilePicture;

    @JsonProperty("account")
    private AccountDTO studentAccount;
}
