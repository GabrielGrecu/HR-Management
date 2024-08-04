package com.nagarro.si.cm.mapper;

import com.nagarro.si.common.dto.CandidateDto;
import com.nagarro.si.common.dto.FeedbackDto;
import com.nagarro.si.cm.entity.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CandidateMapper {

    private final FeedbackMapper feedbackMapper;

    @Autowired
    public CandidateMapper(FeedbackMapper feedbackMapper) {
        this.feedbackMapper = feedbackMapper;
    }

    public Candidate toCandidate(CandidateDto candidateDto) {
        Candidate candidate = new Candidate();
        candidate.setId(candidateDto.getId());
        candidate.setUsername(candidateDto.getUsername());
        candidate.setBirthday(convertToDate(candidateDto.getBirthday()));
        candidate.setEmail(candidateDto.getEmail());
        candidate.setCity(candidateDto.getCity());
        candidate.setAddress(candidateDto.getAddress());
        candidate.setFaculty(candidateDto.getFaculty());
        candidate.setPhoneNumber(candidateDto.getPhoneNumber());
        candidate.setYearsOfExperience(candidateDto.getYearsOfExperience());
        candidate.setRecruitmentChannel(candidateDto.getRecruitmentChannel());
        candidate.setCandidateStatus(candidateDto.getCandidateStatus());
        candidate.setStatusDate(candidateDto.getStatusDate());
        candidate.setFeedbackList(candidateDto.getFeedbacks().stream()
                .map(feedbackMapper::toFeedback)
                .collect(Collectors.toList()));
        return candidate;
    }

    public CandidateDto toDTO(Candidate candidate) {
        List<FeedbackDto> feedbackDtos = candidate.getFeedbackList().stream()
                .map(feedbackMapper::toDto)
                .collect(Collectors.toList());

        return new CandidateDto(
                candidate.getId(),
                candidate.getUsername(),
                convertToLocalDate(candidate.getBirthday()),
                candidate.getEmail(),
                candidate.getCity(),
                candidate.getAddress(),
                candidate.getFaculty(),
                candidate.getPhoneNumber(),
                candidate.getYearsOfExperience(),
                candidate.getRecruitmentChannel(),
                candidate.getCandidateStatus(),
                candidate.getStatusDate(),
                feedbackDtos
        );
    }

    public void updateCandidateFromDto(Candidate candidate, CandidateDto candidateDto) {
        candidate.setUsername(candidateDto.getUsername());
        candidate.setBirthday(convertToDate(candidateDto.getBirthday()));
        candidate.setEmail(candidateDto.getEmail());
        candidate.setCity(candidateDto.getCity());
        candidate.setFaculty(candidateDto.getFaculty());
        candidate.setPhoneNumber(candidateDto.getPhoneNumber());
        candidate.setYearsOfExperience(candidateDto.getYearsOfExperience());
        candidate.setRecruitmentChannel(candidateDto.getRecruitmentChannel());
        candidate.setCandidateStatus(candidateDto.getCandidateStatus());
        candidate.setStatusDate(convertToDate(LocalDate.now()));
        candidate.setFeedbackList(candidateDto.getFeedbacks().stream()
                .map(feedbackMapper::toFeedback)
                .collect(Collectors.toList()));
    }

    private Date convertToDate(LocalDate localDate) {
        return (localDate != null) ? Date.valueOf(localDate) : null;
    }

    private LocalDate convertToLocalDate(Date date) {
        return (date != null) ? date.toLocalDate() : null;
    }
}
