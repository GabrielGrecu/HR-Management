package com.nagarro.si.cm.util;

import com.nagarro.si.common.dto.CandidateDto;
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
        candidate.setStatusDate(candidateDto.getStatusDate());
        return candidate;
    }

    public CandidateDto toDTO(Candidate candidate) {
        CandidateDto candidateDto = new CandidateDto();
        candidateDto.setId(candidate.getId());
        candidateDto.setUsername(candidate.getUsername());
        candidateDto.setBirthday(convertToLocalDate(candidate.getBirthday()));
        candidateDto.setEmail(candidate.getEmail());
        candidateDto.setCity(candidate.getCity());
        candidateDto.setAddress(candidate.getAddress());
        candidateDto.setFaculty(candidate.getFaculty());
        candidateDto.setPhoneNumber(candidate.getPhoneNumber());
        candidateDto.setYearsOfExperience(candidate.getYearsOfExperience());
        candidateDto.setRecruitmentChannel(candidate.getRecruitmentChannel());
        candidateDto.setCandidateStatus(candidate.getCandidateStatus());
        candidateDto.setStatusDate(candidate.getStatusDate());
        candidateDto.setJobId(candidate.getJob().getId());
        return candidateDto;
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
    }

    private Date convertToDate(LocalDate localDate) {
        return (localDate != null) ? Date.valueOf(localDate) : null;
    }

    private LocalDate convertToLocalDate(Date date) {
        return (date != null) ? date.toLocalDate() : null;
    }
}
