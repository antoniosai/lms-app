package com.app.lms.modules.admin_area.account.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_account")
public class AccountEntity {
    @Id
    @Column(name = "account_uuid", unique = true, nullable = false)
    private UUID accountUuid;

    @Column(name = "account_username", unique = true, nullable = false)
    private String accountUsername;
}
