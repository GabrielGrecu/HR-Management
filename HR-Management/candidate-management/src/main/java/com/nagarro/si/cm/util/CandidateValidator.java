package com.nagarro.si.cm.util;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.exception.DuplicateResourceException;
import com.nagarro.si.cm.repository.CandidateRepository;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Component
public class CandidateValidator {

    private final CandidateRepository candidateRepository;

    public CandidateValidator(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public void updateCandidateFields(Candidate candidate, CandidateDto candidateDto) throws ParseException {
        if (Objects.nonNull(candidateDto.getUsername()) && !Objects.equals(candidateDto.getUsername(), candidate.getUsername())) {
            if (candidateRepository.existsCandidateByUsername(candidateDto.getUsername())) {
                throw new DuplicateResourceException("Username already taken");
            }
            candidate.setUsername(candidateDto.getUsername());
        }

        if (Objects.nonNull(candidateDto.getBirthday()) && !Objects.equals(candidateDto.getBirthday(), candidate.getBirthday().toString())) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = dateFormat.parse(candidateDto.getBirthday());
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
            candidate.setBirthday(sqlDate);
        }

        if (Objects.nonNull(candidateDto.getEmail()) && !Objects.equals(candidateDto.getEmail(), candidate.getEmail())) {
            if (candidateRepository.existsCandidateByEmail(candidateDto.getEmail())) {
                throw new DuplicateResourceException("Email already taken");
            }
            candidate.setEmail(candidateDto.getEmail());
        }

        if (Objects.nonNull(candidateDto.getCity()) && !Objects.equals(candidateDto.getCity(), candidate.getCity())) {
            candidate.setCity(candidateDto.getCity());
        }

        if (Objects.nonNull(candidateDto.getFaculty()) && !Objects.equals(candidateDto.getFaculty(), candidate.getFaculty())) {
            candidate.setFaculty(candidateDto.getFaculty());
        }

        if (Objects.nonNull(candidateDto.getPhoneNumber()) && !Objects.equals(candidateDto.getPhoneNumber(), candidate.getPhoneNumber())) {
            if (candidateRepository.existsCandidateByPhoneNumber(candidateDto.getPhoneNumber())) {
                throw new DuplicateResourceException("Phone number already taken");
            }
            candidate.setPhoneNumber(candidateDto.getPhoneNumber());
        }

        if (candidateDto.getYearsOfExperience() != 0 && candidateDto.getYearsOfExperience() != candidate.getYearsOfExperience()) {
            candidate.setYearsOfExperience(candidateDto.getYearsOfExperience());
        }

        if (Objects.nonNull(candidateDto.getRecruitmentChannel()) && !Objects.equals(candidateDto.getRecruitmentChannel(), candidate.getRecruitmentChannel())) {
            candidate.setRecruitmentChannel(candidateDto.getRecruitmentChannel());
        }
    }
}
