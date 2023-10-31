package com.app.lms.modules.student_area.course.services;

import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.master_course.dtos.MasterCourseDTO;
import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.master_course.requests.GetMasterCourseRequest;

import java.util.UUID;

public interface StudentCourseService {
    PaginationUtil<MasterCourseEntity, MasterCourseDTO> getPaginationCourseByInstructorUuid(int page, int perPage, GetMasterCourseRequest instructorCoursePaginationRequest) throws NotFoundException;

    MasterCourseDTO getCourseByUuid(UUID courseUuid) throws NotFoundException, ForbiddenException;
}
