package com.app.lms.modules.admin.entities;

import com.app.lms.core.abstraction.TimestampEntityAbstraction;
import com.app.lms.modules.account.entities.AccountEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_admins")
public class AdminEntity extends TimestampEntityAbstraction {
    @Id
    @Column(name = "admin_uuid", unique = true, nullable = false)
    private UUID adminUuid;

    @Column(name = "admin_identification_number", length = 50, unique = true, nullable = false)
    private String adminIdentificationNumber;

    @Column(name = "admin_name", length = 50, nullable = false)
    private String adminName;

    @Column(name = "admin_birth_place", length = 50, nullable = false)
    private String adminBirthPlace;

    @Column(name = "admin_birth_date", nullable = false)
    private Date adminBirthDate;

    @Column(name = "admin_address")
    private String adminAddress;

    @Column(name = "admin_profile_picture")
    private String adminProfilePicture;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "admin_account_uuid", referencedColumnName = "account_uuid")
    private AccountEntity adminAccount;
}
