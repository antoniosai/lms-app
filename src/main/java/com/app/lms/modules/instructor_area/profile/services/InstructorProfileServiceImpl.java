package com.app.lms.modules.instructor_area.profile.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.modules.admin_area.instructor.dtos.InstructorDTO;
import com.app.lms.modules.profile.services.ProfileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service(value = "instructorProfileService")
@Slf4j
public class InstructorProfileServiceImpl extends ProfileServiceImpl implements InstructorProfileService {

    @Override
    public InstructorDTO getCurrentInstructorProfile() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return getInstructorProfile(authentication.getName());
        }

        throw new NotFoundException("Instructor UUID not Found");
    }

}
