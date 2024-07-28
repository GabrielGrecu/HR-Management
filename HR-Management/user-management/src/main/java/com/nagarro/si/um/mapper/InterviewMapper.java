package com.nagarro.si.um.mapper;

import com.nagarro.si.um.dto.InterviewDTO;
import com.nagarro.si.um.entity.Interview;
import com.nagarro.si.um.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class InterviewMapper {

    public InterviewDTO toInterviewDTO(Interview interview) {
        return new InterviewDTO(
                interview.getUsers().stream().map(User::getEmail).collect(Collectors.toList()),
                interview.getCandidateEmail(),
                interview.getStartDate(),
                interview.getEndDate(),
                interview.getInterviewType(),
                interview.getDescription(),
                interview.getSubject()
        );
    }

    public Interview toInterview(InterviewDTO interviewDTO) {
        Interview interview = new Interview();
        interview.setCandidateEmail(interviewDTO.candidateEmail());
        interview.setStartDate(interviewDTO.startDate());
        interview.setEndDate(interviewDTO.endDate());
        interview.setInterviewType(interviewDTO.interviewType());
        interview.setDescription(interviewDTO.description());
        interview.setSubject(interviewDTO.subject());
        return interview;
    }
}
