package com.nagarro.si.cm.mapper;

import com.nagarro.si.cm.exception.EntityNotFoundException;
import com.nagarro.si.common.dto.FeedbackDto;
import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.entity.Feedback;
import com.nagarro.si.cm.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {

    private final CandidateRepository candidateRepository;

    @Autowired
    public FeedbackMapper(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public Feedback toFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback();
        feedback.setId(feedbackDto.getId());
        feedback.setComment(feedbackDto.getComment());
        feedback.setRole(feedbackDto.getRole());
        feedback.setStatus(feedbackDto.getStatus());

        Candidate candidate = candidateRepository.findById(feedbackDto.getCandidateId())
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found"));
        feedback.setCandidate(candidate);

        return feedback;
    }

    public FeedbackDto toDto(Feedback feedback) {
        return new FeedbackDto(
                feedback.getId(),
                feedback.getComment(),
                feedback.getRole(),
                feedback.getStatus(),
                feedback.getCandidate().getId()
        );
    }
}
