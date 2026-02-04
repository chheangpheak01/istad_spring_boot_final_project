package com.sopheak.istadfinalems.model.dto.employee_document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record EmployeeDocumentCreateDto(
        @NotBlank(message = "Document Name is required")
        String documentName,
        @NotBlank(message = "Document Type is required")
        String documentType,
        @NotBlank(message = "Download Url is required")
        String downloadUrl,
        @NotEmpty(message = "One employees UUID is required")
        String employeeUuids
) {}
