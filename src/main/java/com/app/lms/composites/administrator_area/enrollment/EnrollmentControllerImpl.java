package com.app.lms.composites.administrator_area.enrollment;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.modules.course.services.CourseService;
import com.app.lms.modules.enrollment.services.EnrollmentService;
import com.app.lms.modules.student.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/administrator-area/enrollments")
public class EnrollmentControllerImpl implements EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @Override
    @PutMapping("/course/{courseUuid}/student/{studentUuid}")
    public ResponseEntity<HttpResponseDTO<String>> addStudentEnrollment(@PathVariable UUID courseUuid, @PathVariable UUID studentUuid) throws NotFoundException {

        try {
            enrollmentService.addStudentEnrollment(courseService.getCourseByUuid(courseUuid), studentService.getStudentByUuid(studentUuid));

            return new HttpResponseDTO<>("Successfully Enrolled Course to Student")
                    .setResponseHeaders("courseUuid", courseUuid)
                    .setResponseHeaders("studentUuid", studentUuid)
                    .toResponse("Successfully Enrolled Course to Student");
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(ex.getMessage());
        }
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
