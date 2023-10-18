package com.app.lms.modules.admin_area.instructor.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.instructor.dtos.InstructorDTO;
import com.app.lms.modules.admin_area.instructor.entities.InstructorEntity;
import com.app.lms.modules.admin_area.instructor.requests.GetInstructorRequest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface InstructorController {
    ResponseEntity<HttpResponseDTO<PaginationUtil<InstructorEntity, InstructorDTO>>> getInstructorByPagination(int page, int perPage, GetInstructorRequest getInstructorRequest);

    ResponseEntity<HttpResponseDTO<InstructorDTO>> getInstructorById(UUID instructorUuid) throws NotFoundException;

    ResponseEntity<HttpResponseDTO<InstructorDTO>> createNewInstructor(InstructorDTO newInstructorData);

    ResponseEntity<HttpResponseDTO<InstructorDTO>> updateInstructorByUuid(UUID instructorUuid, InstructorDTO newInstructorData);

    ResponseEntity<HttpResponseDTO<String>> deleteInstructorByUuid(UUID instructorUuid) throws NotFoundException;
}
