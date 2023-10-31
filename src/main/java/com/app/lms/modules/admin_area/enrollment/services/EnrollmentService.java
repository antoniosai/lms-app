package com.app.lms.modules.admin_area.enrollment.services;

import com.app.lms.core.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public interface EnrollmentService {
    void addStudentEnrollment(UUID courseUuid, UUID studentUuid) throws NotFoundException;

    void removeStudentEnrollment(UUID studentUuid);

    List<UUID> findStudentUuidByCourseUuid(UUID courseUuid);

    boolean checkEnrollmentByStudentUuidAndCourseUuid(UUID studentUuid, UUID courseUuid);

}
