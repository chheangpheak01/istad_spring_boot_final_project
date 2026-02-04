package com.sopheak.istadfinalems.model.dto.employee_document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record EmployeeDocumentUpdateDto(
        @NotBlank(message = "Document name can not be empty")
        String documentName,
        @NotBlank(message = "Document type can not be empty")
        String documentType,
        @NotNull(message = "IsDeleted status must be specified")
        Boolean isDeleted
){}
