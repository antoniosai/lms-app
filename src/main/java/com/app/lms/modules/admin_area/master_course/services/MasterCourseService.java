package com.app.lms.modules.admin_area.master_course.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.instructor.dtos.InstructorDTO;
import com.app.lms.modules.admin_area.master_course.dtos.MasterCourseDTO;
import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.master_course.requests.GetMasterCourseRequest;

import java.util.UUID;

public interface MasterCourseService {

    PaginationUtil<MasterCourseEntity, MasterCourseDTO> getCourseByPagination(int page, int perPage, GetMasterCourseRequest getMasterCourseRequest);

    MasterCourseDTO getCourseByUuid(UUID courseUuid) throws NotFoundException;

    MasterCourseDTO attachToInstructor(UUID courseUuid, InstructorDTO instructor) throws NotFoundException;

    MasterCourseDTO detachFromInstructor(UUID courseUuid) throws NotFoundException;

    MasterCourseDTO createNewCourse(MasterCourseDTO newCourseData);

    MasterCourseDTO updateCourseByUuid(UUID courseUuid, MasterCourseDTO newCourseData);

    void deleteCourseByUuid(UUID courseUuid);
}
