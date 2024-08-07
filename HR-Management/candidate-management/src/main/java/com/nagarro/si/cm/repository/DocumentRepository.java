package com.nagarro.si.cm.repository;

import com.nagarro.si.cm.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Optional<Document> findByCandidateIdAndName(int candidateId, String documentName);
}