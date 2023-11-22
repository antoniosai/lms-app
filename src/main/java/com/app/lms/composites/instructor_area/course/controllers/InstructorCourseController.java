package com.app.lms.composites.instructor_area.course.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.course.dtos.CourseDTO;
import com.app.lms.modules.course.entities.CourseEntity;
import com.app.lms.modules.course.requests.GetCourseRequest;
import com.app.lms.modules.student.dtos.StudentDTO;
import com.app.lms.modules.student.entities.StudentEntity;
import com.app.lms.modules.student.requests.GetStudentRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface InstructorCourseController {
    ResponseEntity<HttpResponseDTO<PaginationUtil<CourseEntity, CourseDTO>>> getPaginationCourse(int page, int perPage, GetCourseRequest instructorCoursePaginationRequest) throws NotFoundException;

    ResponseEntity<HttpResponseDTO<CourseDTO>> getOneCourseByUuid(UUID courseUuid) throws NotFoundException, ForbiddenException;

    ResponseEntity<HttpResponseDTO<CourseDTO>> updateCourseByUuid(UUID courseUuid, CourseDTO newCourseData) throws NotFoundException, ForbiddenException;

    ResponseEntity<HttpResponseDTO<PaginationUtil<StudentEntity, StudentDTO>>> getEnrolledStudents(UUID courseUuid, int page, int perPage, GetStudentRequest getStudentRequest) throws ForbiddenException, NotFoundException;

}
