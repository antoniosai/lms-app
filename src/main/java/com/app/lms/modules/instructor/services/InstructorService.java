package com.app.lms.modules.instructor.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.instructor.dtos.InstructorDTO;
import com.app.lms.modules.instructor.entities.InstructorEntity;
import com.app.lms.modules.instructor.requests.GetInstructorRequest;

import java.util.List;
import java.util.UUID;

public interface InstructorService {

    PaginationUtil<InstructorEntity, InstructorDTO> getInstructorByPagination(int page, int perPage, GetInstructorRequest getInstructorRequest);

    InstructorDTO getInstructorByUuid(UUID instructorUuid) throws NotFoundException;

    InstructorDTO createNewInstructor(InstructorDTO newInstructorData);

    InstructorDTO updateInstructorByUuid(UUID instructorUuid, InstructorDTO newInstructorData);

    List<InstructorDTO> findByAccountUuid(UUID instructorUuid);

    InstructorDTO findOneByAccountUuid(UUID instructorUuid) throws NotFoundException;

    void deleteInstructorByUuid(UUID instructorUuid) throws NotFoundException;
}
