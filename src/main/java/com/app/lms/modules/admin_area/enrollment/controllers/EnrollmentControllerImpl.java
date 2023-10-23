package com.app.lms.modules.admin_area.enrollment.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.modules.admin_area.enrollment.services.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/administrator-area/enrollments")
public class EnrollmentControllerImpl implements EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;


    @Override
    @PutMapping("/course/{courseUuid}/student/{studentUuid}")
    public ResponseEntity<HttpResponseDTO<String>> addStudentEnrollment(@PathVariable UUID courseUuid, @PathVariable UUID studentUuid) throws NotFoundException {

        enrollmentService.addStudentEnrollment(courseUuid, studentUuid);

        return new HttpResponseDTO<>("Successfully Enrolled Course to Student")
                .setResponseHeaders("courseUuid", courseUuid)
                .setResponseHeaders("studentUuid", studentUuid)
                .toResponse("Successfully Enrolled Course to Student");
    }

    @Override
    @DeleteMapping("/{enrollmentUuid}")
    public ResponseEntity<HttpResponseDTO<String>> removeStudentEnrollment(@PathVariable UUID enrollmentUuid) {

        enrollmentService.removeStudentEnrollment(enrollmentUuid);

        return new HttpResponseDTO<>("Successfully Remove Student Enrollment")
                .setResponseHeaders("enrollmentUuid", enrollmentUuid)
                .toResponse("Successfully Remove Student Enrollment");
    }
}
