package com.app.lms.composites.administrator_area.course;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.core.validations.IsNumeric;
import com.app.lms.modules.course.dtos.CourseDTO;
import com.app.lms.modules.course.entities.CourseEntity;
import com.app.lms.modules.course.requests.GetCourseRequest;
import com.app.lms.modules.course.services.CourseService;
import com.app.lms.modules.instructor.dtos.InstructorDTO;
import com.app.lms.modules.instructor.services.InstructorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/administrator-area/courses")
public class CourseControllerImpl implements CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private InstructorService instructorService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<CourseEntity, CourseDTO>>> getCourseByPagination(
            @RequestParam(defaultValue = "1") @IsNumeric int page,
            @RequestParam(defaultValue = "20") @IsNumeric int perPage,
            GetCourseRequest getMasterCourseRequest) {
        return new HttpResponseDTO<>(courseService.getCourseByPagination(page, perPage, getMasterCourseRequest))
                .setResponseHeaders("getMasterCourseRequest", getMasterCourseRequest)
                .setResponseHeaders("page", page)
                .setResponseHeaders("perPage", perPage)
                .toResponse("Fetch Course by Pagination from Server");
    }

    @Override
    @GetMapping(value = "/{courseUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<CourseDTO>> getCourseById(@PathVariable UUID courseUuid) throws NotFoundException {
        return new HttpResponseDTO<>(courseService.getCourseByUuid(courseUuid))
                .setResponseHeaders("courseUuid", courseUuid)
                .toResponse("Fetch Single Course by Pagination from Server");
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<CourseDTO>> createNewCourse(@Valid @RequestBody CourseDTO newCourseData) {
        return new HttpResponseDTO<>(courseService.createNewCourse(newCourseData), HttpStatus.CREATED)
                .setResponseHeaders("newCourseData", newCourseData)
                .toResponse("Created New Course");
    }

    @Override
    @PutMapping(value = "/{courseUuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<CourseDTO>> updateCourseByUuid(@PathVariable UUID courseUuid, @Valid @RequestBody CourseDTO newCourseData) throws NotFoundException {
        return new HttpResponseDTO<>(courseService.updateCourseByUuid(courseUuid, newCourseData))
                .setResponseHeaders("newCourseData", newCourseData)
                .toResponse("Updated Course by UUID");
    }

    @Override
    @PutMapping(value = "/{courseUuid}/attach-instructor/{instructorUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<CourseDTO>> attachToInstructor(@PathVariable UUID courseUuid, @PathVariable UUID instructorUuid) throws NotFoundException {

        InstructorDTO instructor = instructorService.getInstructorByUuid(instructorUuid);

        return new HttpResponseDTO<>(courseService.attachToInstructor(courseUuid, instructor))
                .setResponseHeaders("instructorUuid", instructorUuid)
                .setResponseHeaders("courseUuid", courseUuid)
                .toResponse("Attached Course to Instructor " + instructor.getInstructorName());
    }

    @Override
    @PutMapping(value = "/{courseUuid}/detach-instructor", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<CourseDTO>> detachFromInstructor(@PathVariable UUID courseUuid) throws NotFoundException {
        return new HttpResponseDTO<>(courseService.detachFromInstructor(courseUuid))
                .setResponseHeaders("courseUuid", courseUuid)
                .toResponse("Detached Course by UUID");
    }

    @Override
    @DeleteMapping(value = "/{courseUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<String>> deleteCourseByUuid(@PathVariable UUID courseUuid) {
        courseService.deleteCourseByUuid(courseUuid);
        return new HttpResponseDTO<>("Successfully Deleted")
                .setResponseHeaders("courseUuid", courseUuid)
                .toResponse("Successfully Deleted Course by UUID");
    }
}
