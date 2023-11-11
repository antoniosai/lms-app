package com.app.lms.composites.administrator_area.enrollment.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface EnrollmentController {

    ResponseEntity<HttpResponseDTO<String>> addStudentEnrollment(UUID courseUuid, UUID studentUuid) throws NotFoundException;

    ResponseEntity<HttpResponseDTO<String>> removeStudentEnrollment(UUID enrollmentUuid);

}
