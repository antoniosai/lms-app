package com.app.lms.modules.instructor.requests;

import lombok.Data;

@Data
public class GetInstructorRequest {
    private String identificationNumber;

    private String name;
}
