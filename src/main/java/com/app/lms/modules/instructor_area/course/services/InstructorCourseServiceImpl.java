package com.app.lms.modules.instructor_area.course.services;

import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.PaginationUtil;
import com.app.lms.modules.admin_area.instructor.dtos.InstructorDTO;
import com.app.lms.modules.admin_area.master_course.dtos.MasterCourseDTO;
import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.master_course.requests.GetMasterCourseRequest;
import com.app.lms.modules.admin_area.master_course.services.MasterCourseService;
import com.app.lms.modules.instructor_area.profile.services.InstructorProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class InstructorCourseServiceImpl implements InstructorCourseService {

    @Autowired
    private MasterCourseService masterCourseService;

    @Autowired
    private InstructorProfileService instructorProfileService;

    @Override
    public PaginationUtil<MasterCourseEntity, MasterCourseDTO> getPaginationCourseByInstructorUuid(int page, int perPage, GetMasterCourseRequest instructorCoursePaginationRequest) throws NotFoundException {
        instructorCoursePaginationRequest.setInstructorUuid(getCurrentInstructor().getInstructorUuid());

        return masterCourseService.getCourseByPagination(page, perPage, instructorCoursePaginationRequest);
    }

    @Override
    public MasterCourseDTO getCourseByUuid(UUID courseUuid) throws NotFoundException, ForbiddenException {
        MasterCourseDTO course = masterCourseService.getCourseByUuid(courseUuid);

        if(!Objects.equals(course.getCourseInstructor().getInstructorUuid(), getCurrentInstructor().getInstructorUuid())) {
            throw new ForbiddenException("You are not Allowed to Edit This Course");
        }

        return course;
    }

    @Override
    public MasterCourseDTO updateCourseByUuid(UUID courseUuid, MasterCourseDTO newCourseData) throws NotFoundException, ForbiddenException {

        MasterCourseDTO course = masterCourseService.getCourseByUuid(courseUuid);

        if(!Objects.equals(course.getCourseInstructor().getInstructorUuid(), getCurrentInstructor().getInstructorUuid())) {
            throw new ForbiddenException("You are not Allowed to Access This Course");
        }

        return masterCourseService.updateCourseByUuid(courseUuid, newCourseData);
    }

    private InstructorDTO getCurrentInstructor() throws NotFoundException {
        return instructorProfileService.getCurrentInstructorProfile();
    }
}
