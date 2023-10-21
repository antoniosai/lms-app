package com.app.lms.modules.instructor_area.assessment.controllers;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@RequestMapping(path = "/api/v1/administrator-area/assessments")
public class AssessmentControllerImpl implements AssessmentController {

}
