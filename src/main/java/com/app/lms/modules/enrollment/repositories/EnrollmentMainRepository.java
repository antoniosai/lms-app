package com.app.lms.modules.enrollment.repositories;

import com.app.lms.modules.enrollment.entities.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EnrollmentMainRepository extends JpaRepository<EnrollmentEntity, UUID>, JpaSpecificationExecutor<EnrollmentEntity> {

    @Modifying
    @Query("DELETE FROM EnrollmentEntity enrollment WHERE enrollment.enrollmentCourse.courseUuid = :courseUuid AND enrollment.enrollmentStudent.studentUuid = :studentUuid")
    void deleteEnrollment(@Param("courseUuid") UUID courseUuid, @Param("studentUuid") UUID studentUuid);

    @Query("SELECT enrollment.enrollmentStudent.studentUuid FROM EnrollmentEntity enrollment WHERE enrollment.enrollmentCourse.courseUuid = :courseUuid")
    List<UUID> findStudentUuidByCourseUuid(@Param("courseUuid") UUID courseUuid);

    @Query("SELECT enrollment.enrollmentCourse.courseUuid FROM EnrollmentEntity enrollment WHERE enrollment.enrollmentStudent.studentUuid = :studentUuid")
    List<UUID> findCourseUuidByStudentUuid(@Param("studentUuid") UUID studentUuid);

    @Query("SELECT enrollment.enrollmentCourse.courseUuid FROM EnrollmentEntity enrollment WHERE enrollment.enrollmentStudent.studentUuid = :studentUuid AND enrollment.enrollmentCourse.courseUuid = :courseUuid")
    List<UUID> findCourseUuidByStudentUuidAndCourseUuid(@Param("studentUuid") UUID studentUuid, @Param("courseUuid") UUID courseUuid);

}
