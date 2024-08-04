package com.nagarro.si.cm.service;

import com.nagarro.si.common.dto.FeedbackDto;
import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.entity.Feedback;
import com.nagarro.si.cm.exception.EntityNotFoundException;
import com.nagarro.si.cm.mapper.FeedbackMapper;
import com.nagarro.si.cm.repository.CandidateRepository;
import com.nagarro.si.cm.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final CandidateRepository candidateRepository;
    private final FeedbackMapper feedbackMapper;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, CandidateRepository candidateRepository, FeedbackMapper feedbackMapper) {
        this.feedbackRepository = feedbackRepository;
        this.candidateRepository = candidateRepository;
        this.feedbackMapper = feedbackMapper;
    }

    @Override
    public FeedbackDto saveFeedback(FeedbackDto feedbackDto) {
        Candidate candidate = candidateRepository.findById(feedbackDto.getCandidateId())
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found"));

        Feedback feedback = feedbackMapper.toFeedback(feedbackDto);
        feedback.setCandidate(candidate);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return feedbackMapper.toDto(savedFeedback);
    }

    @Override
    public void deleteFeedback(int feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
        feedbackRepository.delete(feedback);
    }

    @Override
    public List<FeedbackDto> findFeedbackByCandidateId(int candidateId) {
        List<Feedback> feedbacks = feedbackRepository.findByCandidateId(candidateId);
        return feedbacks.stream().map(feedbackMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDto> findFeedbackByCandidateEmail(String email) {
        List<Feedback> feedbacks = feedbackRepository.findByCandidateEmail(email);
        return feedbacks.stream().map(feedbackMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<FeedbackDto> findFeedbackByCandidatePhoneNumber(String phoneNumber) {
        List<Feedback> feedbacks = feedbackRepository.findByCandidatePhoneNumber(phoneNumber);
        return feedbacks.stream().map(feedbackMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public FeedbackDto updateFeedback(int feedbackId, FeedbackDto feedbackDto) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));

        feedback.setComment(feedbackDto.getComment());
        feedback.setStatus(feedbackDto.getStatus());

        Feedback updatedFeedback = feedbackRepository.save(feedback);
        return feedbackMapper.toDto(updatedFeedback);
    }
}
