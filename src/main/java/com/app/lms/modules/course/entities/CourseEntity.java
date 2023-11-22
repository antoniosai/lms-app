package com.app.lms.modules.course.entities;

import com.app.lms.core.abstraction.TimestampEntityAbstraction;
import com.app.lms.modules.instructor.entities.InstructorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "m_courses")
public class CourseEntity extends TimestampEntityAbstraction implements Serializable {
    @Id
    @Column(name = "course_uuid", nullable = false, unique = true)
    private UUID courseUuid;

    @Column(name = "course_slug", nullable = false, length = 80, unique = true)
    private String courseSlug;

    @Column(name = "course_name", nullable = false, length = 60)
    private String courseName;

    @Column(name = "course_description")
    @Lob
    private String courseDescription;

    @ManyToOne
    @JoinColumn(name = "course_instructor_uuid")
    private InstructorEntity courseInstructor;
}
