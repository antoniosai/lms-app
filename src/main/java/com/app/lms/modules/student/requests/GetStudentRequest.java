package com.app.lms.modules.student.requests;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GetStudentRequest {
    private String identificationNumber;

    private String name;

    private String email;

    private UUID courseUuid;

    private List<UUID> studentUuid;
}
