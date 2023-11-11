package com.app.lms.modules.student.services;

import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.JpaResultHelperUtil;
import com.app.lms.core.utils.ObjectMapperUtil;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.core.utils.SpecificationUtil;
import com.app.lms.modules.course.dtos.CourseDTO;
import com.app.lms.modules.course.services.CourseService;
import com.app.lms.modules.enrollment.services.EnrollmentService;
import com.app.lms.modules.student.dtos.StudentDTO;
import com.app.lms.modules.student.entities.StudentEntity;
import com.app.lms.modules.student.repositories.StudentMainRepository;
import com.app.lms.modules.student.requests.GetStudentRequest;
import com.app.lms.modules.student.specifications.StudentSpecification;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMainRepository studentMainRepository;
    @Autowired
    private EnrollmentService enrollmentService;

    @Override
    public PaginationUtil<StudentEntity, StudentDTO> getStudentByPagination(int page, int perPage, GetStudentRequest getStudentRequest) {

        Page<StudentEntity> pagedData = studentMainRepository.findAll(
                StudentSpecification.searchByPaginationRequest(getStudentRequest),
                SpecificationUtil.generatePagination(page, perPage)
        );

        return new PaginationUtil<>(pagedData, StudentDTO.class);
    }


    @Override
    public PaginationUtil<StudentEntity, StudentDTO> getStudentByPagination(CourseDTO course, int page, int perPage, UUID instructorUuid, GetStudentRequest getStudentRequest) throws ForbiddenException, NotFoundException {

        if (!Objects.equals(course.getCourseInstructor().getInstructorUuid(), instructorUuid)) {
            throw new ForbiddenException("You are not Allowed to Access Enrolled Student on this Course");
        }

        getStudentRequest.setStudentUuid(enrollmentService.findStudentUuidByCourseUuid(course.getCourseUuid()));

        return getStudentByPagination(page, perPage, getStudentRequest);
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
    public List<StudentDTO> findByAccountUuid(UUID studentUuid) {
        return ObjectMapperUtil.mapAll(studentMainRepository.findByAccountUuid(studentUuid), StudentDTO.class);
    }

    @Override
    public StudentDTO findOneByAccountUuid(UUID accountUuid) throws NotFoundException {
        List<StudentDTO> students = this.findByAccountUuid(accountUuid);

        if (students.isEmpty()) {
            throw new NotFoundException("No Student Found");
        }

        return students.get(0);
    }

    @Override
    public void deleteStudentByUuid(UUID studentUuid) throws NotFoundException {
        log.info("Deleting Student with UUID => " + studentUuid);

        findSingleStudentByUuid(studentUuid);

        // Delete Account ID

        studentMainRepository.deleteById(studentUuid);
    }

    @Override
    @Transactional
    public void deleteStudentByIdentificationNUmber(String studentIdentificationNumber) {
        studentMainRepository.deleteByStudentIdentificationNumber(studentIdentificationNumber);
    }

    private StudentDTO findSingleStudentByUuid(UUID studentUuid) throws NotFoundException {
        StudentEntity student = JpaResultHelperUtil.getSingleResultFromOptional(studentMainRepository.findById(studentUuid));

        if (student == null) {
            log.info("Student Not Found");
            throw new NotFoundException("Student Not Found");
        }

        return ObjectMapperUtil.map(student, StudentDTO.class);
    }
}
