package com.nagarro.si.cm.controller;

import com.nagarro.si.cm.dto.DocumentDto;
import com.nagarro.si.cm.entity.DocumentType;
import com.nagarro.si.cm.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class DocumentControllerTest {

    @Mock
    private DocumentService documentService;

    @InjectMocks
    private DocumentController documentController;

    private final int candidateId = 1;
    private final int documentId = 1;
    private final String documentType = "PDF";
    private MockMultipartFile file;
    private DocumentDto documentDto;

    @BeforeEach
    void setUp() {
        documentDto = new DocumentDto();
        documentDto.setId(documentId);
        documentDto.setType(DocumentType.PDF);
        documentDto.setName("test.pdf");

        file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test content".getBytes());

        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadDocument_Success() throws Exception {
        documentDto.setContent(file.getBytes());

        when(documentService.uploadDocument(eq(candidateId), any(MockMultipartFile.class), eq(documentType))).thenReturn(documentDto);

        ResponseEntity<DocumentDto> response = documentController.uploadDocument(candidateId, documentType, file);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(documentDto.getId(), response.getBody().getId());
        verify(documentService, times(1)).uploadDocument(eq(candidateId), any(MockMultipartFile.class), eq(documentType));
    }

    @Test
    void testUploadDocument_InvalidDocumentType() throws Exception {
        String documentType = "INVALID";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test content".getBytes());

        when(documentService.uploadDocument(eq(candidateId), any(MockMultipartFile.class), eq(documentType)))
                .thenThrow(new RuntimeException("Invalid document type: " + documentType));

        ResponseEntity<DocumentDto> response = documentController.uploadDocument(candidateId, documentType, file);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(documentService, times(1)).uploadDocument(eq(candidateId), any(MockMultipartFile.class), eq(documentType));
    }

    @Test
    void testUploadDocument_IOError() throws Exception {
        when(documentService.uploadDocument(eq(candidateId), any(MockMultipartFile.class), eq(documentType)))
                .thenThrow(new IOException("IO error"));

        ResponseEntity<DocumentDto> response = documentController.uploadDocument(candidateId, documentType, file);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        verify(documentService, times(1)).uploadDocument(eq(candidateId), any(MockMultipartFile.class), eq(documentType));
    }

    @Test
    void testDownloadDocument_Success() {
        documentDto.setContent("test content".getBytes());

        when(documentService.downloadDocument(documentId)).thenReturn(documentDto);

        ResponseEntity<byte[]> response = documentController.downloadDocument(documentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(documentDto.getContent().length, response.getBody().length);
        verify(documentService, times(1)).downloadDocument(documentId);
    }

    @Test
    void testDownloadDocument_NotFound() {
        when(documentService.downloadDocument(documentId))
                .thenThrow(new RuntimeException("Document not found with ID: " + documentId));

        ResponseEntity<byte[]> response = documentController.downloadDocument(documentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(documentService, times(1)).downloadDocument(documentId);
    }
}