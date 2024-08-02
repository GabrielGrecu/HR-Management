package com.nagarro.si.cm.mapper;

import com.nagarro.si.cm.dto.FeedbackDto;
import com.nagarro.si.cm.entity.Feedback;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {
    public Feedback toFeedback(FeedbackDto feedbackDto) {
        Feedback feedback = new Feedback();
        feedback.setId(feedbackDto.getId());
        feedback.setComment(feedback.getComment());
        feedback.setRole(feedbackDto.getRole());
        feedback.setStatus(feedbackDto.getStatus());
        return feedback;
    }

    public FeedbackDto toDto(Feedback feedback){
        return new FeedbackDto(
                feedback.getId(),
                feedback.getComment(),
                feedback.getRole(),
                feedback.getStatus()
        );
    }
}
