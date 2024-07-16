package com.nagarro.si.cm.config;

import com.nagarro.si.cm.dto.CandidateCreationDto;
import com.nagarro.si.cm.dto.CandidateRetrievalDto;
import com.nagarro.si.cm.entity.Candidate;
import org.springframework.stereotype.Component;

@Component
public class CandidateMapper {

    public Candidate toCandidate(CandidateCreationDto candidateCreationDto) {
        return new Candidate(
                candidateCreationDto.getUsername(),
                candidateCreationDto.getBirthDay(),
                candidateCreationDto.getEmail(),
                candidateCreationDto.getCity(),
                candidateCreationDto.getFaculty(),
                candidateCreationDto.getPhoneNumber(),
                candidateCreationDto.getYearsOfExperience(),
                candidateCreationDto.getRecruitmentChannel()
        );
    }

    public CandidateRetrievalDto toDTO(Candidate candidate) {
        return new CandidateRetrievalDto(
                candidate.getUsername(),
                candidate.getEmail(),
                candidate.getPhoneNumber()
        );
    }
}
