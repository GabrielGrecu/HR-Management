package com.nagarro.si.cm.controller;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    private CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

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

    @DeleteMapping("{candidateId}")
    public void deleteCandidate(@PathVariable("candidateId") Integer candidateId) {
        candidateService.deleteCandidateById(candidateId);
    }

    @PutMapping("/{candidateId}")
    public void updateCandidate(
            @PathVariable("candidateId") Integer candidateId,
            @RequestBody CandidateDto updateRequest) throws ParseException {
        candidateService.updateCandidate(candidateId, updateRequest);
    }

    @PatchMapping("/{candidateId}")
    public void patchUpdateCandidate(
            @PathVariable("candidateId") Integer candidateId,
            @RequestBody CandidateDto candidateDto) throws ParseException {
        candidateService.patchCandidate(candidateId, candidateDto);
    }
}
