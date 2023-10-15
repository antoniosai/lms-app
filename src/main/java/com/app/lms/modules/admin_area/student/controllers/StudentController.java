package com.app.lms.modules.admin_area.student.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.student.dtos.StudentDTO;
import com.app.lms.modules.admin_area.student.entities.StudentEntity;
import com.app.lms.modules.admin_area.student.requests.GetStudentRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface StudentController {
    ResponseEntity<HttpResponseDTO<PaginationUtil<StudentEntity, StudentDTO>>> getStudentByPagination(int page, int perPage, GetStudentRequest getStudentRequest);
    ResponseEntity<HttpResponseDTO<StudentDTO>> getStudentById(UUID studentUuid) throws NotFoundException;
    ResponseEntity<HttpResponseDTO<StudentDTO>> createNewStudent(StudentDTO newStudentData);
    ResponseEntity<HttpResponseDTO<StudentDTO>> updateStudentByUuid(UUID studentUuid, StudentDTO newStudentData);
    ResponseEntity<HttpResponseDTO<String>> deleteStudentByUuid(UUID studentUuid) throws NotFoundException;
}
