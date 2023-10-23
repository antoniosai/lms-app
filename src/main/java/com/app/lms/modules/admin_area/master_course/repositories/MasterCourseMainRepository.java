package com.app.lms.modules.admin_area.master_course.repositories;

import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MasterCourseMainRepository extends JpaRepository<MasterCourseEntity, UUID>, JpaSpecificationExecutor<MasterCourseEntity> {
}
