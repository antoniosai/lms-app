package com.app.lms.modules.instructor_area.course.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.core.validations.IsNumeric;
import com.app.lms.modules.admin_area.master_course.dtos.MasterCourseDTO;
import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.master_course.requests.GetMasterCourseRequest;
import com.app.lms.modules.instructor_area.course.services.InstructorCourseService;
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
    private InstructorCourseService instructorCourseService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<MasterCourseEntity, MasterCourseDTO>>> getPaginationCourse(
            @RequestParam(defaultValue = "1") @IsNumeric int page,
            @RequestParam(defaultValue = "20") @IsNumeric int perPage,
            GetMasterCourseRequest instructorCoursePaginationRequest
    ) throws NotFoundException {
        return new HttpResponseDTO<>(instructorCourseService.getPaginationCourseByInstructorUuid(page, perPage, instructorCoursePaginationRequest))
                .setResponseHeaders("instructorCoursePaginationRequest", instructorCoursePaginationRequest)
                .setResponseHeaders("page", page)
                .setResponseHeaders("perPage", perPage)
                .toResponse("Fetch Course by Pagination from Server");
    }

    @Override
    @GetMapping(value = "/{courseUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<MasterCourseDTO>> getOneCourseByUuid(@PathVariable UUID courseUuid) throws NotFoundException, ForbiddenException {
        return new HttpResponseDTO<>(instructorCourseService.getCourseByUuid(courseUuid))
                .setResponseHeaders("courseUuid", courseUuid)
                .toResponse("Fetched One Course by UUID from Server");
    }

    @Override
    @PutMapping(value = "/{courseUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<MasterCourseDTO>> updateCourseByUuid(@PathVariable UUID courseUuid, @RequestBody @Valid MasterCourseDTO newCourseData) throws NotFoundException, ForbiddenException {
        return new HttpResponseDTO<>(instructorCourseService.updateCourseByUuid(courseUuid, newCourseData))
                .setResponseHeaders("courseUuid", courseUuid)
                .toResponse("Fetched One Course by UUID from Server");
    }

}
