package com.nagarro.si.cm.mapper;

import com.nagarro.si.cm.dto.DocumentDto;
import com.nagarro.si.cm.entity.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentMapper {
    public DocumentDto toDTO(Document document) {
        DocumentDto documentDto = new DocumentDto();
        documentDto.setId(document.getId());
        documentDto.setType(document.getType());
        documentDto.setName(document.getName());
        documentDto.setContent(convertToByteArray(document.getContent()));
        documentDto.setCandidateId(document.getCandidate().getId());
        return documentDto;
    }

    private byte[] convertToByteArray(java.sql.Blob blob) {
        try {
            if (blob != null) {
                return blob.getBytes(1, (int) blob.length());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error converting Blob to byte array", e);
        }
        return null;
    }
}