package com.app.lms.modules.admin_area.master_course.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.core.validations.IsNumeric;
import com.app.lms.modules.admin_area.instructor.dtos.InstructorDTO;
import com.app.lms.modules.admin_area.master_course.dtos.MasterCourseDTO;
import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.master_course.requests.GetMasterCourseRequest;
import com.app.lms.modules.admin_area.master_course.services.MasterCourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/administrator-area/courses")
public class MasterCourseControllerImpl implements MasterCourseController {

    @Autowired
    private MasterCourseService masterCourseService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<MasterCourseEntity, MasterCourseDTO>>> getCourseByPagination(
            @RequestParam(defaultValue = "1") @IsNumeric int page,
            @RequestParam(defaultValue = "20") @IsNumeric int perPage,
            GetMasterCourseRequest getMasterCourseRequest) {
        return new HttpResponseDTO<>(masterCourseService.getCourseByPagination(page, perPage, getMasterCourseRequest))
                .setResponseHeaders("getMasterCourseRequest", getMasterCourseRequest)
                .setResponseHeaders("page", page)
                .setResponseHeaders("perPage", perPage)
                .toResponse("Fetch Course by Pagination from Server");
    }

    @Override
    @GetMapping(value = "/{courseUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<MasterCourseDTO>> getCourseById(@PathVariable UUID courseUuid) throws NotFoundException {
        return new HttpResponseDTO<>(masterCourseService.getCourseByUuid(courseUuid))
                .setResponseHeaders("courseUuid", courseUuid)
                .toResponse("Fetch Single Course by Pagination from Server");
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<MasterCourseDTO>> createNewCourse(@Valid @RequestBody MasterCourseDTO newCourseData) {
        return new HttpResponseDTO<>(masterCourseService.createNewCourse(newCourseData), HttpStatus.CREATED)
                .setResponseHeaders("newCourseData", newCourseData)
                .toResponse("Created New Course");
    }

    @Override
    @PutMapping(value = "/{courseUuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<MasterCourseDTO>> updateCourseByUuid(@PathVariable UUID courseUuid, @Valid @RequestBody MasterCourseDTO newCourseData) throws NotFoundException {
        return new HttpResponseDTO<>(masterCourseService.updateCourseByUuid(courseUuid, newCourseData))
                .setResponseHeaders("newCourseData", newCourseData)
                .toResponse("Updated Course by UUID");
    }

    @Override
    @PutMapping(value = "/{courseUuid}/attach-instructor", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<MasterCourseDTO>> attachToInstructor(@PathVariable UUID courseUuid, @RequestBody @Valid InstructorDTO instructor) throws NotFoundException {
        return new HttpResponseDTO<>(masterCourseService.attachToInstructor(courseUuid, instructor))
                .setResponseHeaders("instructor", instructor)
                .setResponseHeaders("courseUuid", courseUuid)
                .toResponse("Attached Course to Instructor " + instructor.getInstructorName());
    }

    @Override
    @PutMapping(value = "/{courseUuid}/detach-instructor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<MasterCourseDTO>> detachFromInstructor(@PathVariable UUID courseUuid) throws NotFoundException {
        return new HttpResponseDTO<>(masterCourseService.detachFromInstructor(courseUuid))
                .setResponseHeaders("courseUuid", courseUuid)
                .toResponse("Detached Course by UUID");
    }

    @Override
    @DeleteMapping(value = "/{courseUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<String>> deleteCourseByUuid(@PathVariable UUID courseUuid) {
        masterCourseService.deleteCourseByUuid(courseUuid);
        return new HttpResponseDTO<>("Successfully Deleted")
                .setResponseHeaders("courseUuid", courseUuid)
                .toResponse("Successfully Deleted Course by UUID");
    }
}
