package com.app.lms.modules.enrollment.dtos;

import com.app.lms.modules.course.entities.CourseEntity;
import com.app.lms.modules.student.entities.StudentEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class EnrollmentDTO {

    @JsonProperty("uuid")
    private UUID enrollmentUuid;

    @JsonProperty("course")
    private CourseEntity enrollmentCourse;

    @JsonProperty("student")
    private StudentEntity enrollmentStudent;

    @JsonProperty("enrollmentDate")
    private Date enrollmentDate;

    @JsonProperty("isActive")
    private Boolean enrollmentIsActive;

}
