package com.app.lms.modules.admin_area.student.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.student.dtos.StudentDTO;
import com.app.lms.modules.admin_area.student.entities.StudentEntity;
import com.app.lms.modules.admin_area.student.requests.GetStudentRequest;

import java.util.UUID;

public interface StudentService {

    PaginationUtil<StudentEntity, StudentDTO> getStudentByPagination(int page, int perPage, GetStudentRequest getStudentRequest);
    StudentDTO getStudentByUuid(UUID studentUuid) throws NotFoundException;
    StudentDTO createNewStudent(StudentDTO newStudentData);
    StudentDTO updateStudentByUuid(UUID studentUuid, StudentDTO newStudentData);
    void deleteStudentByUuid(UUID studentUuid) throws NotFoundException;
}
