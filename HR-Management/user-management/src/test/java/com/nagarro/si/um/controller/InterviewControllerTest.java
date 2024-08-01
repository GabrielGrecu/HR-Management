package com.nagarro.si.um.controller;

import com.nagarro.si.um.dto.InterviewDTO;
import com.nagarro.si.um.entity.InterviewType;
import com.nagarro.si.um.service.InterviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InterviewControllerTest {

    @Mock
    private InterviewService interviewService;

    @InjectMocks
    private InterviewController interviewController;

    private InterviewDTO interviewDTO;
    private InterviewDTO scheduledInterviewDTO;

    @BeforeEach
    public void setup() {
        interviewDTO = new InterviewDTO(
                Arrays.asList("d@gmail.com", "a@yahoo.com", "m@gmail.com"),
                "dia@gmail.com",
                Timestamp.valueOf("2024-07-26 14:30:00"),
                Timestamp.valueOf("2024-07-26 15:30:00"),
                InterviewType.HR,
                "Interview for software dev position",
                "HR Interview"
        );
        scheduledInterviewDTO = new InterviewDTO(
                Arrays.asList("d@gmail.com", "a@yahoo.com", "m@gmail.com"),
                "dia@gmail.com",
                Timestamp.valueOf("2024-07-26 14:30:00"),
                Timestamp.valueOf("2024-07-26 15:30:00"),
                InterviewType.HR,
                "Interview for software dev position",
                "HR Interview"
        );
    }

    @Test
    public void testScheduleInterview() {
        when(interviewService.scheduleInterview(interviewDTO)).thenReturn(scheduledInterviewDTO);

        InterviewDTO result = interviewController.scheduleInterview(interviewDTO);

        assertNotNull(result);
        assertEquals(scheduledInterviewDTO, result);
        verify(interviewService, times(1)).scheduleInterview(interviewDTO);
    }
}
