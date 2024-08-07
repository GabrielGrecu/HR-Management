package com.nagarro.si.cm.controller;

import com.nagarro.si.cm.dto.DocumentDto;
import com.nagarro.si.cm.dto.DocumentSummaryDto;
import com.nagarro.si.cm.entity.DocumentType;
import com.nagarro.si.cm.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
        documentDto.setContent("test content".getBytes());

        file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test content".getBytes());
    }

    @Test
    void testUploadDocumentSuccess() throws IOException, SQLException {
        when(documentService.uploadDocument(eq(candidateId), any(MockMultipartFile.class), eq(documentType)))
                .thenReturn(documentDto);

        ResponseEntity<DocumentDto> response = documentController.uploadDocument(candidateId, documentType, file);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(documentDto.getId(), response.getBody().getId());
        assertEquals(documentDto.getName(), response.getBody().getName());
        verify(documentService, times(1)).uploadDocument(eq(candidateId), any(MockMultipartFile.class), eq(documentType));
    }

    @Test
    void testDownloadDocumentSuccess() {
        when(documentService.downloadDocument(documentId)).thenReturn(documentDto);

        ResponseEntity<byte[]> response = documentController.downloadDocument(documentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(new String(documentDto.getContent()), new String(response.getBody()));
        verify(documentService, times(1)).downloadDocument(documentId);
    }

    @Test
    void testGetDocumentsByCandidateIdSuccess() {
        DocumentSummaryDto summaryDto = new DocumentSummaryDto(documentId, DocumentType.PDF, "test.pdf");
        List<DocumentSummaryDto> documentSummaries = Collections.singletonList(summaryDto);

        when(documentService.getDocumentsByCandidateId(candidateId)).thenReturn(documentSummaries);

        ResponseEntity<List<DocumentSummaryDto>> response = documentController.getDocumentsByCandidateId(candidateId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(summaryDto, response.getBody().get(0));
        verify(documentService, times(1)).getDocumentsByCandidateId(candidateId);
    }

    @Test
    void testDeleteDocumentSuccess() {
        doNothing().when(documentService).deleteDocumentByCandidateIdAndName(candidateId, documentDto.getName());

        ResponseEntity<String> response = documentController.deleteDocument(candidateId, documentDto.getName());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Document deleted successfully.", response.getBody());
        verify(documentService, times(1)).deleteDocumentByCandidateIdAndName(candidateId, documentDto.getName());
    }
}