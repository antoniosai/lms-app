package com.app.lms.modules.admin_area.instructor.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.JpaResultHelperUtil;
import com.app.lms.core.utils.ObjectMapperUtil;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.core.utils.SpecificationUtil;
import com.app.lms.modules.admin_area.instructor.dtos.InstructorDTO;
import com.app.lms.modules.admin_area.instructor.entities.InstructorEntity;
import com.app.lms.modules.admin_area.instructor.repositories.InstructorMainRepository;
import com.app.lms.modules.admin_area.instructor.requests.GetInstructorRequest;
import com.app.lms.modules.admin_area.instructor.specifications.InstructorSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorMainRepository instructorMainRepository;

    @Override
    public PaginationUtil<InstructorEntity, InstructorDTO> getInstructorByPagination(int page, int perPage, GetInstructorRequest getInstructorRequest) {

        Page<InstructorEntity> pagedData = instructorMainRepository.findAll(
                InstructorSpecification.searchByPaginationRequest(getInstructorRequest),
                SpecificationUtil.generatePagination(page, perPage)
        );

        return new PaginationUtil<>(pagedData, InstructorDTO.class);
    }

    @Override
    public InstructorDTO getInstructorByUuid(UUID instructorUuid) throws NotFoundException {
        return findSingleInstructorByUuid(instructorUuid);
    }

    @Override
    public InstructorDTO createNewInstructor(InstructorDTO newInstructorData) {
        newInstructorData.setInstructorUuid(UUID.randomUUID());

        log.info("Inserting new Instructor Data => " + newInstructorData);

        InstructorEntity newInstructor = instructorMainRepository.save(ObjectMapperUtil.map(newInstructorData, InstructorEntity.class));

        return ObjectMapperUtil.map(newInstructor, InstructorDTO.class);
    }

    @Override
    public InstructorDTO updateInstructorByUuid(UUID instructorUuid, InstructorDTO newInstructorData) {

        newInstructorData.setInstructorUuid(instructorUuid);

        InstructorEntity updatedInstructor = instructorMainRepository.save(ObjectMapperUtil.map(newInstructorData, InstructorEntity.class));

        return ObjectMapperUtil.map(updatedInstructor, InstructorDTO.class);
    }

    @Override
    public List<InstructorDTO> findByAccountUuid(UUID instructorUuid) {
        return ObjectMapperUtil.mapAll(instructorMainRepository.findByAccountUuid(instructorUuid), InstructorDTO.class);
    }

    @Override
    public void deleteInstructorByUuid(UUID instructorUuid) throws NotFoundException {
        log.info("Deleting Instructor with UUID => " + instructorUuid);

        findSingleInstructorByUuid(instructorUuid);

        // Delete Account ID

        instructorMainRepository.deleteById(instructorUuid);
    }

    private InstructorDTO findSingleInstructorByUuid(UUID instructorUuid) throws NotFoundException {
        InstructorEntity instructor = JpaResultHelperUtil.getSingleResultFromOptional(instructorMainRepository.findById(instructorUuid));

        if(instructor == null) {
            log.info("Instructor Not Found");
            throw new NotFoundException("Instructor Not Found");
        }

        return ObjectMapperUtil.map(instructor, InstructorDTO.class);
    }
}
