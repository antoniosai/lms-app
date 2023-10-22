package com.app.lms.modules.admin_area.account.repositories;

import com.app.lms.modules.admin_area.account.entities.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountMainRepository extends JpaRepository<AccountEntity, UUID>, JpaSpecificationExecutor<AccountEntity> {
    AccountEntity findByAccountUsername(String username);

    @Modifying
    @Query("DELETE FROM AccountEntity ac WHERE ac.accountUsername = :username")
    void deleteByAccountUsername(@Param("username") String username);

    @Query("SELECT account FROM AccountEntity account WHERE account.accountUsername = :username")
    List<AccountEntity> findAccountByUsername(@Param("username") String username);
}
