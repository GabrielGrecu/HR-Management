package com.nagarro.si.cm.dto;

public record CandidateDto(
        String username,
        String birthday,
        String email,
        String city,
        String faculty,
        String phoneNumber,
        int yearsOfExperience,
        String recruitmentChannel
) {
}
