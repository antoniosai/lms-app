package com.app.lms.modules.course.dtos;

import com.app.lms.modules.instructor.dtos.InstructorDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDTO {

    @JsonProperty("uuid")
    private UUID courseUuid;

    @JsonProperty("slug")
    private String courseSlug;

    @JsonProperty("name")
    private String courseName;

    @JsonProperty("description")
    private String courseDescription;

    @JsonProperty("instructor")
    private InstructorDTO courseInstructor;
}
