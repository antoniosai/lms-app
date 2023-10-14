package com.app.lms.modules.admin_area.instructor.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/instructor")
@Validated
public class InstructorControllerImpl implements InstructorController {
}
