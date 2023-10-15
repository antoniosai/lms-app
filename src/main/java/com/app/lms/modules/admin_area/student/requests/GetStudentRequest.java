package com.app.lms.modules.admin_area.student.requests;

import lombok.Data;

@Data
public class GetStudentRequest {
    private String identificationNumber;

    private String name;

    private String email;
}
