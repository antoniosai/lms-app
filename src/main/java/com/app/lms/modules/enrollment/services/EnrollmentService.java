package com.app.lms.modules.enrollment.services;

import com.app.lms.modules.course.dtos.CourseDTO;
import com.app.lms.modules.student.dtos.StudentDTO;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    void addStudentEnrollment(CourseDTO course, StudentDTO student);

    void removeStudentEnrollment(UUID studentUuid);

    List<UUID> findStudentUuidByCourseUuid(UUID courseUuid);

    List<UUID> findCourseUuidByStudentUuid(UUID studentUuid);

    boolean checkEnrollmentByStudentUuidAndCourseUuid(UUID studentUuid, UUID courseUuid);

}
