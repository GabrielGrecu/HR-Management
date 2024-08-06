package com.nagarro.si.cm.service;

import com.nagarro.si.cm.dto.DocumentDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DocumentService {
    DocumentDto uploadDocument(int candidateId,
                               MultipartFile file,
                               String documentType) throws IOException;

    DocumentDto downloadDocument(int documentId);
}