package com.app.lms.modules.admin_area.enrollment.entities;

import com.app.lms.modules.admin_area.master_course.entities.MasterCourseEntity;
import com.app.lms.modules.admin_area.student.entities.StudentEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_enrollments",  uniqueConstraints = @UniqueConstraint(columnNames = {"enrollment_course_id", "enrollment_student_id"}))
public class EnrollmentEntity implements Serializable {
    @Id
    @Column(name = "enrollment_uuid", nullable = false, insertable = false)
    private UUID enrollmentUuid;

    @ManyToOne
    @JoinColumn(name = "enrollment_course_id", nullable = false)
    private MasterCourseEntity enrollmentCourse;

    @ManyToOne
    @JoinColumn(name = "enrollment_student_id", nullable = false)
    private StudentEntity enrollmentStudent;

    @Column(name = "enrollment_date", nullable = false)
    private Date enrollmentDate;

    @Column(name = "enrollment_is_active", nullable = false  )
    private Boolean enrollmentIsActive;

}
