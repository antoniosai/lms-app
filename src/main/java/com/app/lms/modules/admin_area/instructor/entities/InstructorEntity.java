package com.app.lms.modules.admin_area.instructor.entities;

import com.app.lms.core.abstraction.TimestampEntityAbstraction;
import com.app.lms.modules.admin_area.account.entities.AccountEntity;
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
@Table(name = "m_instructors")
public class InstructorEntity extends TimestampEntityAbstraction {
    @Id
    @Column(name = "instructor_uuid", unique = true, nullable = false)
    private UUID instructorUuid;

    @Column(name = "instructor_identification_number", length = 50, unique = true, nullable = false)
    private String instructorIdentificationNumber;

    @Column(name = "instructor_name", length = 50, nullable = false)
    private String instructorName;

    @Column(name = "instructor_birth_place", length = 50, nullable = false)
    private String instructorBirthPlace;

    @Column(name = "instructor_birth_date", nullable = false)
    private Date instructorBirthDate;

    @Column(name = "instructor_address")
    private String instructorAddress;

    @Column(name = "instructor_profile_picture")
    private String instructorProfilePicture;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "instructor_account_uuid", referencedColumnName = "account_uuid")
    private AccountEntity instructorAccount;
}
