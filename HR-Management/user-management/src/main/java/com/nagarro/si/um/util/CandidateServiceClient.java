package com.nagarro.si.um.util;

import com.nagarro.si.common.dto.CandidateDto;

import java.util.Optional;

public interface CandidateServiceClient {
    Optional<CandidateDto> getCandidateByEmail(String email);
}
