package com.app.lms.modules.student.entities;

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
@Table(name = "m_student")
public class StudentEntity extends TimestampEntityAbstraction {

    @Id
    @Column(name = "student_uuid", unique = true, nullable = false)
    private UUID studentUuid;

    @Column(name = "student_identification_number", length = 50, unique = true, nullable = false)
    private String studentIdentificationNumber;

    @Column(name = "student_name", length = 50, nullable = false)
    private String studentName;

    @Column(name = "student_birth_place", length = 50, nullable = false)
    private String studentBirthPlace;

    @Column(name = "student_birth_date", nullable = false)
    private Date studentBirthDate;

    @Column(name = "student_email", nullable = false, length = 50, unique = true)
    private String studentEmail;

    @Column(name = "student_address")
    private String studentAddress;

    @Column(name = "student_profile_picture")
    private String studentProfilePicture;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "student_account_uuid", referencedColumnName = "account_uuid")
    private AccountEntity studentAccount;
}
