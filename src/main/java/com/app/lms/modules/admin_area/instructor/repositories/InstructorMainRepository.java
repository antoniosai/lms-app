package com.app.lms.modules.admin_area.instructor.repositories;

import com.app.lms.modules.admin_area.instructor.entities.InstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InstructorMainRepository extends JpaRepository<InstructorEntity, UUID>, JpaSpecificationExecutor<InstructorEntity> {
    @Query("SELECT instructor FROM InstructorEntity instructor WHERE instructor.instructorAccount.accountUuid = :uuid AND instructor.instructorAccount IS NOT NULL")
    List<InstructorEntity> findByAccountUuid(@Param("uuid") UUID uuid);
}
