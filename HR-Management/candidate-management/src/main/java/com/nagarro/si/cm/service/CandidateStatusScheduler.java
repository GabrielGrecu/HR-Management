package com.nagarro.si.cm.service;

import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.entity.Status;
import com.nagarro.si.cm.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
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
        List<Candidate> candidates = candidateRepository.findAll();

        for (Candidate candidate : candidates) {
            if (candidate.getCandidateStatus() != Status.ARCHIVED &&
                    candidate.getStatusDate().toLocalDate().isBefore(threeMonthsAgo)) {
                if (candidate.getCandidateStatus() == Status.IN_PROGRESS ||
                        candidate.getCandidateStatus() == Status.REJECTED) {
                    candidate.setCandidateStatus(Status.ARCHIVED);
                    candidate.setStatusDate(Date.valueOf(LocalDate.now()));
                    candidateRepository.save(candidate);
                }
            }
        }
    }
}
