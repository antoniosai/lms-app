package com.app.lms.modules.admin_area.student.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.*;
import com.app.lms.modules.admin_area.student.dtos.StudentDTO;
import com.app.lms.modules.admin_area.student.entities.StudentEntity;
import com.app.lms.modules.admin_area.student.repositories.StudentMainRepository;
import com.app.lms.modules.admin_area.student.requests.GetStudentRequest;
import com.app.lms.modules.admin_area.student.specifications.StudentSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMainRepository studentMainRepository;

    @Override
    public PaginationUtil<StudentEntity, StudentDTO> getStudentByPagination(int page, int perPage, GetStudentRequest getStudentRequest) {

        Page<StudentEntity> pagedData = studentMainRepository.findAll(
                StudentSpecification.searchByPaginationRequest(getStudentRequest),
                SpecificationUtil.generatePagination(page, perPage)
        );

        return new PaginationUtil<>(pagedData, StudentDTO.class);
    }

    @Override
    public StudentDTO getStudentByUuid(UUID studentUuid) throws NotFoundException {
        return findSingleStudentByUuid(studentUuid);
    }

    @Override
    public StudentDTO createNewStudent(StudentDTO newStudentData) {
        newStudentData.setStudentUuid(UUID.randomUUID());

        log.info("Inserting new Student Data => " + newStudentData);

        StudentEntity newStudent = studentMainRepository.save(ObjectMapperUtil.map(newStudentData, StudentEntity.class));

        return ObjectMapperUtil.map(newStudent, StudentDTO.class);
    }

    @Override
    public StudentDTO updateStudentByUuid(UUID studentUuid, StudentDTO newStudentData) {

        newStudentData.setStudentUuid(studentUuid);

        StudentEntity updatedStudent = studentMainRepository.save(ObjectMapperUtil.map(newStudentData, StudentEntity.class));

        return ObjectMapperUtil.map(updatedStudent, StudentDTO.class);
    }

    @Override
    public void deleteStudentByUuid(UUID studentUuid) throws NotFoundException {
        log.info("Deleting Student with UUID => " + studentUuid);

        findSingleStudentByUuid(studentUuid);

        studentMainRepository.deleteById(studentUuid);
    }

    private StudentDTO findSingleStudentByUuid(UUID studentUuid) throws NotFoundException {
        StudentEntity student = JpaResultHelperUtil.getSingleResultFromOptional(studentMainRepository.findById(studentUuid));

        if(student == null) {
            log.info("Student Not Found");
            throw new NotFoundException("Student Not Found");
        }

        return ObjectMapperUtil.map(student, StudentDTO.class);
    }
}
