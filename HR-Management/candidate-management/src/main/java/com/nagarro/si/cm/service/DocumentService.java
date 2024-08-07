package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.DocumentDto;
import com.nagarro.si.cm.dto.DocumentSummaryDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DocumentService {
    DocumentSummaryDto uploadDocument(int candidateId,
                               MultipartFile file,
                               String documentType) throws IOException, SQLException;


    DocumentDto downloadDocument(int documentId);

    DocumentDto downloadDocumentByCandidateIdAndName(int candidateId, String documentName);

    List<DocumentSummaryDto> getDocumentsByCandidateId(int candidateId);

    void deleteDocumentByCandidateIdAndName(int candidateId, String documentName);
}