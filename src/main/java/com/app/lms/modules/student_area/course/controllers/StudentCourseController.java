package com.app.lms.modules.student_area.course.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.master_course.dtos.MasterCourseDTO;
import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.master_course.requests.GetMasterCourseRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface StudentCourseController {
    ResponseEntity<HttpResponseDTO<PaginationUtil<MasterCourseEntity, MasterCourseDTO>>> getPaginationCourse(int page, int perPage, GetMasterCourseRequest instructorCoursePaginationRequest) throws NotFoundException;
    ResponseEntity<HttpResponseDTO<MasterCourseDTO>> getDetailCourse(UUID courseUuid) throws NotFoundException, ForbiddenException;
}