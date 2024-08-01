package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.entity.Status;
import com.nagarro.si.cm.exception.EntityAlreadyExistsException;
import com.nagarro.si.cm.exception.EntityNotFoundException;
import com.nagarro.si.cm.exception.InvalidBirthdayException;
import com.nagarro.si.cm.repository.CandidateRepository;
import com.nagarro.si.cm.util.CandidateMapper;
import com.nagarro.si.cm.util.CandidateSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository, CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }

    @Override
    public CandidateDto saveCandidate(CandidateDto candidateDto) {
        checkValidation(candidateDto, null);

        candidateDto.setStatusDate(Date.valueOf(LocalDate.now()));

        Candidate candidate = candidateMapper.toCandidate(candidateDto);
        Candidate savedCandidate = candidateRepository.save(candidate);
        return candidateMapper.toDTO(savedCandidate);
    }

    @Override
    public List<CandidateDto> getAllCandidates() {
        List<Candidate> candidates = candidateRepository.findAll();
        return candidates.stream()
                .filter(candidate -> candidate.getCandidateStatus() != Status.ARCHIVED)
                .map(candidateMapper::toDTO)
                .toList();
    }

    @Override
    public List<CandidateDto> getArchivedCandidates() {
        List<Candidate> candidates = candidateRepository.findByCandidateStatus(Status.ARCHIVED);
        return candidates.stream()
                .map(candidateMapper::toDTO)
                .toList();
    }

    @Override
    public CandidateDto getCandidateById(int candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Candidate with id = %d not found", candidateId)
                ));
        return candidateMapper.toDTO(candidate);
    }

    @Override
    public CandidateDto getCandidateByUsername(String username) {
        Candidate candidate = candidateRepository.getCandidateByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Candidate with username = %s not found", username)
                ));
        return candidateMapper.toDTO(candidate);
    }

    @Override
    public List<CandidateDto> filterCandidatesByAnyField(Map<String, Object> filters) {
        Specification<Candidate> spec = CandidateSpecification.createDynamicSearchSpecification(filters);
        List<Candidate> candidates = candidateRepository.findAll(spec);
        if (candidates.isEmpty()) {
            throw new EntityNotFoundException("No candidates were found matching the provided filters");
        }
        return candidates.stream().map(candidateMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteCandidateById(int candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException("Candidate with id [" + candidateId + "] not found"));
        candidateRepository.delete(candidate);
    }

    @Override
    public void updateCandidate(Integer candidateId, CandidateDto candidateDto) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException("Candidate with id [%s] not found".formatted(candidateId)));

        checkValidation(candidateDto, candidate);

        candidateMapper.updateCandidateFromDto(candidate, candidateDto);
        candidateRepository.save(candidate);
    }

    @Override
    public void patchCandidate(Integer candidateId, CandidateDto updateRequest) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException("Candidate with id [%s] not found".formatted(candidateId)));

        checkValidation(updateRequest, candidate);

        partialUpdate(updateRequest, candidate);

        candidateRepository.save(candidate);
    }

    private void partialUpdate(CandidateDto updateRequest, Candidate candidate) {
        if (updateRequest.getUsername() != null) {
            candidate.setUsername(updateRequest.getUsername());
        }
        if (updateRequest.getBirthday() != null) {
            candidate.setBirthday(Date.valueOf(updateRequest.getBirthday()));
        }
        if (updateRequest.getEmail() != null) {
            candidate.setEmail(updateRequest.getEmail());
        }
        if (updateRequest.getCity() != null) {
            candidate.setCity(updateRequest.getCity());
        }
        if (updateRequest.getFaculty() != null) {
            candidate.setFaculty(updateRequest.getFaculty());
        }
        if (updateRequest.getPhoneNumber() != null) {
            candidate.setPhoneNumber(updateRequest.getPhoneNumber());
        }
        if (updateRequest.getYearsOfExperience() != null) {
            candidate.setYearsOfExperience(updateRequest.getYearsOfExperience());
        }
        if (updateRequest.getRecruitmentChannel() != null) {
            candidate.setRecruitmentChannel(updateRequest.getRecruitmentChannel());
        }
    }

    private void checkValidation(CandidateDto candidateDto, Candidate existingCandidate) {
        if (existingCandidate == null || (candidateDto.getEmail() != null && !candidateDto.getEmail().equals(existingCandidate.getEmail()))) {
            if (candidateRepository.existsCandidateByEmail(candidateDto.getEmail())) {
                throw new EntityAlreadyExistsException("Candidate with email " + candidateDto.getEmail() + " already exists");
            }
        }

        if (existingCandidate == null || (candidateDto.getPhoneNumber() != null && !candidateDto.getPhoneNumber().equals(existingCandidate.getPhoneNumber()))) {
            if (candidateRepository.existsCandidateByPhoneNumber(candidateDto.getPhoneNumber())) {
                throw new EntityAlreadyExistsException("Candidate with phone number " + candidateDto.getPhoneNumber() + " already exists");
            }
        }

        if (candidateDto.getBirthday() != null && candidateDto.getBirthday().isAfter(LocalDate.now())) {
            throw new InvalidBirthdayException("Birthday cannot be in the future");
        }
    }
}

