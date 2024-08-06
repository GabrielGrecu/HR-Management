package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.DocumentDto;
import com.nagarro.si.cm.entity.Candidate;
import com.nagarro.si.cm.entity.Document;
import com.nagarro.si.cm.entity.DocumentType;
import com.nagarro.si.cm.repository.CandidateRepository;
import com.nagarro.si.cm.repository.DocumentRepository;
import com.nagarro.si.cm.mapper.DocumentMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DocumentServiceImplTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private DocumentMapper documentMapper;

    @Mock
    private DocumentRepository documentRepository;

    @InjectMocks
    private DocumentServiceImpl documentServiceImpl;

    private final int candidateId = 1;
    private final String documentType = "PDF";
    private Candidate candidate;
    private final int documentId = 1;
    private Document document;
    private DocumentDto documentDto;
    private MockMultipartFile file;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        candidate = new Candidate();
        candidate.setId(candidateId);

        document = new Document();
        document.setId(documentId);
        document.setType(DocumentType.PDF);
        document.setName("test.pdf");
        document.setCandidate(candidate);

        documentDto = new DocumentDto();
        documentDto.setId(document.getId());
        documentDto.setType(document.getType());
        documentDto.setName(document.getName());
        documentDto.setCandidateId(candidateId);

        file = new MockMultipartFile("file", "test.pdf", "application/pdf", "test content".getBytes());
    }

    @Test
    void testUploadDocumentSuccess() throws Exception {
        document.setContent(new SerialBlob(file.getBytes()));
        documentDto.setContent(file.getBytes());

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));
        when(documentRepository.save(any(Document.class))).thenReturn(document);
        when(documentMapper.toDTO(any(Document.class))).thenReturn(documentDto);

        DocumentDto result = documentServiceImpl.uploadDocument(candidateId, file, documentType);

        assertNotNull(result);
        assertEquals(documentDto, result);

        verify(candidateRepository, times(1)).findById(candidateId);
        verify(documentRepository, times(1)).save(any(Document.class));
        verify(documentMapper, times(1)).toDTO(any(Document.class));
    }

    @Test
    void testUploadDocumentInvalidDocumentType() {
        Exception exception = assertThrows(RuntimeException.class, () -> documentServiceImpl.uploadDocument(candidateId, file, "INVALID"));

        String expectedMessage = "Invalid document type: INVALID";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        verify(candidateRepository, times(0)).findById(candidateId);
        verify(documentRepository, times(0)).save(any(Document.class));
        verify(documentMapper, times(0)).toDTO(any(Document.class));
    }

    @Test
    void testUploadDocumentCandidateNotFound() {
        when(candidateRepository.findById(candidateId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> documentServiceImpl.uploadDocument(candidateId, file, documentType));

        String expectedMessage = "Candidate not found with ID: " + candidateId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        verify(candidateRepository, times(1)).findById(candidateId);
        verify(documentRepository, times(0)).save(any(Document.class));
        verify(documentMapper, times(0)).toDTO(any(Document.class));
    }

    @Test
    void testDownloadDocumentSuccess() throws Exception {
        Blob blobContent = new SerialBlob("test content".getBytes());
        document.setContent(blobContent);
        documentDto.setContent(blobContent.getBytes(1, (int) blobContent.length()));

        when(documentRepository.findById(documentId)).thenReturn(Optional.of(document));
        when(documentMapper.toDTO(any(Document.class))).thenReturn(documentDto);

        DocumentDto result = documentServiceImpl.downloadDocument(documentId);

        assertNotNull(result);
        assertEquals(documentDto, result);

        verify(documentRepository, times(1)).findById(documentId);
        verify(documentMapper, times(1)).toDTO(any(Document.class));
    }

    @Test
    void testDownloadDocumentNotFound() {
        when(documentRepository.findById(documentId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> documentServiceImpl.downloadDocument(documentId));

        String expectedMessage = "Document not found with ID: " + documentId;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        verify(documentRepository, times(1)).findById(documentId);
        verify(documentMapper, times(0)).toDTO(any(Document.class));
    }
}