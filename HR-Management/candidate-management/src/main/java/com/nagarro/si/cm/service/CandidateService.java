package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.CandidateDto;

import java.util.List;

public interface CandidateService {

    CandidateDto saveCandidate(CandidateDto candidateDto);
    List<CandidateDto> getAllCandidates();
    CandidateDto getCandidateById(int candidateId);
}
