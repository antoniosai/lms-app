package com.app.lms.modules.admin_area.master_course.requests;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GetMasterCourseRequest {
    private String courseName;
    private UUID instructorUuid;
    private List<UUID> coursesUuid;
}
