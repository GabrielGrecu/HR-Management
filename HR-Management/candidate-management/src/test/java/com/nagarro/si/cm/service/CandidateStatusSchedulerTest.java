package com.nagarro.si.cm.service;

import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.repository.CandidateRepository;
import com.nagarro.si.common.dto.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CandidateStatusSchedulerTest {

    @InjectMocks
    private CandidateStatusScheduler candidateStatusScheduler;

    @Mock
    private CandidateRepository candidateRepository;

    private Candidate candidate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        candidate = new Candidate();
        candidate.setId(1);
        candidate.setUsername("john_doe");
        candidate.setBirthday(Date.valueOf(LocalDate.of(1990, 1, 1)));
        candidate.setEmail("john.doe@example.com");
        candidate.setCity("New York");
        candidate.setAddress("123 Elm Street");
        candidate.setFaculty("Engineering");
        candidate.setPhoneNumber("+1234567890");
        candidate.setYearsOfExperience(5);
        candidate.setRecruitmentChannel("LinkedIn");
        candidate.setCandidateStatus(Status.REJECTED);
        candidate.setStatusDate(Date.valueOf(LocalDate.now().minusMonths(4)));
    }

    @Test
    public void testUpdateCandidateStatus() {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        Date threeMonthsAgoDate = Date.valueOf(threeMonthsAgo);

        when(candidateRepository.findCandidatesForArchiving(List.of(Status.IN_PROGRESS, Status.REJECTED), threeMonthsAgoDate))
                .thenReturn(List.of(candidate));

        candidateStatusScheduler.updateCandidateStatus();

        verify(candidateRepository).save(candidate);
        assertEquals(Status.ARCHIVED, candidate.getCandidateStatus(), "Candidate status should be ARCHIVED");
    }
}
