package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.CandidateDto;

import java.text.ParseException;
import java.util.List;

public interface CandidateService {

    CandidateDto saveCandidate(CandidateDto candidateDto);

    List<CandidateDto> getAllCandidates();

    CandidateDto getCandidateById(int candidateId);

    void deleteCandidateById(int candidateId);

    void updateCandidate(Integer candidateId, CandidateDto candidateDto) throws ParseException;

    void patchCandidate(Integer candidateId, CandidateDto updateRequest) throws ParseException;
}
