package com.nagarro.si.cm.service;

import com.nagarro.si.cm.repository.CandidateRepository;
import com.nagarro.si.cm.entity.Candidate;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CandidateStatusSchedulerTest {

    @Autowired
    private CandidateStatusScheduler candidateStatusScheduler;

    @MockBean
    private CandidateRepository candidateRepository;

    @Test
    public void testUpdateCandidateStatus() {
        Candidate candidate = new Candidate();
        candidate.setCandidateStatus("Old Status");
        when(candidateRepository.findAll()).thenReturn(List.of(candidate));

        candidateStatusScheduler.updateCandidateStatus();

        verify(candidateRepository).save(candidate);
        assert(candidate.getCandidateStatus().equals("Archived"));
    }
}
