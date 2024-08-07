package com.nagarro.si.cm.controller;

import com.nagarro.si.cm.dto.DocumentDto;
import com.nagarro.si.cm.dto.DocumentSummaryDto;
import com.nagarro.si.cm.entity.DocumentType;
import com.nagarro.si.cm.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<DocumentSummaryDto> uploadDocument(
            @RequestParam("candidateId") int candidateId,
            @RequestParam("documentType") String documentType,
            @RequestParam("file") MultipartFile file) throws IOException, SQLException {

        DocumentSummaryDto documentSummaryDto = documentService.uploadDocument(candidateId, file, documentType);
        return ResponseEntity.status(HttpStatus.CREATED).body(documentSummaryDto);
    }

    @GetMapping("/download/{candidateId}/{documentName}")
    public ResponseEntity<byte[]> downloadCandidateDocument(
            @PathVariable int candidateId,
            @PathVariable String documentName) {

        DocumentDto documentDto = documentService.downloadDocumentByCandidateIdAndName(candidateId, documentName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documentDto.getName() + "\"")
                .contentType(getMediaTypeForDocument(documentDto.getType()))
                .body(documentDto.getContent());
    }

    @GetMapping("/candidate/{candidateId}")
    public ResponseEntity<List<DocumentSummaryDto>> getDocumentsByCandidateId(@PathVariable int candidateId) {
        List<DocumentSummaryDto> documents = documentService.getDocumentsByCandidateId(candidateId);
        return ResponseEntity.ok(documents);
    }

    @DeleteMapping("/candidate/{candidateId}/{documentName}")
    public ResponseEntity<String> deleteDocument(@PathVariable int candidateId, @PathVariable String documentName) {
        documentService.deleteDocumentByCandidateIdAndName(candidateId, documentName);
        return ResponseEntity.ok("Document deleted successfully.");
    }

    @GetMapping("/download/{documentId}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable int documentId) {
        DocumentDto documentDto = documentService.downloadDocument(documentId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documentDto.getName() + "\"")
                .contentType(getMediaTypeForDocument(documentDto.getType()))
                .body(documentDto.getContent());
    }

    private MediaType getMediaTypeForDocument(DocumentType documentType) {
        if (documentType == DocumentType.PDF) {
            return MediaType.APPLICATION_PDF;
        }
        return MediaType.APPLICATION_OCTET_STREAM;
    }
}