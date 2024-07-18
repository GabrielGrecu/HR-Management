package com.nagarro.si.cm.controller;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public CandidateDto createCandidate(@RequestBody CandidateDto candidateDto) {
        return candidateService.saveCandidate(candidateDto);
    }

    @GetMapping
    public List<CandidateDto> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @GetMapping("/{candidateId}")
    public CandidateDto getCandidateById(@PathVariable("candidateId") Integer candidateId) {
        return candidateService.getCandidateById(candidateId);
    }
}
