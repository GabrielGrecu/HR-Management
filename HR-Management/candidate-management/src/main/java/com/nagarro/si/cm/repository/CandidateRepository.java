package com.nagarro.si.cm.repository;

import com.nagarro.si.cm.entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {
    boolean existsCandidateById(Integer id);
    boolean existsCandidateByEmail(String email);
    boolean existsCandidateByUsername(String username);
    boolean existsCandidateByPhoneNumber(String phoneNumber);

}
