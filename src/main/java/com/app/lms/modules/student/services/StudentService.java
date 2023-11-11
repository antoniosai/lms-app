package com.app.lms.modules.student.services;

import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.course.dtos.CourseDTO;
import com.app.lms.modules.student.dtos.StudentDTO;
import com.app.lms.modules.student.entities.StudentEntity;
import com.app.lms.modules.student.requests.GetStudentRequest;

import java.util.List;
import java.util.UUID;

public interface StudentService {

    PaginationUtil<StudentEntity, StudentDTO> getStudentByPagination(int page, int perPage, GetStudentRequest getStudentRequest);

    PaginationUtil<StudentEntity, StudentDTO> getStudentByPagination(CourseDTO course, int page, int perPage, UUID instructorUuid, GetStudentRequest getStudentRequest) throws ForbiddenException, NotFoundException;

    StudentDTO getStudentByUuid(UUID studentUuid) throws NotFoundException;

    StudentDTO createNewStudent(StudentDTO newStudentData);

    StudentDTO updateStudentByUuid(UUID studentUuid, StudentDTO newStudentData);

    List<StudentDTO> findByAccountUuid(UUID accountUuid);

    StudentDTO findOneByAccountUuid(UUID accountUuid) throws NotFoundException;

    void deleteStudentByUuid(UUID studentUuid) throws NotFoundException;

    void deleteStudentByIdentificationNUmber(String studentIdentificationNumber);
}
