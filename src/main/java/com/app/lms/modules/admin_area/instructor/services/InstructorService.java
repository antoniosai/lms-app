package com.app.lms.modules.admin_area.instructor.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.instructor.dtos.InstructorDTO;
import com.app.lms.modules.admin_area.instructor.entities.InstructorEntity;
import com.app.lms.modules.admin_area.instructor.requests.GetInstructorRequest;

import java.util.UUID;

public interface InstructorService {

    PaginationUtil<InstructorEntity, InstructorDTO> getInstructorByPagination(Integer page, Integer perPage, GetInstructorRequest getInstructorRequest);
    InstructorDTO getInstructorByUuid(UUID instructorUuid) throws NotFoundException;
    InstructorDTO createNewInstructor(InstructorDTO newInstructorData);
    InstructorDTO updateInstructorByUuid(UUID instructorUuid, InstructorDTO newInstructorData);
    void deleteInstructorByUuid(UUID instructorUuid);
}
