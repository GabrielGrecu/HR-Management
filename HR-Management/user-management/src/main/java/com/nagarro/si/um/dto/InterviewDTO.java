package com.nagarro.si.um.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nagarro.si.um.entity.InterviewType;
import lombok.Builder;

import java.sql.Timestamp;
import java.util.List;

@Builder
public record InterviewDTO(
        List<String> attendeesEmails,
        String candidateEmail,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
        Timestamp startDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
        Timestamp endDate,
        InterviewType interviewType,
        String description,
        String subject
) {
        public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
}
