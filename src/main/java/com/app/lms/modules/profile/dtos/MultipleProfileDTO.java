package com.app.lms.modules.profile.dtos;

import com.app.lms.modules.admin.dtos.AdministratorDTO;
import com.app.lms.modules.instructor.dtos.InstructorDTO;
import com.app.lms.modules.student.dtos.StudentDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MultipleProfileDTO {
    private StudentDTO student;
    private InstructorDTO instructor;
    private AdministratorDTO administrator;
}
