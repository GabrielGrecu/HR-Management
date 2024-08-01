package com.nagarro.si.cm.service;

import com.nagarro.si.common.dto.CandidateDto;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface CandidateService {

    CandidateDto saveCandidate(CandidateDto candidateDto);

    List<CandidateDto> getAllCandidates();

    CandidateDto getCandidateById(int candidateId);

    CandidateDto getCandidateByUsername(String username);

    CandidateDto getCandidateByEmail(String email);

    List<CandidateDto> filterCandidatesByAnyField(Map<String, Object> filters);

    void deleteCandidateById(int candidateId);

    void updateCandidate(Integer candidateId, CandidateDto candidateDto) throws ParseException;

    void patchCandidate(Integer candidateId, CandidateDto updateRequest) throws ParseException;
}
