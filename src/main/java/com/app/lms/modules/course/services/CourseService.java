package com.app.lms.modules.course.services;

import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.instructor.dtos.InstructorDTO;
import com.app.lms.modules.course.dtos.CourseDTO;
import com.app.lms.modules.course.entities.CourseEntity;
import com.app.lms.modules.course.requests.GetCourseRequest;
import com.app.lms.modules.student.dtos.StudentDTO;
import com.app.lms.modules.student.entities.StudentEntity;
import com.app.lms.modules.student.requests.GetStudentRequest;

import java.util.UUID;

public interface CourseService {

    PaginationUtil<CourseEntity, CourseDTO> getCourseByPagination(int page, int perPage, GetCourseRequest getMasterCourseRequest);

    CourseDTO getCourseByUuid(UUID courseUuid) throws NotFoundException;

    CourseDTO attachToInstructor(UUID courseUuid, InstructorDTO instructor) throws NotFoundException;

    CourseDTO detachFromInstructor(UUID courseUuid) throws NotFoundException;

    CourseDTO createNewCourse(CourseDTO newCourseData);

    CourseDTO updateCourseByUuid(UUID courseUuid, CourseDTO newCourseData) throws NotFoundException;

    CourseDTO findSingleCourseByUuid(UUID instructorUuid) throws NotFoundException;

    void deleteCourseByUuid(UUID courseUuid);

    // Instructor Service

    PaginationUtil<CourseEntity, CourseDTO> getPaginationCourseByInstructorUuid(int page, int perPage, GetCourseRequest instructorCoursePaginationRequest) throws NotFoundException;

    CourseDTO getCourseByUuidFromInstructor(UUID courseUuid) throws NotFoundException, ForbiddenException;

    CourseDTO updateCourseByUuidFromInstructor(UUID courseUuid, CourseDTO newCourseData) throws NotFoundException, ForbiddenException;

    PaginationUtil<StudentEntity, StudentDTO> getStudentEnrolledFromInstructor(UUID courseUuid, int page, int perPage, GetStudentRequest getStudentRequest) throws ForbiddenException, NotFoundException;

    // Student Area Course

    PaginationUtil<CourseEntity, CourseDTO> getPaginationCourseByInstructorUuidFromStudent(int page, int perPage, GetCourseRequest instructorCoursePaginationRequest) throws NotFoundException;

    CourseDTO getCourseByUuidFromStudent(UUID courseUuid) throws NotFoundException, ForbiddenException;
}
