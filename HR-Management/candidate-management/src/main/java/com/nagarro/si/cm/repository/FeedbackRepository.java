package com.nagarro.si.cm.repository;

import com.nagarro.si.cm.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByCandidateId(int candidateId);
    List<Feedback> findByCandidateEmail(String email);
    List<Feedback> findByCandidatePhoneNumber(String phoneNumber);
}
