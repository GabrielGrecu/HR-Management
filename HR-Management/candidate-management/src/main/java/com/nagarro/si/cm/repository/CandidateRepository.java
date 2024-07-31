package com.nagarro.si.cm.repository;

import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer>, JpaSpecificationExecutor<Candidate> {
    boolean existsCandidateById(Integer id);

    boolean existsCandidateByEmail(String email);

    boolean existsCandidateByUsername(String username);

    boolean existsCandidateByPhoneNumber(String phoneNumber);

    Optional<Candidate> getCandidateByUsername(String username);

    List<Candidate> findByCandidateStatus(Status candidateStatus);

    List<Candidate> findByCandidateStatusNot(Status candidateStatus);

}
