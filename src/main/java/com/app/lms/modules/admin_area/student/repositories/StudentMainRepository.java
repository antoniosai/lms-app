package com.app.lms.modules.admin_area.student.repositories;

import com.app.lms.modules.admin_area.student.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentMainRepository extends JpaRepository<StudentEntity, UUID>, JpaSpecificationExecutor<StudentEntity> {
    @Query("SELECT student FROM StudentEntity student WHERE student.studentUuid = :uuid AND student.studentAccount IS NOT NULL")
    List<StudentEntity> findByAccountUuid(@Param("uuid") UUID uuid);
}
