package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.DocumentDto;
import com.nagarro.si.cm.dto.DocumentSummaryDto;
import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.entity.Document;
import com.nagarro.si.cm.entity.DocumentType;
import com.nagarro.si.cm.exception.EntityNotFoundException;
import com.nagarro.si.cm.exception.EntityAlreadyExistsException;
import com.nagarro.si.cm.repository.CandidateRepository;
import com.nagarro.si.cm.repository.DocumentRepository;
import com.nagarro.si.cm.mapper.DocumentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

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
    public DocumentSummaryDto uploadDocument(int candidateId, MultipartFile file, String documentType) throws IOException, SQLException {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with ID: " + candidateId));

        String documentName = file.getOriginalFilename();

        boolean duplicateExists = candidate.getDocuments().stream()
                .anyMatch(doc -> doc.getName().equals(documentName));

        if (duplicateExists) {
            throw new EntityAlreadyExistsException("A document with the name '" + documentName + "' already exists for this candidate.");
        }

        Blob documentContent = new SerialBlob(file.getBytes());

        Document newDocument = new Document();
        newDocument.setName(documentName);
        newDocument.setType(DocumentType.valueOf(documentType.toUpperCase()));
        newDocument.setContent(documentContent);
        newDocument.setCandidate(candidate);

        documentRepository.save(newDocument);

        DocumentSummaryDto documentSummaryDto = new DocumentSummaryDto();
        documentSummaryDto.setId(newDocument.getId());
        documentSummaryDto.setType(newDocument.getType());
        documentSummaryDto.setName(newDocument.getName());

        return documentSummaryDto;
    }

    @Override
    public DocumentDto downloadDocument(int documentId) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with ID: " + documentId));

        return documentMapper.toDTO(document);
    }

    @Override
    public List<DocumentSummaryDto> getDocumentsByCandidateId(int candidateId) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with ID: " + candidateId));

        return candidate.getDocuments().stream()
                .map(doc -> new DocumentSummaryDto(doc.getId(), doc.getType(), doc.getName()))
                .toList();
    }

    @Override
    public DocumentDto downloadDocumentByCandidateIdAndName(int candidateId, String documentName) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with ID: " + candidateId));

        Document document = candidate.getDocuments().stream()
                .filter(doc -> doc.getName().equals(documentName))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Document not found with name: " + documentName));

        return documentMapper.toDTO(document);
    }

    @Override
    public void deleteDocumentByCandidateIdAndName(int candidateId, String documentName) {
        candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found with ID: " + candidateId));

        Document document = documentRepository.findByCandidateIdAndName(candidateId, documentName)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with candidate ID: " + candidateId + " and name: " + documentName));

        documentRepository.delete(document);
    }
}
