package com.app.lms.composites.administrator_area.instructor.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.core.validations.IsNumeric;
import com.app.lms.modules.instructor.dtos.InstructorDTO;
import com.app.lms.modules.instructor.entities.InstructorEntity;
import com.app.lms.modules.instructor.requests.GetInstructorRequest;
import com.app.lms.modules.instructor.services.InstructorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/administrator-area/instructors")
public class InstructorControllerImpl implements InstructorController {
    @Autowired
    private InstructorService instructorService;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<PaginationUtil<InstructorEntity, InstructorDTO>>> getInstructorByPagination(
            @RequestParam(defaultValue = "1") @IsNumeric int page,
            @RequestParam(defaultValue = "20") @IsNumeric int perPage,
            GetInstructorRequest getInstructorRequest
    ) {
        return new HttpResponseDTO<>(instructorService.getInstructorByPagination(page, perPage, getInstructorRequest))
                .setResponseHeaders("getInstructorRequest", getInstructorRequest)
                .setResponseHeaders("page", page)
                .setResponseHeaders("perPage", perPage)
                .toResponse("Fetch Instructor by Pagination from Server");
    }

    @Override
    @GetMapping(value = "/{instructorUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<InstructorDTO>> getInstructorById(@PathVariable UUID instructorUuid) throws NotFoundException {
        return new HttpResponseDTO<>(instructorService.getInstructorByUuid(instructorUuid))
                .setResponseHeaders("instructorUuid", instructorUuid)
                .toResponse("Fetch Single Instructor by UUID from Server");
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<InstructorDTO>> createNewInstructor(@Valid @RequestBody InstructorDTO newInstructorData) {
        return new HttpResponseDTO<>(instructorService.createNewInstructor(newInstructorData), HttpStatus.CREATED)
                .setResponseHeaders("newInstructorData", newInstructorData)
                .toResponse("Successfully Created a Instructor");
    }

    @Override
    @PutMapping(value = "/{instructorUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<InstructorDTO>> updateInstructorByUuid(@PathVariable UUID instructorUuid, @RequestBody @Valid InstructorDTO newInstructorData) {
        return new HttpResponseDTO<>(instructorService.updateInstructorByUuid(instructorUuid, newInstructorData))
                .setResponseHeaders("newInstructorData", newInstructorData)
                .toResponse("Successfully Updated a Instructor By UUID from Server");
    }

    @Override
    @DeleteMapping(value = "/{instructorUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<String>> deleteInstructorByUuid(@PathVariable UUID instructorUuid) throws NotFoundException {
        instructorService.deleteInstructorByUuid(instructorUuid);
        return new HttpResponseDTO<>("Instructor has been Deleted")
                .setResponseHeaders("instructorUuid", instructorUuid)
                .toResponse("Successfully Deleted a Instructor By UUID from Server");
    }
}
