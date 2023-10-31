package com.app.lms.modules.student_area.course.services;

import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.enrollment.services.EnrollmentService;
import com.app.lms.modules.admin_area.master_course.dtos.MasterCourseDTO;
import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.master_course.requests.GetMasterCourseRequest;
import com.app.lms.modules.admin_area.master_course.services.MasterCourseService;
import com.app.lms.modules.profile.services.ProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentCourseServiceImpl extends ProfileServiceImpl implements StudentCourseService {

    @Autowired
    private MasterCourseService masterCourseService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Override
    public PaginationUtil<MasterCourseEntity, MasterCourseDTO> getPaginationCourseByInstructorUuid(int page, int perPage, GetMasterCourseRequest studentCoursePaginationRequest) throws NotFoundException {

        List<UUID> coursesUuid = enrollmentService.findCourseUuidByStudentUuid(getCurrentStudent().getStudentUuid());

        studentCoursePaginationRequest.setCoursesUuid(coursesUuid);

        return masterCourseService.getCourseByPagination(page, perPage, studentCoursePaginationRequest);
    }

    @Override
    public MasterCourseDTO getCourseByUuid(UUID courseUuid) throws NotFoundException, ForbiddenException {

        if (!isStudentEnrolledCourse(getCurrentStudent().getStudentUuid(), courseUuid)) {
            throw new ForbiddenException("You are not Allowed to Access This Course");
        }

        return masterCourseService.getCourseByUuid(courseUuid);
    }

    private boolean isStudentEnrolledCourse(UUID studentUuid, UUID courseUuid) {
        return enrollmentService.checkEnrollmentByStudentUuidAndCourseUuid(studentUuid, courseUuid);
    }
}
