package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.exception.DuplicateResourceException;
import com.nagarro.si.cm.exception.ResourceNotFoundException;
import com.nagarro.si.cm.repository.CandidateRepository;
import com.nagarro.si.cm.util.CandidateMapper;
import com.nagarro.si.cm.util.CandidateValidator;
import com.nagarro.si.cm.util.ValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;
    private final ValidatorUtil validatorUtil;
    private final CandidateValidator candidateValidator;
    private static final Logger logger = LoggerFactory.getLogger(CandidateServiceImpl.class);

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository,
                                CandidateMapper candidateMapper,
                                ValidatorUtil validatorUtil,
                                CandidateValidator candidateValidator) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
        this.validatorUtil = validatorUtil;
        this.candidateValidator = candidateValidator;
    }

    @Override
    public CandidateDto saveCandidate(CandidateDto candidateDto) {
        validatorUtil.validate(candidateDto);
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
    public void deleteCandidateById(int candidateId) {
        checkIfcandidateExistsOrThrow(candidateId);
        candidateRepository.deleteById(candidateId);
    }

    private void checkIfcandidateExistsOrThrow(int candidateId) {
        if (!candidateRepository.existsCandidateById(candidateId)) {
            throw new ResourceNotFoundException(
                    "candidate with id [%s] not found".formatted(candidateId)
            );
        }
    }

    public void updateCandidate(Integer candidateId, CandidateDto candidateDto) throws ParseException {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Candidate with id [%s] not found".formatted(candidateId)
                ));

        validatorUtil.validate(candidateDto);

        try {
            candidateValidator.updateCandidateFields(candidate, candidateDto);
            candidateRepository.save(candidate);
        } catch (DuplicateResourceException e) {
            logger.error("Error updating candidate: {}", e.getMessage());
            throw e;
        }
    }


    @Override
    public void patchCandidate(Integer candidateId, CandidateDto updateRequest) throws ParseException {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Candidate with id [%s] not found".formatted(candidateId)
                ));

        validatorUtil.validate(updateRequest);

        try {
            candidateValidator.updateCandidateFields(candidate, updateRequest);
            candidateRepository.save(candidate);
        } catch (DuplicateResourceException e) {
            logger.error("Error patching candidate: {}", e.getMessage());
            throw e;
        }
    }
}
