package com.app.lms.modules.admin_area.master_course.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.*;
import com.app.lms.modules.admin_area.instructor.dtos.InstructorDTO;
import com.app.lms.modules.admin_area.master_course.dtos.MasterCourseDTO;
import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.master_course.repositories.MasterCourseMainRepository;
import com.app.lms.modules.admin_area.master_course.requests.GetMasterCourseRequest;
import com.app.lms.modules.admin_area.master_course.specifications.MasterCourseSpecification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class MasterCourseServiceImpl implements MasterCourseService {

    @Autowired
    private MasterCourseMainRepository masterCourseMainRepository;

    @Override
    public PaginationUtil<MasterCourseEntity, MasterCourseDTO> getCourseByPagination(int page, int perPage, GetMasterCourseRequest getMasterCourseRequest) {

        Page<MasterCourseEntity> pagedData = masterCourseMainRepository.findAll(
                MasterCourseSpecification.searchByPaginationRequest(getMasterCourseRequest),
                SpecificationUtil.generatePagination(page, perPage)
        );

        return new PaginationUtil<>(pagedData, MasterCourseDTO.class);
    }

    @Override
    public MasterCourseDTO getCourseByUuid(UUID courseUuid) throws NotFoundException {
        MasterCourseDTO course = findSingleCourseByUuid(courseUuid);

        if (course == null) {
            throw new NotFoundException("Course Not Found");
        }

        return course;
    }

    @Override
    public MasterCourseDTO attachToInstructor(UUID courseUuid, InstructorDTO instructor) throws NotFoundException {
        MasterCourseDTO course = findSingleCourseByUuid(courseUuid);

        course.setCourseInstructor(instructor);

        masterCourseMainRepository.save(ObjectMapperUtil.map(course, MasterCourseEntity.class));

        return course;
    }

    @Override
    public MasterCourseDTO detachFromInstructor(UUID courseUuid) throws NotFoundException {
        MasterCourseDTO course = findSingleCourseByUuid(courseUuid);

        course.setCourseInstructor(null);

        masterCourseMainRepository.save(ObjectMapperUtil.map(course, MasterCourseEntity.class));

        return course;
    }

    @Override
    public MasterCourseDTO createNewCourse(MasterCourseDTO newCourseData) {
        newCourseData.setCourseUuid(UUID.randomUUID());
        newCourseData.setCourseSlug(StringUtil.createSlug(newCourseData.getCourseName()));

        MasterCourseEntity newCourse = masterCourseMainRepository.save(ObjectMapperUtil.map(newCourseData, MasterCourseEntity.class));

        return ObjectMapperUtil.map(newCourse, MasterCourseDTO.class);
    }

    @Override
    public MasterCourseDTO updateCourseByUuid(UUID courseUuid, MasterCourseDTO newCourseData) {
        return null;
    }

    @Override
    public void deleteCourseByUuid(UUID courseUuid) {
        masterCourseMainRepository.deleteById(courseUuid);
    }

    private MasterCourseDTO findSingleCourseByUuid(UUID instructorUuid) throws NotFoundException {
        MasterCourseEntity instructor = JpaResultHelperUtil.getSingleResultFromOptional(masterCourseMainRepository.findById(instructorUuid));

        if (instructor == null) {
            log.info("Course Not Found");
            throw new NotFoundException("Course Not Found");
        }

        return ObjectMapperUtil.map(instructor, MasterCourseDTO.class);
    }
}
