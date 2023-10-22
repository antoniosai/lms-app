package com.app.lms.modules.profile.controllers;

import com.app.lms.core.exceptions.NotFoundException;

import java.security.Principal;

public interface ProfileController {
    String currentUserName(Principal principal) throws NotFoundException;
}
