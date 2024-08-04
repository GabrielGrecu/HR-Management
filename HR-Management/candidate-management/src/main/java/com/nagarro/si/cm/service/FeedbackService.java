package com.nagarro.si.cm.service;

import com.nagarro.si.common.dto.FeedbackDto;

import java.util.List;

public interface FeedbackService {
    FeedbackDto saveFeedback(FeedbackDto feedbackDto);
    void deleteFeedback(int feedbackId);
    List<FeedbackDto> findFeedbackByCandidateId(int candidateId);
    List<FeedbackDto> findFeedbackByCandidateEmail(String email);
    List<FeedbackDto> findFeedbackByCandidatePhoneNumber(String phoneNumber);
    FeedbackDto updateFeedback(int feedbackId, FeedbackDto feedbackDto);
}
