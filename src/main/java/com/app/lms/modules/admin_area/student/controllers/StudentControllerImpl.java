package com.app.lms.modules.admin_area.student.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.core.validations.IsNumeric;
import com.app.lms.modules.admin_area.student.dtos.StudentDTO;
import com.app.lms.modules.admin_area.student.entities.StudentEntity;
import com.app.lms.modules.admin_area.student.requests.GetStudentRequest;
import com.app.lms.modules.admin_area.student.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/administrator-area/students")
@Secured("ADMINISTRATOR")
public class StudentControllerImpl implements StudentController {

    @Autowired
    private StudentService studentService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<StudentEntity, StudentDTO>>> getStudentByPagination(
            @RequestParam(defaultValue = "1") @IsNumeric int page,
            @RequestParam(defaultValue = "20") @IsNumeric int perPage,
            GetStudentRequest getStudentRequest
    ) {
        return new HttpResponseDTO<>(studentService.getStudentByPagination(page, perPage, getStudentRequest))
                .setResponseHeaders("getStudentRequest", getStudentRequest)
                .setResponseHeaders("page", page)
                .setResponseHeaders("perPage", perPage)
                .toResponse("Fetch Student by Pagination from Server");
    }

    @Override
    @Secured("ADMINISTRATOR")
    @GetMapping(value = "/{studentUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<StudentDTO>> getStudentById(@PathVariable UUID studentUuid) throws NotFoundException {
        return new HttpResponseDTO<>(studentService.getStudentByUuid(studentUuid))
                .setResponseHeaders("studentUuid", studentUuid)
                .toResponse("Fetch Single Student by UUID from Server");
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<StudentDTO>> createNewStudent(@Valid @RequestBody StudentDTO newStudentData) {
        return new HttpResponseDTO<>(studentService.createNewStudent(newStudentData), HttpStatus.CREATED)
                .setResponseHeaders("newStudentData", newStudentData)
                .toResponse("Successfully Created a Student");
    }

    @Override
    @PutMapping(value = "/{studentUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<StudentDTO>> updateStudentByUuid(@PathVariable UUID studentUuid, @RequestBody @Valid StudentDTO newStudentData) {
        return new HttpResponseDTO<>(studentService.updateStudentByUuid(studentUuid, newStudentData))
                .setResponseHeaders("newStudentData", newStudentData)
                .toResponse("Successfully Updated a Student By UUID from Server");
    }

    @Override
    @DeleteMapping(value = "/{studentUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<String>> deleteStudentByUuid(@PathVariable UUID studentUuid) throws NotFoundException {
        studentService.deleteStudentByUuid(studentUuid);
        return new HttpResponseDTO<>("Student has been Deleted")
                .setResponseHeaders("studentUuid", studentUuid)
                .toResponse("Successfully Deleted a Student By UUID from Server");
    }

}
