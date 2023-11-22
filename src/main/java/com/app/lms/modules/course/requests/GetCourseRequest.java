package com.app.lms.modules.course.requests;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GetCourseRequest {
    private String courseName;
    private UUID instructorUuid;
    private List<UUID> coursesUuid;
}
