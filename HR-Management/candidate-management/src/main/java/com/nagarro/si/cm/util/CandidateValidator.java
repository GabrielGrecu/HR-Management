package com.nagarro.si.cm.util;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.exception.DuplicateResourceException;
import com.nagarro.si.cm.repository.CandidateRepository;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class CandidateValidator {

    private final CandidateRepository candidateRepository;

    public CandidateValidator(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public boolean updateCandidateFields(Candidate candidate, CandidateDto candidateDto) throws ParseException {
        boolean changes = false;

        if (candidateDto.getUsername() != null && !candidateDto.getUsername().equals(candidate.getUsername())) {
            if (candidateRepository.existsCandidateByUsername(candidateDto.getUsername())) {
                throw new DuplicateResourceException("Username already taken");
            }
            candidate.setUsername(candidateDto.getUsername());
            changes = true;
        }

        if (candidateDto.getBirthday() != null && !candidateDto.getBirthday().equals(candidate.getBirthday())) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(candidateDto.getBirthday());
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

            candidate.setBirthday(sqlDate);
            changes = true;
        }

        if (candidateDto.getEmail() != null && !candidateDto.getEmail().equals(candidate.getEmail())) {
            if (candidateRepository.existsCandidateByEmail(candidateDto.getEmail())) {
                throw new DuplicateResourceException("Email already taken");
            }
            candidate.setEmail(candidateDto.getEmail());
            changes = true;
        }

        if (candidateDto.getCity() != null && !candidateDto.getCity().equals(candidate.getCity())) {
            candidate.setCity(candidateDto.getCity());
            changes = true;
        }

        if (candidateDto.getFaculty() != null && !candidateDto.getFaculty().equals(candidate.getFaculty())) {
            candidate.setFaculty(candidateDto.getFaculty());
            changes = true;
        }

        if (candidateDto.getPhoneNumber() != null && !candidateDto.getPhoneNumber().equals(candidate.getPhoneNumber())) {
            if (candidateRepository.existsCandidateByPhoneNumber(candidateDto.getPhoneNumber())) {
                throw new DuplicateResourceException("Phone number already taken");
            }
            candidate.setPhoneNumber(candidateDto.getPhoneNumber());
            changes = true;
        }

        if (candidateDto.getYearsOfExperience() != 0 && candidateDto.getYearsOfExperience() != candidate.getYearsOfExperience()) {
            candidate.setYearsOfExperience(candidateDto.getYearsOfExperience());
            changes = true;
        }

        if (candidateDto.getRecruitmentChannel() != null && !candidateDto.getRecruitmentChannel().equals(candidate.getRecruitmentChannel())) {
            candidate.setRecruitmentChannel(candidateDto.getRecruitmentChannel());
            changes = true;
        }

        return changes;
    }
}
