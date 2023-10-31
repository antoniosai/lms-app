package com.app.lms.modules.admin_area.enrollment.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.ObjectMapperUtil;
import com.app.lms.modules.admin_area.enrollment.entities.EnrollmentEntity;
import com.app.lms.modules.admin_area.enrollment.repositories.EnrollmentMainRepository;
import com.app.lms.modules.admin_area.master_course.dtos.MasterCourseDTO;
import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.master_course.services.MasterCourseService;
import com.app.lms.modules.admin_area.student.dtos.StudentDTO;
import com.app.lms.modules.admin_area.student.entities.StudentEntity;
import com.app.lms.modules.admin_area.student.services.StudentService;
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

    @Autowired
    private StudentService studentService;

    @Autowired
    private MasterCourseService courseService;

    @Override
    public void addStudentEnrollment(UUID courseUuid, UUID studentUuid) throws NotFoundException {

        MasterCourseDTO course = courseService.getCourseByUuid(courseUuid);
        StudentDTO student = studentService.getStudentByUuid(studentUuid);

        EnrollmentEntity newEnrollment = new EnrollmentEntity();
        newEnrollment.setEnrollmentUuid(UUID.randomUUID());
        newEnrollment.setEnrollmentDate(new Date());
        newEnrollment.setEnrollmentIsActive(true);
        newEnrollment.setEnrollmentCourse(ObjectMapperUtil.map(course, MasterCourseEntity.class));
        newEnrollment.setEnrollmentStudent(ObjectMapperUtil.map(student, StudentEntity.class));

        log.info("newEnrollment => " + newEnrollment);

        enrollmentMainRepository.save(newEnrollment);
    }

    @Override
    public void removeStudentEnrollment(UUID enrollmentUuid) {
        enrollmentMainRepository.deleteById(enrollmentUuid);
    }

    @Override
    public List<UUID> findStudentUuidByCourseUuid(UUID courseUuid) {
        List<UUID> studentUuid = enrollmentMainRepository.findStudentUuidByCourseUuid(courseUuid);

        log.info("UUID => {}", studentUuid);

        return studentUuid;
    }

    @Override
    public boolean checkEnrollmentByStudentUuidAndCourseUuid(UUID studentUuid, UUID courseUuid) {

        List<UUID> listCourseUuid = enrollmentMainRepository.findCourseUuidByStudentUuidAndCourseUuid(studentUuid, courseUuid);

        return !listCourseUuid.isEmpty();
    }
}
