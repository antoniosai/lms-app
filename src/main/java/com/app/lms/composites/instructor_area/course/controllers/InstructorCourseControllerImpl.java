package com.app.lms.composites.instructor_area.course.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.core.validations.IsNumeric;
import com.app.lms.modules.course.dtos.CourseDTO;
import com.app.lms.modules.course.entities.CourseEntity;
import com.app.lms.modules.course.requests.GetCourseRequest;
import com.app.lms.modules.course.services.CourseService;
import com.app.lms.modules.profile.services.ProfileService;
import com.app.lms.modules.student.dtos.StudentDTO;
import com.app.lms.modules.student.entities.StudentEntity;
import com.app.lms.modules.student.requests.GetStudentRequest;
import com.app.lms.modules.student.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/instructor-area/courses")
public class InstructorCourseControllerImpl implements InstructorCourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ProfileService profileService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<CourseEntity, CourseDTO>>> getPaginationCourse(
            @RequestParam(defaultValue = "1") @IsNumeric int page,
            @RequestParam(defaultValue = "20") @IsNumeric int perPage,
            GetCourseRequest instructorCoursePaginationRequest
    ) throws NotFoundException {
        return new HttpResponseDTO<>(courseService.getPaginationCourseByInstructorUuid(page, perPage, instructorCoursePaginationRequest))
                .setResponseHeaders("instructorCoursePaginationRequest", instructorCoursePaginationRequest)
                .setResponseHeaders("page", page)
                .setResponseHeaders("perPage", perPage)
                .toResponse("Fetch Course by Pagination from Server");
    }

    @Override
    @GetMapping(value = "/{courseUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<CourseDTO>> getOneCourseByUuid(@PathVariable UUID courseUuid) throws NotFoundException, ForbiddenException {
        return new HttpResponseDTO<>(courseService.getCourseByUuid(courseUuid))
                .setResponseHeaders("courseUuid", courseUuid)
                .toResponse("Fetched One Course by UUID from Server");
    }

    @Override
    @PutMapping(value = "/{courseUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<CourseDTO>> updateCourseByUuid(@PathVariable UUID courseUuid, @RequestBody @Valid CourseDTO newCourseData) throws NotFoundException, ForbiddenException {
        return new HttpResponseDTO<>(courseService.updateCourseByUuid(courseUuid, newCourseData))
                .setResponseHeaders("courseUuid", courseUuid)
                .toResponse("Fetched One Course by UUID from Server");
    }

    @Override
    @GetMapping(value = "/{courseUuid}/student", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<StudentEntity, StudentDTO>>> getEnrolledStudents(
            @PathVariable UUID courseUuid,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int perPage,
            GetStudentRequest getStudentRequest
    ) throws ForbiddenException, NotFoundException {

        CourseDTO course = courseService.getCourseByUuid(courseUuid);
        return new HttpResponseDTO<>(studentService.getStudentByPagination(course, page, perPage, profileService.getCurrentInstructor().getInstructorUuid(), getStudentRequest))
                .setResponseHeaders("courseUuid", courseUuid)
                .setResponseHeaders("page", page)
                .setResponseHeaders("perPage", perPage)
                .toResponse("See Enrolled Student");
    }

}
