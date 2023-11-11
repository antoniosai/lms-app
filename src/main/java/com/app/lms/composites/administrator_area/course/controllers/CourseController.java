package com.app.lms.composites.administrator_area.course.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.instructor.dtos.InstructorDTO;
import com.app.lms.modules.course.dtos.CourseDTO;
import com.app.lms.modules.course.entities.CourseEntity;
import com.app.lms.modules.course.requests.GetCourseRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CourseController {
    ResponseEntity<HttpResponseDTO<PaginationUtil<CourseEntity, CourseDTO>>> getCourseByPagination(int page, int perPage, GetCourseRequest getMasterCourseRequest);

    ResponseEntity<HttpResponseDTO<CourseDTO>> getCourseById(UUID courseUuid) throws NotFoundException;

    ResponseEntity<HttpResponseDTO<CourseDTO>> createNewCourse(CourseDTO newCourseData);

    ResponseEntity<HttpResponseDTO<CourseDTO>> updateCourseByUuid(UUID courseUuid, CourseDTO newCourseData) throws NotFoundException;

    ResponseEntity<HttpResponseDTO<CourseDTO>> attachToInstructor(UUID courseUuid, UUID instructorUuid) throws NotFoundException;

    ResponseEntity<HttpResponseDTO<CourseDTO>> detachFromInstructor(UUID courseUuid) throws NotFoundException;

    ResponseEntity<HttpResponseDTO<String>> deleteCourseByUuid(UUID courseUuid);

}
