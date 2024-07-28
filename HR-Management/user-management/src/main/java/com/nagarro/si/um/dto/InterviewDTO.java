package com.nagarro.si.um.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nagarro.si.um.entity.InterviewType;

import java.sql.Timestamp;
import java.util.List;

public record InterviewDTO(
        List<String> attendeesEmails,
        String candidateEmail,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") Timestamp startDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss") Timestamp endDate,
        InterviewType interviewType,
        String description,
        String subject
) {
}
