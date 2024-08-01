package com.nagarro.si.cm.controller;

import com.nagarro.si.cm.dto.CandidateDto;
import com.nagarro.si.cm.service.CandidateService;
import com.nagarro.si.cm.validator.ValidationGroups;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Tag(name = "Candidate Controller", description = "This is my Candidate Controller")
@Log4j2
@RestController
@RequestMapping("/candidates")
public class CandidateController {

    private final CandidateService candidateService;

    @Autowired
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    public CandidateDto createCandidate(@Validated(ValidationGroups.ValidateUpdate.class) @RequestBody CandidateDto candidateDto) {
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

    @Operation(
            summary = "Update candidate",
            description = "Update an existing candidate's information."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Candidate updated successfully"),
            @ApiResponse(responseCode = "404", description = "Candidate not found"),
            @ApiResponse(responseCode = "400", description = "Invalid update data")
    })
    @PutMapping("/{candidateId}")
    public void updateCandidate(
            @PathVariable("candidateId") Integer candidateId,
            @Validated(ValidationGroups.ValidatePatch.class) @RequestBody CandidateDto updateRequest) throws ParseException {
        log.debug("Request to update candidate with ID: {}", candidateId);
        log.debug("Update request details: {}", updateRequest);
        candidateService.updateCandidate(candidateId, updateRequest);
        log.info("Candidate with ID: {} updated successfully", candidateId);
    }

    @PatchMapping("/{candidateId}")
    public void patchUpdateCandidate(
            @PathVariable("candidateId") Integer candidateId,
            @Validated(ValidationGroups.ValidatePatch.class) @RequestBody CandidateDto candidateDto) throws ParseException {
        candidateService.patchCandidate(candidateId, candidateDto);
    }
}
