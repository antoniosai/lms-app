package com.app.lms.modules.account.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.JpaResultHelperUtil;
import com.app.lms.core.utils.ObjectMapperUtil;
import com.app.lms.core.utils.PasswordUtil;
import com.app.lms.enums.AccountTypeEnum;
import com.app.lms.modules.account.dtos.AccountDTO;
import com.app.lms.modules.account.entities.AccountEntity;
import com.app.lms.modules.account.repositories.AccountMainRepository;
import com.app.lms.modules.instructor.dtos.InstructorDTO;
import com.app.lms.modules.instructor.services.InstructorService;
import com.app.lms.modules.student.dtos.StudentDTO;
import com.app.lms.modules.student.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMainRepository accountMainRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private InstructorService instructorService;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> accountMainRepository.findByAccountUsername(username);
    }

    @Override
    public AccountDTO attachAccount(AccountDTO accountData, AccountTypeEnum accountType, UUID userUuid) throws Exception {

        // Check if it's Exist or Not
        boolean checkAccount = checkUserAccountIfExists(accountType, userUuid);


        if (checkAccount) throw new Exception("Can't process because account already has a Account");

        AccountDTO account;
        if (accountType == AccountTypeEnum.STUDENT) {
            accountData = createAccount(accountData, AccountTypeEnum.STUDENT);
            account = attachAccountToStudent(userUuid, accountData);
        } else if (accountType == AccountTypeEnum.INSTRUCTOR) {
            accountData = createAccount(accountData, AccountTypeEnum.INSTRUCTOR);
            account = attachAccountToInstructor(userUuid, accountData);
        } else if (accountType == AccountTypeEnum.ADMINISTRATOR) {
            accountData = createAccount(accountData, AccountTypeEnum.ADMINISTRATOR);
            account = attachAccountToInstructor(userUuid, accountData);
        } else {
            throw new Exception("Account Type Not Found. Please select one of these: STUDENT, INSTRUCTOR, ADMINISTRATOR");
        }

        return account;
    }

    @Override
    public AccountDTO findOneAccountByAccountUsername(String username) throws NotFoundException {
        List<AccountDTO> accounts = this.findAccountsByAccountUsername(username);

        if (accounts.isEmpty()) {
            throw new NotFoundException("Account Not Found");
        }

        return accounts.get(0);
    }

    @Override
    public List<AccountDTO> findAccountsByAccountUsername(String username) {
        List<AccountEntity> accountEntities = accountMainRepository.findAccountByUsername(username);

        return ObjectMapperUtil.mapAll(accountEntities, AccountDTO.class);
    }

    @Override
    public void detachAccount(UUID accountUuid) throws NotFoundException {
        AccountDTO existingAccount = findAccountByUuid(accountUuid);

        if (existingAccount.getAccountType() == AccountTypeEnum.STUDENT) {

        }

        if (existingAccount.getAccountType() == AccountTypeEnum.INSTRUCTOR) {

        }

        // TODO: Create a process to delete Account UUID from Administrator
        if (existingAccount.getAccountType() == AccountTypeEnum.ADMINISTRATOR) {

        }
    }

    @Override
    public void deleteAccountByUuid(UUID accountUuid) {

        accountMainRepository.deleteById(accountUuid);

    }

    @Override
    public void deleteAccountByUsername(String username) {
        accountMainRepository.deleteByAccountUsername(username);
    }

    private AccountDTO findAccountByUuid(UUID accountUuid) throws NotFoundException {
        AccountEntity account = JpaResultHelperUtil.getSingleResultFromOptional(accountMainRepository.findById(accountUuid));

        if (account == null) {
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
        if (AccountTypeEnum.STUDENT == accountType) {
            return !studentService.findByAccountUuid(userUuid).isEmpty();
        } else if (AccountTypeEnum.INSTRUCTOR == accountType) {
            return !instructorService.findByAccountUuid(userUuid).isEmpty();
        } else if (AccountTypeEnum.ADMINISTRATOR == accountType) {
            return !studentService.findByAccountUuid(userUuid).isEmpty();
        } else {
            return false;
        }
    }

    private AccountDTO attachAccountToStudent(UUID studentUuid, AccountDTO account) throws NotFoundException {

        // Find Data by UUID
        StudentDTO student = studentService.getStudentByUuid(studentUuid);

        // Update StudentAccount from fetched Data Before
        student.setStudentAccount(ObjectMapperUtil.map(account, AccountDTO.class));

        // Update value
        studentService.updateStudentByUuid(studentUuid, ObjectMapperUtil.map(student, StudentDTO.class));

        return account;
    }

    private AccountDTO attachAccountToAdministrator(UUID administratorUuid, AccountDTO account) {
        //        TODO: Create administrator
//        StudentEntity administrator = JpaResultHelperUtil.getSingleResultFromOptional(studentService.findById(administratorUuid));
//
//        administrator.setStudentAccount(ObjectMapperUtil.map(account, AccountEntity.class));

        return account;
    }

    private AccountDTO attachAccountToInstructor(UUID instructorUuid, AccountDTO account) throws NotFoundException {

        // Find Data by UUID
        InstructorDTO instructor = instructorService.getInstructorByUuid(instructorUuid);

        // Update InstructorAccount from fetched Data Before
        instructor.setInstructorAccount(ObjectMapperUtil.map(account, AccountDTO.class));

        // Update value
        instructorService.updateInstructorByUuid(instructorUuid, ObjectMapperUtil.map(instructor, InstructorDTO.class));

        return account;
    }
}
