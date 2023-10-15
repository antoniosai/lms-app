package com.app.lms.modules.admin_area.account.entities;

import com.app.lms.enums.AccountTypeEnum;
import jakarta.persistence.*;
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

    @Column(name = "account_name", nullable = false, unique = true, length = 20)
    private String accountUsername;

    @Column(name = "account_password", unique = true, nullable = false, length = 50)
    private String accountPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 20)
    private AccountTypeEnum accountType;
}
