package com.app.lms.modules.student_area.course.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.master_course.dtos.MasterCourseDTO;
import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.master_course.requests.GetMasterCourseRequest;
import com.app.lms.modules.student_area.course.services.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/student-area/courses")
public class StudentCourseControllerImpl implements StudentCourseController {

    @Autowired
    private StudentCourseService studentCourseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<HttpResponseDTO<PaginationUtil<MasterCourseEntity, MasterCourseDTO>>> getPaginationCourse(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int perPage,
            GetMasterCourseRequest masterCourseRequest
    ) throws NotFoundException {
        return new HttpResponseDTO<>(studentCourseService.getPaginationCourseByInstructorUuid(page, perPage, masterCourseRequest))
                .setResponseHeaders("masterCourseRequest", masterCourseRequest)
                .setResponseHeaders("page", page)
                .setResponseHeaders("perPage", perPage)
                .toResponse("Fetch Course by Pagination from Server");
    }

    @Override
    @GetMapping("/{courseUuid}")
    public ResponseEntity<HttpResponseDTO<MasterCourseDTO>> getDetailCourse(@PathVariable UUID courseUuid) throws NotFoundException, ForbiddenException {
        return new HttpResponseDTO<>(studentCourseService.getCourseByUuid(courseUuid))
                .setResponseHeaders("courseUuid", courseUuid)
                .toResponse("Fetched One Course by UUID from Server");
    }
}
