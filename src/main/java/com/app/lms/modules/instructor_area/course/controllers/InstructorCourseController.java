package com.app.lms.modules.instructor_area.course.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.master_course.dtos.MasterCourseDTO;
import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.master_course.requests.GetMasterCourseRequest;
import com.app.lms.modules.admin_area.student.dtos.StudentDTO;
import com.app.lms.modules.admin_area.student.entities.StudentEntity;
import com.app.lms.modules.admin_area.student.requests.GetStudentRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface InstructorCourseController {
    ResponseEntity<HttpResponseDTO<PaginationUtil<MasterCourseEntity, MasterCourseDTO>>> getPaginationCourse(int page, int perPage, GetMasterCourseRequest instructorCoursePaginationRequest) throws NotFoundException;

    ResponseEntity<HttpResponseDTO<MasterCourseDTO>> getOneCourseByUuid(UUID courseUuid) throws NotFoundException, ForbiddenException;

    ResponseEntity<HttpResponseDTO<MasterCourseDTO>> updateCourseByUuid(UUID courseUuid, MasterCourseDTO newCourseData) throws NotFoundException, ForbiddenException;

    ResponseEntity<HttpResponseDTO<PaginationUtil<StudentEntity, StudentDTO>>> getEnrolledStudents(UUID courseUuid, int page, int perPage, GetStudentRequest getStudentRequest) throws ForbiddenException, NotFoundException;

}
