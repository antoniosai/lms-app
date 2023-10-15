package com.app.lms.modules.admin_area.instructor.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/admin-area/instructors")
@Validated
public class InstructorControllerImpl implements InstructorController {
}
