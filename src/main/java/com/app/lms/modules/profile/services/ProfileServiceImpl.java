package com.app.lms.modules.profile.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.ObjectMapperUtil;
import com.app.lms.enums.AccountTypeEnum;
import com.app.lms.modules.admin_area.account.dtos.AccountDTO;
import com.app.lms.modules.admin_area.account.services.AccountService;
import com.app.lms.modules.admin_area.admin.dtos.AdministratorDTO;
import com.app.lms.modules.admin_area.admin.services.AdministratorService;
import com.app.lms.modules.admin_area.instructor.dtos.InstructorDTO;
import com.app.lms.modules.admin_area.instructor.services.InstructorService;
import com.app.lms.modules.admin_area.student.dtos.StudentDTO;
import com.app.lms.modules.admin_area.student.services.StudentService;
import com.app.lms.modules.profile.dtos.ProfileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    private StudentDTO getStudentProfile(String username) throws NotFoundException {

        AccountDTO account = findAccountByUsername(username);
        return studentService.findOneByAccountUuid(account.getAccountUuid());
    }

    private InstructorDTO getInstructorProfile(String username) throws NotFoundException {

        AccountDTO account = findAccountByUsername(username);
        return instructorService.findOneByAccountUuid(account.getAccountUuid());
    }

    private AdministratorDTO getAdministratorProfile(String username) throws NotFoundException {

        AccountDTO account = findAccountByUsername(username);
        return administratorService.findOneByAccountUuid(account.getAccountUuid());

    }

    private AccountDTO findAccountByUsername(String username) throws NotFoundException {
        return accountService.findOneAccountByAccountUsername(username);
    }

    public Object getProfile(String username) throws NotFoundException {

        AccountDTO account = findAccountByUsername(username);

        if (account.getAccountType() == AccountTypeEnum.ADMINISTRATOR) {
            ProfileDTO<AdministratorDTO> profile = new ProfileDTO<>();
            profile.setAccount(account);
            profile.setProfile(getAdministratorProfile(username));

            return profile;
        } else if(account.getAccountType() == AccountTypeEnum.INSTRUCTOR) {
            ProfileDTO<InstructorDTO> profile = new ProfileDTO<>();
            profile.setAccount(account);
            profile.setProfile(getInstructorProfile(username));

            return profile;

        } else if(account.getAccountType() == AccountTypeEnum.STUDENT) {
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

}
