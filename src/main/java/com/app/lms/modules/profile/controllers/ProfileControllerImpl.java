package com.app.lms.modules.profile.controllers;

import com.app.lms.core.dtos.HttpResponseDTO;
import com.app.lms.core.exceptions.NotFoundException;
import com.app.lms.core.utils.ObjectMapperUtil;
import com.app.lms.enums.AccountTypeEnum;
import com.app.lms.modules.admin_area.account.dtos.AccountDTO;
import com.app.lms.modules.profile.dtos.MultipleProfileDTO;
import com.app.lms.modules.profile.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileControllerImpl {

    @Autowired
    private ProfileService profileService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<Object>> currentUserName(Principal principal) throws NotFoundException {

        return new HttpResponseDTO<>(profileService.getProfile(principal.getName()))
                .toResponse("Fetched Profile");

    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpResponseDTO<String>> updateProfile(
            @RequestBody MultipleProfileDTO multipleProfile,
            Authentication authentication
    ) throws NotFoundException {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        AccountDTO account = ObjectMapperUtil.map(userDetails, AccountDTO.class);

        if (account.getAccountType() == AccountTypeEnum.STUDENT) {
            profileService.updateProfile(account.getAccountUsername(), multipleProfile.getStudent());
        } else if (account.getAccountType() == AccountTypeEnum.INSTRUCTOR) {
            profileService.updateProfile(account.getAccountUsername(), multipleProfile.getInstructor());
        } else if (account.getAccountType() == AccountTypeEnum.ADMINISTRATOR) {
            profileService.updateProfile(account.getAccountUsername(), multipleProfile.getAdministrator());
        }

        return new HttpResponseDTO<>("Successfully Updated")
                .toResponse("Successfully Updated");

    }
}
