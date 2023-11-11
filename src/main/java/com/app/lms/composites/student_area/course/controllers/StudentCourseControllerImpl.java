package com.app.lms.composites.student_area.course.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.enums.AccountTypeEnum;
import com.app.lms.modules.course.dtos.CourseDTO;
import com.app.lms.modules.course.entities.CourseEntity;
import com.app.lms.modules.course.requests.GetCourseRequest;
import com.app.lms.modules.assessment.GetAssessmentRequest;
import com.app.lms.modules.assessment.dtos.AssessmentDTO;
import com.app.lms.modules.assessment.entities.AssessmentEntity;
import com.app.lms.modules.course.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/student-area/courses")
public class StudentCourseControllerImpl implements StudentCourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<HttpResponseDTO<PaginationUtil<CourseEntity, CourseDTO>>> getPaginationCourse(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int perPage,
            GetCourseRequest masterCourseRequest
    ) throws NotFoundException {
        return new HttpResponseDTO<>(courseService.getPaginationCourseByInstructorUuid(page, perPage, masterCourseRequest, AccountTypeEnum.STUDENT))
                .setResponseHeaders("masterCourseRequest", masterCourseRequest)
                .setResponseHeaders("page", page)
                .setResponseHeaders("perPage", perPage)
                .toResponse("Fetch Course by Pagination from Server");
    }

    @Override
    @GetMapping(value = "/{courseUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<CourseDTO>> getDetailCourse(@PathVariable UUID courseUuid) throws NotFoundException, ForbiddenException {
        return new HttpResponseDTO<>(courseService.getCourseByUuid(courseUuid, AccountTypeEnum.STUDENT))
                .setResponseHeaders("courseUuid", courseUuid)
                .toResponse("Fetched One Course by UUID from Server");
    }

    @Override
    public ResponseEntity<HttpResponseDTO<PaginationUtil<AssessmentEntity, AssessmentDTO>>> getPaginatedAssignmentByCourseUuid(UUID courseUuid, int page, int perPage, GetAssessmentRequest getAssessmentRequest) {
        return null;
    }

}
