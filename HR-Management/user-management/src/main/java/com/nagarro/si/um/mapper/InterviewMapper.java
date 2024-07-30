package com.nagarro.si.um.mapper;

import com.nagarro.si.um.dto.InterviewDTO;
import com.nagarro.si.um.entity.Interview;
import com.nagarro.si.um.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class InterviewMapper {

    public InterviewDTO toInterviewDTO(Interview interview) {
        return InterviewDTO.builder()
                .attendeesEmails(interview.getUsers().stream().map(User::getEmail).collect(Collectors.toList()))
                .candidateEmail(interview.getCandidateEmail())
                .startDate(interview.getStartDate())
                .endDate(interview.getEndDate())
                .interviewType(interview.getInterviewType())
                .description(interview.getDescription())
                .subject(interview.getSubject())
                .build();
    }

    public Interview toInterview(InterviewDTO interviewDTO) {
        return Interview.builder()
                .candidateEmail(interviewDTO.candidateEmail())
                .startDate(interviewDTO.startDate())
                .endDate(interviewDTO.endDate())
                .interviewType(interviewDTO.interviewType())
                .description(interviewDTO.description())
                .subject(interviewDTO.subject())
                .build();
    }
}
