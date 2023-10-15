package com.app.lms.modules.admin_area.account.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.JpaResultHelperUtil;
import com.app.lms.core.utils.ObjectMapperUtil;
import com.app.lms.core.utils.PasswordUtil;
import com.app.lms.enums.AccountTypeEnum;
import com.app.lms.modules.admin_area.account.dtos.AccountDTO;
import com.app.lms.modules.admin_area.account.entities.AccountEntity;
import com.app.lms.modules.admin_area.account.repositories.AccountMainRepository;
import com.app.lms.modules.admin_area.instructor.entities.InstructorEntity;
import com.app.lms.modules.admin_area.instructor.repositories.InstructorMainRepository;
import com.app.lms.modules.admin_area.student.entities.StudentEntity;
import com.app.lms.modules.admin_area.student.repositories.StudentMainRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMainRepository accountMainRepository;

    @Autowired
    private StudentMainRepository studentMainRepository;

    @Autowired
    private InstructorMainRepository instructorMainRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                return accountMainRepository.findByAccountUsername(username);
            }
        };
    }

    @Override
    public AccountDTO attachAccount(AccountDTO accountData, AccountTypeEnum accountType, UUID userUuid) throws Exception {

        // Check if it's Exist or Not
        boolean checkAccount = checkUserAccountIfExists(accountType, userUuid);


        if(checkAccount) throw new Exception("Can't process because account already has a Account");

        AccountDTO account;
        if(accountType == AccountTypeEnum.STUDENT) {
            accountData = createAccount(accountData, AccountTypeEnum.STUDENT);
            account = attachAccountToStudent(userUuid, accountData);
        } else if(accountType == AccountTypeEnum.INSTRUCTOR) {
            accountData = createAccount(accountData, AccountTypeEnum.INSTRUCTOR);
            account = attachAccountToInstructor(userUuid, accountData);
        } else if(accountType == AccountTypeEnum.ADMINISTRATOR) {
            accountData = createAccount(accountData, AccountTypeEnum.ADMINISTRATOR);
            account = attachAccountToInstructor(userUuid, accountData);
        } else {
            throw new NotFoundException("Account Type Not Found. Please select one of these: STUDENT, INSTRUCTOR, ADMINISTRATOR");
        }

        return account;
    }

    @Override
    public void deleteAccountByUuid(UUID accountUuid) {

        accountMainRepository.deleteById(accountUuid);

    }

    private AccountDTO findAccountByUuid(UUID accountUuid) throws NotFoundException {
        AccountEntity account = JpaResultHelperUtil.getSingleResultFromOptional(accountMainRepository.findById(accountUuid));

        if(account == null) {
            throw new NotFoundException("Account is Not Found");
        }

        return ObjectMapperUtil.map(account, AccountDTO.class);
    }

    private AccountDTO createAccount(AccountDTO newAccountData, AccountTypeEnum accountTypeEnum) {

        // Set Default UUID as Primary Key Value
        newAccountData.setAccountUuid(UUID.randomUUID());
        newAccountData.setAccountType(accountTypeEnum);
        newAccountData.setAccountPassword(PasswordUtil.encodePassword(newAccountData.getAccountPassword()));

        // Assign new Password
        AccountEntity accountEntity = ObjectMapperUtil.map(newAccountData, AccountEntity.class);

        // Save to Database
        AccountEntity account = accountMainRepository.save(accountEntity);

        // Map a Return into a DTO
        return ObjectMapperUtil.map(account, AccountDTO.class);
    }

    private boolean checkUserAccountIfExists(AccountTypeEnum accountType, UUID userUuid) {
        if(AccountTypeEnum.STUDENT == accountType) {
            return !studentMainRepository.findByAccountUuid(userUuid).isEmpty();
        } else if(AccountTypeEnum.INSTRUCTOR == accountType) {
            return !instructorMainRepository.findByAccountUuid(userUuid).isEmpty();
        } else if(AccountTypeEnum.ADMINISTRATOR == accountType) {
            return JpaResultHelperUtil.getSingleResultFromOptional(instructorMainRepository.findById(userUuid)) != null;
        } else {
            return false;
        }
    }

    private AccountDTO attachAccountToStudent(UUID studentUuid, AccountDTO account) {

        // Find Data by UUID
        StudentEntity student = JpaResultHelperUtil.getSingleResultFromOptional(studentMainRepository.findById(studentUuid));

        // Update StudentAccount from fetched Data Before
        student.setStudentAccount(ObjectMapperUtil.map(account, AccountEntity.class));

        // Update value
        studentMainRepository.save(student);

        return account;
    }

    private AccountDTO attachAccountToAdministrator(UUID administratorUuid, AccountDTO account) {
        StudentEntity administrator = JpaResultHelperUtil.getSingleResultFromOptional(studentMainRepository.findById(administratorUuid));

        administrator.setStudentAccount(ObjectMapperUtil.map(account, AccountEntity.class));

        return account;
    }

    private AccountDTO attachAccountToInstructor(UUID instructorUuid, AccountDTO account) {
        InstructorEntity instructor = JpaResultHelperUtil.getSingleResultFromOptional(instructorMainRepository.findById(instructorUuid));

        instructor.setInstructorAccount(ObjectMapperUtil.map(account, AccountEntity.class));

        instructorMainRepository.save(instructor);

        return account;
    }
}
