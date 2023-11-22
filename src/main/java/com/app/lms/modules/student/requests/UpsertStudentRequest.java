package com.app.lms.modules.student.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpsertStudentRequest {
    @JsonProperty("identificationNumber")
    @NotBlank
    private String studentIdentificationNumber;

    @JsonProperty("name")
    @NotBlank
    private String studentName;

    @JsonProperty("birthPlace")
    @NotBlank
    private String studentBirthPlace;

    @JsonProperty("birthDate")
    @NotBlank
    private String studentBirthDate;

    @JsonProperty("address")
    @NotBlank
    private String studentAddress;

    @JsonProperty("profilePicture")
    private String studentProfilePicture;
}
