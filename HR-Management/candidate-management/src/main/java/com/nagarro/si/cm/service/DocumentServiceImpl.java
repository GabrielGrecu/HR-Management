package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.DocumentDto;
import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.entity.Document;
import com.nagarro.si.cm.entity.DocumentType;
import com.nagarro.si.cm.repository.CandidateRepository;
import com.nagarro.si.cm.repository.DocumentRepository;
import com.nagarro.si.cm.mapper.DocumentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final CandidateRepository candidateRepository;

    private final DocumentMapper documentMapper;

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(CandidateRepository candidateRepository, DocumentMapper documentMapper, DocumentRepository documentRepository) {
        this.candidateRepository = candidateRepository;
        this.documentMapper = documentMapper;
        this.documentRepository = documentRepository;
    }

    @Override
    public DocumentDto uploadDocument(int candidateId, MultipartFile file, String documentType) throws IOException {
        DocumentType type;
        try {
            type = DocumentType.valueOf(documentType.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid document type: " + documentType);
        }

        Optional<Candidate> candidateOptional = candidateRepository.findById(candidateId);
        if (candidateOptional.isEmpty()) {
            throw new RuntimeException("Candidate not found with ID: " + candidateId);
        }
        Candidate candidate = candidateOptional.get();

        Blob content;
        try {
            content = new SerialBlob(file.getBytes());
        } catch (Exception e) {
            throw new IOException("Error converting file to Blob", e);
        }

        Document document = new Document();
        document.setType(type);
        document.setName(file.getOriginalFilename());
        document.setContent(content);
        document.setCandidate(candidate);

        document = documentRepository.save(document);

        return documentMapper.toDTO(document);
    }

    @Override
    public DocumentDto downloadDocument(int documentId) {
        Optional<Document> documentOptional = documentRepository.findById(documentId);
        if (documentOptional.isEmpty()) {
            throw new RuntimeException("Document not found with ID: " + documentId);
        }
        Document document = documentOptional.get();

        return documentMapper.toDTO(document);
    }
}