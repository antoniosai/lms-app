package com.app.lms.modules.admin_area.instructor.requests;

import com.app.lms.core.validations.IsSanitized;
import lombok.Data;

@Data
public class GetInstructorRequest {

    @IsSanitized
    private String instructorIdentificationNumber;

    @IsSanitized
    private String instructorName;

    @IsSanitized
    private String instructorBirthPlace;
}
