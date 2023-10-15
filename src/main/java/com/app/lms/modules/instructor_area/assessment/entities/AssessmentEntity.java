package com.app.lms.modules.instructor_area.assessment.entities;

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
@Table(name = "m_assessment")
public class AssessmentEntity {
    @Id
    @Column(name = "assessment_uuid", unique = true, nullable = false)
    private UUID assessmentUuid;

    @Column(name = "assessment_name", nullable = false, length = 50)
    private String assessmentName;

    @Column(name = "assessment_description")
    @Lob
    private String assessmentDescription;

    @Column(name = "assessment_instruction")
    @Lob
    private String assessmentInstruction;
}
