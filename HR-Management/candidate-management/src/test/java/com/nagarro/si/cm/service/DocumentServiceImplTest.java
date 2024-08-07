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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentServiceImplTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private DocumentMapper documentMapper;

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentServiceImpl documentService;

    private final int candidateId = 1;
    private final int documentId = 1;
    private final String documentType = "PDF";
    private MockMultipartFile file;
    private DocumentDto documentDto;
    private Document document;
    private Candidate candidate;

    @BeforeEach
    void setUp() throws SQLException {
        documentDto = new DocumentDto();
        documentDto.setId(documentId);
        documentDto.setType(DocumentType.PDF);
        documentDto.setName("test.pdf");
        documentDto.setContent("test content".getBytes());

        file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test content".getBytes());

        document = new Document();
        document.setId(documentId);
        document.setType(DocumentType.PDF);
        document.setName("test.pdf");
        document.setContent(new SerialBlob("test content".getBytes()));

        candidate = new Candidate();
        candidate.setId(candidateId);
        candidate.setDocuments(Collections.singletonList(document));
    }

    @Test
    void testUploadDocumentSuccess() throws IOException, SQLException {
        Candidate candidate = new Candidate();
        candidate.setId(candidateId);
        candidate.setDocuments(Collections.emptyList());

        Document newDocument = new Document();
        newDocument.setId(2);
        newDocument.setName("test.pdf");
        newDocument.setType(DocumentType.PDF);
        newDocument.setContent(new SerialBlob("test content".getBytes()));

        DocumentSummaryDto documentSummaryDto = new DocumentSummaryDto();
        documentSummaryDto.setId(newDocument.getId());
        documentSummaryDto.setName(newDocument.getName());
        documentSummaryDto.setType(newDocument.getType());

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(documentRepository.save(any(Document.class))).thenAnswer(invocation -> {
            Document doc = invocation.getArgument(0);
            doc.setId(2);
            return doc;
        });

        DocumentSummaryDto result = documentService.uploadDocument(candidateId, file, documentType);

        assertNotNull(result);
        assertEquals(2, result.getId());
        assertEquals("test.pdf", result.getName());
        assertEquals(DocumentType.PDF, result.getType());

        verify(candidateRepository, times(1)).findById(candidateId);
        verify(documentRepository, times(1)).save(any(Document.class));
    }

    @Test
    void testUploadDocumentDuplicate() {
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        Document existingDocument = new Document();
        existingDocument.setName("test.pdf");
        candidate.setDocuments(Collections.singletonList(existingDocument));

        assertThrows(EntityAlreadyExistsException.class, () ->
                documentService.uploadDocument(candidateId, file, documentType)
        );

        verify(candidateRepository, times(1)).findById(candidateId);
        verify(documentRepository, never()).save(any(Document.class));
    }

    @Test
    void testDownloadDocumentSuccess() {
        when(documentRepository.findById(documentId)).thenReturn(Optional.of(document));
        when(documentMapper.toDTO(any(Document.class))).thenReturn(documentDto);

        DocumentDto result = documentService.downloadDocument(documentId);

        assertNotNull(result);
        assertEquals(documentId, result.getId());
        assertEquals("test.pdf", result.getName());
        verify(documentRepository, times(1)).findById(documentId);
    }

    @Test
    void testDownloadDocumentNotFound() {
        when(documentRepository.findById(documentId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                documentService.downloadDocument(documentId)
        );

        verify(documentRepository, times(1)).findById(documentId);
    }

    @Test
    void testGetDocumentsByCandidateIdSuccess() {
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        List<DocumentSummaryDto> result = documentService.getDocumentsByCandidateId(candidateId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(document.getId(), result.get(0).getId());
        assertEquals(document.getName(), result.get(0).getName());
        verify(candidateRepository, times(1)).findById(candidateId);
    }

    @Test
    void testGetDocumentsByCandidateIdNotFound() {
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                documentService.getDocumentsByCandidateId(candidateId)
        );

        verify(candidateRepository, times(1)).findById(candidateId);
    }

    @Test
    void testDownloadDocumentByCandidateIdAndNameSuccess() {
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(documentMapper.toDTO(any(Document.class))).thenReturn(documentDto);

        DocumentDto result = documentService.downloadDocumentByCandidateIdAndName(candidateId, document.getName());

        assertNotNull(result);
        assertEquals(document.getId(), result.getId());
        assertEquals(document.getName(), result.getName());
        verify(candidateRepository, times(1)).findById(candidateId);
    }

    @Test
    void testDownloadDocumentByCandidateIdAndNameDocumentNotFound() {
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

        assertThrows(EntityNotFoundException.class, () ->
                documentService.downloadDocumentByCandidateIdAndName(candidateId, "nonexistent.pdf")
        );

        verify(candidateRepository, times(1)).findById(candidateId);
    }

    @Test
    void testDeleteDocumentByCandidateIdAndNameSuccess() {
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(documentRepository.findByCandidateIdAndName(candidateId, document.getName())).thenReturn(Optional.of(document));

        documentService.deleteDocumentByCandidateIdAndName(candidateId, document.getName());

        verify(candidateRepository, times(1)).findById(candidateId);
        verify(documentRepository, times(1)).findByCandidateIdAndName(candidateId, document.getName());
        verify(documentRepository, times(1)).delete(document);
    }

    @Test
    void testDeleteDocumentByCandidateIdAndNameDocumentNotFound() {
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(documentRepository.findByCandidateIdAndName(candidateId, "nonexistent.pdf")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () ->
                documentService.deleteDocumentByCandidateIdAndName(candidateId, "nonexistent.pdf")
        );

        verify(candidateRepository, times(1)).findById(candidateId);
        verify(documentRepository, times(1)).findByCandidateIdAndName(candidateId, "nonexistent.pdf");
        verify(documentRepository, never()).delete(any(Document.class));
    }
}