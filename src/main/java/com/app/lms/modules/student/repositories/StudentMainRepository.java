package com.app.lms.modules.student.repositories;

import com.app.lms.modules.student.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentMainRepository extends JpaRepository<StudentEntity, UUID>, JpaSpecificationExecutor<StudentEntity> {
    @Query("SELECT student FROM StudentEntity student WHERE student.studentAccount.accountUuid = :uuid AND student.studentAccount IS NOT NULL")
    List<StudentEntity> findByAccountUuid(@Param("uuid") UUID uuid);

    @Modifying
    @Query("DELETE FROM StudentEntity s WHERE s.studentIdentificationNumber = :identificationNumber")
    void deleteByStudentIdentificationNumber(@Param("identificationNumber") String identificationNumber);
}
