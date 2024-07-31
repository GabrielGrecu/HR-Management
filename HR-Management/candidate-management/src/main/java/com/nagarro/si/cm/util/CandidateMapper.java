package com.nagarro.si.cm.util;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.entity.Candidate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
public class CandidateMapper {

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
        return candidate;
    }

    public CandidateDto toDTO(Candidate candidate) {
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
                candidate.getStatusDate()
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
        candidate.setStatusDate(Date.valueOf(LocalDate.now()));
    }

    private Date convertToDate(LocalDate localDate) {
        return (localDate != null) ? Date.valueOf(localDate) : null;
    }

    private LocalDate convertToLocalDate(Date date) {
        return (date != null) ? date.toLocalDate() : null;
    }
}
