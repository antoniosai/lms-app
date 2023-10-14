package com.app.lms.modules.admin_area.instructor.repositories;

import com.app.lms.modules.admin_area.instructor.entities.InstructorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InstructorMainRepository extends JpaRepository<InstructorEntity, UUID>, JpaSpecificationExecutor<InstructorEntity> {
}
