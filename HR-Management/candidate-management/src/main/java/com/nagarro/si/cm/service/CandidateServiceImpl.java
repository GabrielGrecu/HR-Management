package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.exception.ResourceNotFoundException;
import com.nagarro.si.cm.repository.CandidateRepository;
import com.nagarro.si.cm.util.CandidateMapper;
import com.nagarro.si.cm.util.CandidateSpecification;
import com.nagarro.si.cm.util.CandidateValidator;
import com.nagarro.si.cm.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final CandidateValidator candidateValidator;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository,
                                CandidateMapper candidateMapper,
                                CandidateValidator candidateValidator) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
        this.candidateValidator = candidateValidator;
    }

    @Override
    public CandidateDto saveCandidate(CandidateDto candidateDto) {
        ValidatorUtil.validate(candidateDto);
        Candidate candidate = candidateMapper.toCandidate(candidateDto);
        Candidate savedCandidate = candidateRepository.save(candidate);
        return candidateMapper.toDTO(savedCandidate);
    }

    @Override
    public List<CandidateDto> getAllCandidates() {
        List<Candidate> candidates = candidateRepository.findAll();
        return candidates.stream()
                .map(candidateMapper::toDTO)
                .toList();
    }

    @Override
    public CandidateDto getCandidateById(int candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId).
                orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Candidate with id = %d not found", candidateId)
                ));
        return candidateMapper.toDTO(candidate);
    }

    @Override
    public CandidateDto getCandidateByUsername(String username) {
        Candidate candidate = candidateRepository.getCandidateByUsername(username).
                orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Candidate with username = %s not found", username)
                ));
        return candidateMapper.toDTO(candidate);
    }

    public List<CandidateDto> filterCandidatesByAnyField(Map<String, Object> filters) {
        Specification<Candidate> spec = CandidateSpecification.createDynamicSearchSpecification(filters);
        List<Candidate> candidates = candidateRepository.findAll(spec);
        if(candidates.isEmpty()) {
            throw new ResourceNotFoundException("No candidates were found matching the provided filters");
        }
        return candidates.stream().map(candidateMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteCandidateById(int candidateId) {
        checkIfCandidateExistsOrThrow(candidateId);
        candidateRepository.deleteById(candidateId);
    }

    private void checkIfCandidateExistsOrThrow(int candidateId) {
        if (!candidateRepository.existsCandidateById(candidateId)) {
            throw new ResourceNotFoundException(
                    "candidate with id [%s] not found".formatted(candidateId)
            );
        }
    }

    public void updateCandidate(Integer candidateId, CandidateDto candidateDto) throws ParseException {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Candidate with id [%s] not found".formatted(candidateId)));

        ValidatorUtil.validate(candidateDto);
        candidateValidator.updateCandidateFields(candidate, candidateDto);
        candidateRepository.save(candidate);
    }


    @Override
    public void patchCandidate(Integer candidateId, CandidateDto updateRequest) throws ParseException {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Candidate with id [%s] not found".formatted(candidateId)));

        ValidatorUtil.validate(updateRequest);
        candidateValidator.updateCandidateFields(candidate, updateRequest);
        candidateRepository.save(candidate);
    }
}
