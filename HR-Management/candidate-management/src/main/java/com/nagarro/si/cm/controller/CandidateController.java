package com.nagarro.si.cm.controller;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.service.CandidateService;
import com.nagarro.si.cm.validation.EmailValidator;
import com.nagarro.si.cm.validation.PhoneNumberValidator;
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

    @PostMapping
    public ResponseEntity<CandidateDto> createCandidate(@RequestBody CandidateDto candidateDto) {
        CandidateDto savedCandidateDto = candidateService.saveCandidate(candidateDto);
        return new ResponseEntity<>(savedCandidateDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CandidateDto>> getAllCandidates() {
        List<CandidateDto> candidateDtoList = candidateService.getAllCandidates();
        return ResponseEntity.ok(candidateDtoList);
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<CandidateDto> getCandidateById(@PathVariable("candidateId") Integer candidateId) {
        CandidateDto candidateDto = candidateService.getCandidateById(candidateId);
        return ResponseEntity.ok(candidateDto);
    }
}
