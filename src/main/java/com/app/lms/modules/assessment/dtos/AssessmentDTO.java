package com.app.lms.modules.assessment.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssessmentDTO {
    private UUID assessmentUuid;
    private String assessmentName;
    private String assessmentDescription;
    private String assessmentInstruction;
}
