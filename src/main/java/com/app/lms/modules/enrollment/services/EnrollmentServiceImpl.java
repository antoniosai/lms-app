package com.app.lms.modules.enrollment.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.ObjectMapperUtil;
import com.app.lms.modules.course.dtos.CourseDTO;
import com.app.lms.modules.course.entities.CourseEntity;
import com.app.lms.modules.enrollment.entities.EnrollmentEntity;
import com.app.lms.modules.enrollment.repositories.EnrollmentMainRepository;
import com.app.lms.modules.student.dtos.StudentDTO;
import com.app.lms.modules.student.entities.StudentEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    private EnrollmentMainRepository enrollmentMainRepository;

    @Override
    public void addStudentEnrollment(CourseDTO course, StudentDTO student) throws NotFoundException {

        EnrollmentEntity newEnrollment = new EnrollmentEntity();
        newEnrollment.setEnrollmentUuid(UUID.randomUUID());
        newEnrollment.setEnrollmentDate(new Date());
        newEnrollment.setEnrollmentIsActive(true);
        newEnrollment.setEnrollmentCourse(ObjectMapperUtil.map(course, CourseEntity.class));
        newEnrollment.setEnrollmentStudent(ObjectMapperUtil.map(student, StudentEntity.class));

        enrollmentMainRepository.save(newEnrollment);
    }

    @Override
    public void removeStudentEnrollment(UUID enrollmentUuid) {
        enrollmentMainRepository.deleteById(enrollmentUuid);
    }

    @Override
    public List<UUID> findStudentUuidByCourseUuid(UUID courseUuid) {
        return enrollmentMainRepository.findStudentUuidByCourseUuid(courseUuid);
    }

    @Override
    public boolean checkEnrollmentByStudentUuidAndCourseUuid(UUID studentUuid, UUID courseUuid) {

        List<UUID> listCourseUuid = enrollmentMainRepository.findCourseUuidByStudentUuidAndCourseUuid(studentUuid, courseUuid);

        return !listCourseUuid.isEmpty();
    }

    @Override
    public List<UUID> findCourseUuidByStudentUuid(UUID studentUuid) {
        return enrollmentMainRepository.findCourseUuidByStudentUuid(studentUuid);
    }
}
