package com.nagarro.si.cm.controller;

import com.nagarro.si.common.dto.FeedbackDto;
import com.nagarro.si.cm.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public FeedbackDto createFeedback(@Validated @RequestBody FeedbackDto feedbackDto) {
        return feedbackService.saveFeedback(feedbackDto);
    }

    @DeleteMapping("/{feedbackId}")
    public void deleteFeedback(@PathVariable("feedbackId") int feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
    }

    @GetMapping("/candidate/{candidateId}")
    public List<FeedbackDto> getFeedbackByCandidateId(@PathVariable("candidateId") int candidateId) {
        return feedbackService.findFeedbackByCandidateId(candidateId);
    }

    @GetMapping("/candidate/email/{email}")
    public List<FeedbackDto> getFeedbackByCandidateEmail(@PathVariable("email") String email) {
        return feedbackService.findFeedbackByCandidateEmail(email);
    }

    @GetMapping("/candidate/phone/{phoneNumber}")
    public List<FeedbackDto> getFeedbackByCandidatePhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        return feedbackService.findFeedbackByCandidatePhoneNumber(phoneNumber);
    }

    @PutMapping("/{feedbackId}")
    public FeedbackDto updateFeedback(@PathVariable("feedbackId") int feedbackId, @Validated @RequestBody FeedbackDto feedbackDto) {
        return feedbackService.updateFeedback(feedbackId, feedbackDto);
    }
}
