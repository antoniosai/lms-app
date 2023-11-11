package com.app.lms.modules.course.services;

import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.enums.AccountTypeEnum;
import com.app.lms.modules.course.dtos.CourseDTO;
import com.app.lms.modules.course.entities.CourseEntity;
import com.app.lms.modules.course.requests.GetCourseRequest;
import com.app.lms.modules.instructor.dtos.InstructorDTO;

import java.util.UUID;

public interface CourseService {

    PaginationUtil<CourseEntity, CourseDTO> getCourseByPagination(int page, int perPage, GetCourseRequest getMasterCourseRequest);

    CourseDTO getCourseByUuid(UUID courseUuid) throws NotFoundException;

    CourseDTO attachToInstructor(UUID courseUuid, InstructorDTO instructor) throws NotFoundException;

    CourseDTO detachFromInstructor(UUID courseUuid) throws NotFoundException;

    CourseDTO createNewCourse(CourseDTO newCourseData);

    CourseDTO updateCourseByUuid(UUID courseUuid, CourseDTO newCourseData) throws NotFoundException;

    CourseDTO updateCourseByUuid(UUID courseUuid, CourseDTO newCourseData, AccountTypeEnum accountType) throws NotFoundException, ForbiddenException;

    CourseDTO findSingleCourseByUuid(UUID instructorUuid) throws NotFoundException;

    void deleteCourseByUuid(UUID courseUuid);

    // Instructor Service

    PaginationUtil<CourseEntity, CourseDTO> getPaginationCourseByInstructorUuid(int page, int perPage, GetCourseRequest instructorCoursePaginationRequest) throws NotFoundException;

    PaginationUtil<CourseEntity, CourseDTO> getPaginationCourseByInstructorUuid(int page, int perPage, GetCourseRequest instructorCoursePaginationRequest, AccountTypeEnum accountTypeEnum) throws NotFoundException;

    CourseDTO getCourseByUuid(UUID courseUuid, AccountTypeEnum accountType) throws NotFoundException, ForbiddenException;

    // Student Area Course
}
