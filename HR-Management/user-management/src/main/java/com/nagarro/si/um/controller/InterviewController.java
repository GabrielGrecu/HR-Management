package com.nagarro.si.um.controller;

import com.nagarro.si.um.dto.InterviewDTO;
import com.nagarro.si.um.service.InterviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    @Autowired
    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    public InterviewDTO scheduleInterview(@RequestBody InterviewDTO interviewDTO) {
        return interviewService.scheduleInterview(interviewDTO);
    }
}
