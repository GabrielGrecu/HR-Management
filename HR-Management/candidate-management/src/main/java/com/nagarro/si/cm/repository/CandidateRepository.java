package com.nagarro.si.cm.repository;

import com.nagarro.si.cm.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

}
