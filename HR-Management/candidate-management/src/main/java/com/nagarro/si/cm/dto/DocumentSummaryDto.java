package com.nagarro.si.cm.dto;

import com.nagarro.si.cm.entity.DocumentType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentSummaryDto {

    private int id;

    @NotNull(message = "Document type is required")
    private DocumentType type;

    @NotNull(message = "Document name is required")
    private String name;
}
