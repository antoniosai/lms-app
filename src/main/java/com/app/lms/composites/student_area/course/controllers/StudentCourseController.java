package com.app.lms.composites.student_area.course.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.assessment.GetAssessmentRequest;
import com.app.lms.modules.assessment.dtos.AssessmentDTO;
import com.app.lms.modules.assessment.entities.AssessmentEntity;
import com.app.lms.modules.course.dtos.CourseDTO;
import com.app.lms.modules.course.entities.CourseEntity;
import com.app.lms.modules.course.requests.GetCourseRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface StudentCourseController {
    ResponseEntity<HttpResponseDTO<PaginationUtil<CourseEntity, CourseDTO>>> getPaginationCourse(int page, int perPage, GetCourseRequest instructorCoursePaginationRequest) throws NotFoundException;

    ResponseEntity<HttpResponseDTO<CourseDTO>> getDetailCourse(UUID courseUuid) throws NotFoundException, ForbiddenException;

    ResponseEntity<HttpResponseDTO<PaginationUtil<AssessmentEntity, AssessmentDTO>>> getPaginatedAssignmentByCourseUuid(UUID courseUuid, int page, int perPage, GetAssessmentRequest getAssessmentRequest);
}
