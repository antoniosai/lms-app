package com.app.lms.modules.assessment.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController(value = "instructorAssessmentController")
@RequestMapping(path = "/api/v1/instructor-area/assessments")
@PreAuthorize("hasRole('INSTRUCTOR')")
public class AssessmentControllerImpl implements AssessmentController {

}
