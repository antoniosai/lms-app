package com.app.lms.modules.admin_area.account.services;

import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.JpaResultHelperUtil;
import com.app.lms.core.utils.ObjectMapperUtil;
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
    public AccountDTO attachAccount(AccountDTO accountData, AccountTypeEnum accountType, UUID userUuid) throws NotFoundException {

        log.info("Inserting new Account Data");
        log.info("accountData =>  " + accountData);
        log.info("accountType =>  " + accountType);
        log.info("userUuid =>  " + userUuid);

        AccountDTO account;
        if(accountType == AccountTypeEnum.STUDENT) {
            accountData = createAccount(accountData);
            account = attachAccountToStudent(userUuid, accountData);
        } else if(accountType == AccountTypeEnum.INSTRUCTOR) {
            accountData = createAccount(accountData);
            account = attachAccountToInstructor(userUuid, accountData);
        } else if(accountType == AccountTypeEnum.ADMINISTRATOR) {
            accountData = createAccount(accountData);
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

    private AccountDTO createAccount(AccountDTO newAccountData) {

        newAccountData.setAccountUuid(UUID.randomUUID());

        AccountEntity account = accountMainRepository.save(ObjectMapperUtil.map(newAccountData, AccountEntity.class));

        return ObjectMapperUtil.map(account, AccountDTO.class);
    }

    private AccountDTO attachAccountToStudent(UUID studentUuid, AccountDTO account) {
        StudentEntity student = JpaResultHelperUtil.getSingleResultFromOptional(studentMainRepository.findById(studentUuid));

        student.setStudentAccount(ObjectMapperUtil.map(account, AccountEntity.class));

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
