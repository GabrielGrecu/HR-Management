package com.nagarro.si.cm.dto;

import com.nagarro.si.cm.entity.DocumentType;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class DocumentDto {

    private int id;

    @NotNull(message = "Document type is required")
    private DocumentType type;

    @NotNull(message = "Document name is required")
    private String name;

    @NotNull(message = "Document content is required")
    private byte[] content;

    private int candidateId;
}