package com.sopheak.istadfinalems.model.dto.employee_document;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeSummaryDto;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record EmployeeDocumentResponseDto(
        String uuid,
        String documentName,
        String documentType,
        String downloadUrl,
        LocalDateTime createdAt,
        EmployeeSummaryDto employee
) {}
