package com.app.lms.modules.profile.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.enums.AccountTypeEnum;
import com.app.lms.modules.account.dtos.AccountDTO;
import com.app.lms.modules.account.services.AccountService;
import com.app.lms.modules.admin.dtos.AdministratorDTO;
import com.app.lms.modules.admin.services.AdministratorService;
import com.app.lms.modules.instructor.dtos.InstructorDTO;
import com.app.lms.modules.instructor.services.InstructorService;
import com.app.lms.modules.profile.dtos.ProfileDTO;
import com.app.lms.modules.student.dtos.StudentDTO;
import com.app.lms.modules.student.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Primary
@Service
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private AccountService accountService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private AdministratorService administratorService;

    protected StudentDTO getStudentProfile(String username) throws NotFoundException {

        AccountDTO account = findAccountByUsername(username);
        return studentService.findOneByAccountUuid(account.getAccountUuid());
    }

    protected InstructorDTO getInstructorProfile(String username) throws NotFoundException {

        AccountDTO account = findAccountByUsername(username);
        return instructorService.findOneByAccountUuid(account.getAccountUuid());
    }

    protected AdministratorDTO getAdministratorProfile(String username) throws NotFoundException {

        AccountDTO account = findAccountByUsername(username);
        return administratorService.findOneByAccountUuid(account.getAccountUuid());

    }

    protected AccountDTO findAccountByUsername(String username) throws NotFoundException {
        return accountService.findOneAccountByAccountUsername(username);
    }

    public Object getProfile(String username) throws NotFoundException {

        AccountDTO account = findAccountByUsername(username);

        if (account.getAccountType() == AccountTypeEnum.ADMINISTRATOR) {
            ProfileDTO<AdministratorDTO> profile = new ProfileDTO<>();
            profile.setAccount(account);
            profile.setProfile(getAdministratorProfile(username));

            return profile;
        } else if (account.getAccountType() == AccountTypeEnum.INSTRUCTOR) {
            ProfileDTO<InstructorDTO> profile = new ProfileDTO<>();
            profile.setAccount(account);
            profile.setProfile(getInstructorProfile(username));

            return profile;

        } else if (account.getAccountType() == AccountTypeEnum.STUDENT) {
            ProfileDTO<StudentDTO> profile = new ProfileDTO<>();
            profile.setAccount(account);
            profile.setProfile(getStudentProfile(username));

            return profile;
        } else {
            throw new NotFoundException("Profile Not Found");
        }
    }

    @Override
    public void updateProfile(String username, StudentDTO newProfileData) throws NotFoundException {
        StudentDTO student = getStudentProfile(username);
        studentService.updateStudentByUuid(student.getStudentUuid(), newProfileData);
    }

    @Override
    public void updateProfile(String username, InstructorDTO newProfileData) throws NotFoundException {
        InstructorDTO instructor = getInstructorProfile(username);
        instructorService.updateInstructorByUuid(instructor.getInstructorUuid(), newProfileData);
    }

    @Override
    public void updateProfile(String username, AdministratorDTO newProfileData) throws NotFoundException {
        AdministratorDTO administrator = getAdministratorProfile(username);
        administratorService.updateAdminByUuid(administrator.getAdminUuid(), newProfileData);
    }

    @Override
    public StudentDTO getCurrentStudent() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return getStudentProfile(authentication.getName());
        }

        throw new NotFoundException("Student UUID not Found");
    }

    @Override
    public InstructorDTO getCurrentInstructor() throws NotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return getInstructorProfile(authentication.getName());
        }

        throw new NotFoundException("Instructor UUID not Found");
    }

}
