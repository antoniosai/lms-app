package com.app.lms.modules.admin_area.instructor.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.instructor.dtos.InstructorDTO;
import com.app.lms.modules.admin_area.instructor.entities.InstructorEntity;
import com.app.lms.modules.admin_area.instructor.repositories.InstructorMainRepository;
import com.app.lms.modules.admin_area.instructor.requests.GetInstructorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorMainRepository instructorMainRepository;

    @Override
    public PaginationUtil<InstructorEntity, InstructorDTO> getInstructorByPagination(Integer page, Integer perPage, GetInstructorRequest getInstructorRequest) {

        Pageable paging = PageRequest.of(page - 1, perPage);

        Specification<InstructorEntity> specs = (root, query, builder) ->
            builder.or(
                builder.like(builder.upper(root.get("accountName")), "%" + getInstructorRequest.getInstructorName() + "%")
            );

        Page<InstructorEntity> pagedData = instructorMainRepository.findAll(specs, paging);

        return new PaginationUtil<>(pagedData, InstructorDTO.class);
    }

    @Override
    public InstructorDTO getInstructorByUuid(UUID instructorUuid) throws NotFoundException {
        return null;
    }

    @Override
    public InstructorDTO createNewInstructor(InstructorDTO newInstructorData) {
        return null;
    }

    @Override
    public InstructorDTO updateInstructorByUuid(UUID instructorUuid, InstructorDTO newInstructorData) {
        return null;
    }

    @Override
    public void deleteInstructorByUuid(UUID instructorUuid) {

    }
}
