package com.app.lms.modules.admin_area.enrollment.repositories;

import com.app.lms.modules.admin_area.enrollment.entities.EnrollmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnrollmentMainRepository extends JpaRepository<EnrollmentEntity, UUID>, JpaSpecificationExecutor<EnrollmentEntity> {

    @Modifying
    @Query("DELETE FROM EnrollmentEntity enrollment WHERE enrollment.enrollmentCourse.courseUuid = :courseUuid AND enrollment.enrollmentStudent.studentUuid = :studentUuid")
    void deleteEnrollment(@Param("courseUuid") UUID courseUuid, @Param("studentUuid") UUID studentUuid);

}
