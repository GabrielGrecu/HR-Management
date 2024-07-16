package com.nagarro.si.cm.util;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.entity.Candidate;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper {

    public Candidate toCandidate(CandidateDto candidateDto) {
        return new Candidate(
                candidateDto.getUsername(),
                java.sql.Date.valueOf(candidateDto.getBirthday()),
                candidateDto.getEmail(),
                candidateDto.getCity(),
                candidateDto.getFaculty(),
                candidateDto.getPhoneNumber(),
                candidateDto.getYearsOfExperience(),
                candidateDto.getRecruitmentChannel()
        );
    }

    public CandidateDto toDTO(Candidate candidate) {
        return new CandidateDto(
                candidate.getUsername(),
                candidate.getBirthday().toString(),
                candidate.getEmail(),
                candidate.getCity(),
                candidate.getFaculty(),
                candidate.getPhoneNumber(),
                candidate.getYearsOfExperience(),
                candidate.getRecruitmentChannel()
        );
    }
}
