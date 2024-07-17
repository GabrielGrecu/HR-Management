package com.nagarro.si.cm.repository;

import com.nagarro.si.cm.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

}
