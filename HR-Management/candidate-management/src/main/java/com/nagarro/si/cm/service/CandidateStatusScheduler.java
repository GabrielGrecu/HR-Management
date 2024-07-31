package com.nagarro.si.cm.service;

import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.entity.Status;
import com.nagarro.si.cm.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateStatusScheduler {

    private final CandidateRepository candidateRepository;

    @Autowired
    public CandidateStatusScheduler(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Scheduled(cron = "0 0 0 1 */3 *")
    public void updateCandidateStatus() {
        List<Candidate> candidates = candidateRepository.findAll();
        for (Candidate candidate : candidates) {
            candidate.setCandidateStatus(Status.valueOf("Archived"));
            candidateRepository.save(candidate);
        }
    }
}
