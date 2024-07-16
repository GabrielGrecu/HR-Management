package com.nagarro.si.cm.controller;

import com.nagarro.si.cm.config.CandidateMapper;
import com.nagarro.si.cm.dto.CandidateCreationDto;
import com.nagarro.si.cm.dto.CandidateRetrievalDto;
import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private CandidateMapper candidateMapper;

    @PostMapping
    public ResponseEntity<CandidateRetrievalDto> createCandidate(@RequestBody CandidateCreationDto candidateCreationDTO) {
        Candidate candidate = candidateMapper.toCandidate(candidateCreationDTO);
        Candidate savedCandidate = candidateService.saveCandidate(candidate);
        CandidateRetrievalDto candidateRetrievalDto = candidateMapper.toDTO(savedCandidate);
        return new ResponseEntity<>(candidateRetrievalDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CandidateRetrievalDto>> getAllCandidates() {
        List<Candidate> candidates = candidateService.getAllCandidates();
        List<CandidateRetrievalDto> candidateRetrievalDtos = candidates.stream()
                .map(candidateMapper::toDTO)
                .toList();
        return ResponseEntity.ok(candidateRetrievalDtos);
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<CandidateRetrievalDto> getCandidateById(@PathVariable("candidateId") Integer candidateId) {
        Candidate candidate = candidateService.getCandidateById(candidateId);
        CandidateRetrievalDto candidateRetrievalDto = candidateMapper.toDTO(candidate);
        return ResponseEntity.ok(candidateRetrievalDto);
    }
}
