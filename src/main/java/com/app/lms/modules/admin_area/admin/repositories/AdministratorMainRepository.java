package com.app.lms.modules.admin_area.admin.repositories;

import com.app.lms.modules.admin_area.admin.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdministratorMainRepository extends JpaRepository<AdminEntity, UUID>, JpaSpecificationExecutor<AdminEntity> {
    @Query("SELECT admin FROM AdminEntity admin WHERE admin.adminUuid = :uuid AND admin.adminAccount IS NOT NULL")
    List<AdminEntity> findByAccountUuid(@Param("uuid") UUID uuid);
}
