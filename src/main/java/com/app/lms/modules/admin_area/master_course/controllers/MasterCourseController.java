package com.app.lms.modules.admin_area.master_course.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.instructor.dtos.InstructorDTO;
import com.app.lms.modules.admin_area.master_course.dtos.MasterCourseDTO;
import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.master_course.requests.GetMasterCourseRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface MasterCourseController {
    ResponseEntity<HttpResponseDTO<PaginationUtil<MasterCourseEntity, MasterCourseDTO>>> getCourseByPagination(int page, int perPage, GetMasterCourseRequest getMasterCourseRequest);

    ResponseEntity<HttpResponseDTO<MasterCourseDTO>> getCourseById(UUID courseUuid) throws NotFoundException;

    ResponseEntity<HttpResponseDTO<MasterCourseDTO>> createNewCourse(MasterCourseDTO newCourseData);

    ResponseEntity<HttpResponseDTO<MasterCourseDTO>> updateCourseByUuid(UUID courseUuid, MasterCourseDTO newCourseData) throws NotFoundException;

    ResponseEntity<HttpResponseDTO<MasterCourseDTO>> attachToInstructor(UUID courseUuid, InstructorDTO instructor) throws NotFoundException;

    ResponseEntity<HttpResponseDTO<MasterCourseDTO>> detachFromInstructor(UUID courseUuid) throws NotFoundException;

    ResponseEntity<HttpResponseDTO<String>> deleteCourseByUuid(UUID courseUuid);

}
