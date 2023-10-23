package com.app.lms.modules.admin_area.master_course.dtos;

import com.app.lms.modules.admin_area.instructor.dtos.InstructorDTO;
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
public class MasterCourseDTO {

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
