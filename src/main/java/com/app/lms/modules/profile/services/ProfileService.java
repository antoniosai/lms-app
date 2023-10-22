package com.app.lms.modules.profile.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.modules.admin_area.admin.dtos.AdministratorDTO;
import com.app.lms.modules.admin_area.instructor.dtos.InstructorDTO;
import com.app.lms.modules.admin_area.student.dtos.StudentDTO;

public interface ProfileService {
    Object getProfile(String username) throws NotFoundException;

    void updateProfile(String username, StudentDTO newProfileData) throws NotFoundException;

    void updateProfile(String username, InstructorDTO newProfileData) throws NotFoundException;

    void updateProfile(String username, AdministratorDTO newProfileData) throws NotFoundException;
}
