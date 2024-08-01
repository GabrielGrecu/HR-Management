package com.nagarro.si.cm.service;

import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.common.dto.Status;
import com.nagarro.si.cm.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class CandidateStatusScheduler {

    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateStatusScheduler(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateCandidateStatus() {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        Date threeMonthsAgoDate = Date.valueOf(threeMonthsAgo);

        List<Candidate> candidates = candidateRepository.findCandidatesForArchiving(
                Arrays.asList(Status.IN_PROGRESS, Status.REJECTED), threeMonthsAgoDate
        );

        candidates.stream()
                .peek(candidate -> {
                    candidate.setCandidateStatus(Status.ARCHIVED);
                    candidate.setStatusDate(Date.valueOf(LocalDate.now()));
                })
                .forEach(candidateRepository::save);
    }
}

