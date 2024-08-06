package com.nagarro.si.cm.controller;

import com.nagarro.si.cm.dto.DocumentDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<DocumentDto> uploadDocument(
            @RequestParam("candidateId") int candidateId,
            @RequestParam("documentType") String documentType,
            @RequestParam("file") MultipartFile file) {

        try {
            DocumentDto documentDto = documentService.uploadDocument(candidateId, file, documentType);
            return ResponseEntity.status(HttpStatus.CREATED).body(documentDto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }
    }

    @GetMapping("/download/{documentId}")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable int documentId) {
        try {
            DocumentDto documentDto = documentService.downloadDocument(documentId);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + documentDto.getName() + "\"")
                    .contentType(getMediaTypeForDocument(documentDto.getType()))
                    .body(documentDto.getContent());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    private MediaType getMediaTypeForDocument(DocumentType documentType) {
        return switch (documentType) {
            case PDF -> MediaType.APPLICATION_PDF;
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }
}