package com.nagarro.si.cm.controller;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.service.CandidateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    public CandidateDto createCandidate(@Valid @RequestBody CandidateDto candidateDto) {
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

    @GetMapping("/username/{username}")
    public CandidateDto getCandidateByUsername(@PathVariable("username") String username) {
        return candidateService.getCandidateByUsername(username);
    }

    @GetMapping("/search")
    public List<CandidateDto> searchCandidates(@RequestParam Map<String, Object> params) {
        return candidateService.filterCandidatesByAnyField(params);
    }

    @DeleteMapping("{candidateId}")
    public void deleteCandidate(@PathVariable("candidateId") Integer candidateId) {
        candidateService.deleteCandidateById(candidateId);
    }

    @PutMapping("/{candidateId}")
    public void updateCandidate(
            @PathVariable("candidateId") Integer candidateId,
            @Valid @RequestBody CandidateDto updateRequest) throws ParseException {
        candidateService.updateCandidate(candidateId, updateRequest);
    }

    @PatchMapping("/{candidateId}")
    public void patchUpdateCandidate(
            @PathVariable("candidateId") Integer candidateId,
            @Valid @RequestBody CandidateDto candidateDto) throws ParseException {
        candidateService.patchCandidate(candidateId, candidateDto);
    }
}
