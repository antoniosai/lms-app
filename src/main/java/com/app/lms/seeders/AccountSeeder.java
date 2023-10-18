package com.app.lms.seeders;

import com.app.lms.enums.AccountTypeEnum;
import com.app.lms.modules.admin_area.account.dtos.AccountDTO;
import com.app.lms.modules.admin_area.account.services.AccountService;
import com.app.lms.modules.admin_area.student.dtos.StudentDTO;
import com.app.lms.modules.admin_area.student.services.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class AccountSeeder implements CommandLineRunner {

    @Autowired
    private StudentService studentService;

    @Autowired
    private AccountService accountService;

    @Override
    public void run(String... args) throws Exception {
        log.info("Seeding an Account Data");

//        studentSeeder();

        log.info("Finish Seeding Account Data");
    }

    private void studentSeeder() throws Exception {

//        accountService.deleteAccountByUsername("antoniosai");
//        studentService.deleteStudentByIdentificationNUmber("32050909960004");

        StudentDTO newStudentData = new StudentDTO();
        newStudentData.setStudentUuid(UUID.randomUUID());
        newStudentData.setStudentAddress("Jl. Semangka No. 121");
        newStudentData.setStudentEmail("finallyantonio@gmail.com");
        newStudentData.setStudentName("Antonio Saiful Islam");
        newStudentData.setStudentBirthDate("1996-09-09");
        newStudentData.setStudentBirthPlace("Garut");
        newStudentData.setStudentIdentificationNumber("32050909960004");

        StudentDTO savedStudentEntity = studentService.createNewStudent(newStudentData);

        createAccount(AccountTypeEnum.STUDENT, savedStudentEntity.getStudentUuid());

    }

    private void createAccount(AccountTypeEnum accountType, UUID userUuid) throws Exception {
        AccountDTO newAccount = new AccountDTO();
        newAccount.setAccountPassword("antoniosai");
        newAccount.setAccountUsername("antoniosai");
        newAccount.setAccountType(AccountTypeEnum.STUDENT);

        accountService.attachAccount(newAccount, accountType, userUuid);
    }

}
