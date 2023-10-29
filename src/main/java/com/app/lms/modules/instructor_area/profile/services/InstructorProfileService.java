package com.app.lms.modules.instructor_area.profile.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.modules.admin_area.instructor.dtos.InstructorDTO;

public interface InstructorProfileService {
    InstructorDTO getCurrentInstructorProfile() throws NotFoundException;
}
