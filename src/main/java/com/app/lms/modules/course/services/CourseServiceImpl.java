package com.app.lms.modules.course.services;

import com.app.lms.core.exceptions.ForbiddenException;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.*;
import com.app.lms.modules.enrollment.services.EnrollmentService;
import com.app.lms.modules.instructor.dtos.InstructorDTO;
import com.app.lms.modules.course.dtos.CourseDTO;
import com.app.lms.modules.course.entities.CourseEntity;
import com.app.lms.modules.course.repositories.CourseMainRepository;
import com.app.lms.modules.course.requests.GetCourseRequest;
import com.app.lms.modules.course.specifications.CourseSpecification;
import com.app.lms.modules.profile.services.ProfileServiceImpl;
import com.app.lms.modules.student.dtos.StudentDTO;
import com.app.lms.modules.student.entities.StudentEntity;
import com.app.lms.modules.student.requests.GetStudentRequest;
import com.app.lms.modules.student.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class CourseServiceImpl extends ProfileServiceImpl implements CourseService {

    @Autowired
    private CourseMainRepository masterCourseMainRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private EnrollmentService enrollmentService;

    @Override
    public PaginationUtil<CourseEntity, CourseDTO> getCourseByPagination(int page, int perPage, GetCourseRequest getMasterCourseRequest) {

        Page<CourseEntity> pagedData = masterCourseMainRepository.findAll(
                CourseSpecification.searchByPaginationRequest(getMasterCourseRequest),
                SpecificationUtil.generatePagination(page, perPage)
        );

        return new PaginationUtil<>(pagedData, CourseDTO.class);
    }

    @Override
    public CourseDTO getCourseByUuid(UUID courseUuid) throws NotFoundException {
        CourseDTO course = findSingleCourseByUuid(courseUuid);

        if (course == null) {
            throw new NotFoundException("Course Not Found");
        }

        return course;
    }

    @Override
    public CourseDTO attachToInstructor(UUID courseUuid, InstructorDTO instructor) throws NotFoundException {
        CourseDTO course = findSingleCourseByUuid(courseUuid);

        course.setCourseInstructor(instructor);

        masterCourseMainRepository.save(ObjectMapperUtil.map(course, CourseEntity.class));

        return course;
    }

    @Override
    public CourseDTO detachFromInstructor(UUID courseUuid) throws NotFoundException {
        CourseDTO course = findSingleCourseByUuid(courseUuid);

        course.setCourseInstructor(null);

        masterCourseMainRepository.save(ObjectMapperUtil.map(course, CourseEntity.class));

        return course;
    }

    @Override
    public CourseDTO createNewCourse(CourseDTO newCourseData) {
        newCourseData.setCourseUuid(UUID.randomUUID());
        newCourseData.setCourseSlug(StringUtil.createSlug(newCourseData.getCourseName()));

        CourseEntity newCourse = masterCourseMainRepository.save(ObjectMapperUtil.map(newCourseData, CourseEntity.class));

        return ObjectMapperUtil.map(newCourse, CourseDTO.class);
    }

    @Override
    public CourseDTO updateCourseByUuid(UUID courseUuid, CourseDTO newCourseData) throws NotFoundException {

        CourseDTO course = findSingleCourseByUuid(courseUuid);
        course.setCourseName(newCourseData.getCourseName());
        course.setCourseUuid(courseUuid);
        course.setCourseSlug(StringUtil.createSlug(newCourseData.getCourseName()));

        CourseEntity updatedStudent = masterCourseMainRepository.save(ObjectMapperUtil.map(course, CourseEntity.class));

        return ObjectMapperUtil.map(updatedStudent, CourseDTO.class);
    }

    @Override
    public void deleteCourseByUuid(UUID courseUuid) {
        masterCourseMainRepository.deleteById(courseUuid);
    }

    @Override
    public PaginationUtil<CourseEntity, CourseDTO> getPaginationCourseByInstructorUuid(int page, int perPage, GetCourseRequest instructorCoursePaginationRequest) throws NotFoundException {

        instructorCoursePaginationRequest.setInstructorUuid(getCurrentInstructor().getInstructorUuid());

        return getCourseByPagination(page, perPage, instructorCoursePaginationRequest);
    }

    @Override
    public CourseDTO getCourseByUuidFromInstructor(UUID courseUuid) throws NotFoundException, ForbiddenException {
        CourseDTO course = getCourseByUuid(courseUuid);

        if (!Objects.equals(course.getCourseInstructor().getInstructorUuid(), getCurrentInstructor().getInstructorUuid())) {
            throw new ForbiddenException("You are not Allowed to Edit This Course");
        }

        return course;
    }

    @Override
    public CourseDTO updateCourseByUuidFromInstructor(UUID courseUuid, CourseDTO newCourseData) throws NotFoundException, ForbiddenException {

        CourseDTO course = getCourseByUuid(courseUuid);

        if (!Objects.equals(course.getCourseInstructor().getInstructorUuid(), getCurrentInstructor().getInstructorUuid())) {
            throw new ForbiddenException("You are not Allowed to Access This Course");
        }

        return updateCourseByUuid(courseUuid, newCourseData);
    }

    @Override
    public PaginationUtil<StudentEntity, StudentDTO> getStudentEnrolledFromInstructor(UUID courseUuid, int page, int perPage, GetStudentRequest getStudentRequest) throws ForbiddenException, NotFoundException {
        CourseDTO course = getCourseByUuid(courseUuid);

        if (!Objects.equals(course.getCourseInstructor().getInstructorUuid(), getCurrentInstructor().getInstructorUuid())) {
            throw new ForbiddenException("You are not Allowed to Access Enrolled Student on this Course");
        }

        getStudentRequest.setStudentUuid(enrollmentService.findStudentUuidByCourseUuid(courseUuid));

        return studentService.getStudentByPagination(page, perPage, getStudentRequest);
    }

    @Override
    public PaginationUtil<CourseEntity, CourseDTO> getPaginationCourseByInstructorUuidFromStudent(int page, int perPage, GetCourseRequest studentCoursePaginationRequest) throws NotFoundException {

        List<UUID> coursesUuid = enrollmentService.findCourseUuidByStudentUuid(getCurrentStudent().getStudentUuid());

        studentCoursePaginationRequest.setCoursesUuid(coursesUuid);

        return getCourseByPagination(page, perPage, studentCoursePaginationRequest);
    }

    @Override
    public CourseDTO getCourseByUuidFromStudent(UUID courseUuid) throws NotFoundException, ForbiddenException {

        if (!isStudentEnrolledCourse(getCurrentStudent().getStudentUuid(), courseUuid)) {
            throw new ForbiddenException("You are not Allowed to Access This Course");
        }

        return getCourseByUuid(courseUuid);
    }

    @Override
    public CourseDTO findSingleCourseByUuid(UUID instructorUuid) throws NotFoundException {
        CourseEntity instructor = JpaResultHelperUtil.getSingleResultFromOptional(masterCourseMainRepository.findById(instructorUuid));

        if (instructor == null) {
            throw new NotFoundException("Course Not Found");
        }

        return ObjectMapperUtil.map(instructor, CourseDTO.class);
    }


    private boolean isStudentEnrolledCourse(UUID studentUuid, UUID courseUuid) {
        return enrollmentService.checkEnrollmentByStudentUuidAndCourseUuid(studentUuid, courseUuid);
    }
}
