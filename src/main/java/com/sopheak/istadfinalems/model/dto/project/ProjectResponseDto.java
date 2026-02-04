package com.sopheak.istadfinalems.model.dto.project;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeSummaryDto;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record ProjectResponseDto(
        String uuid,
        String projectName,
        String description,
        LocalDateTime createdAt,
        Set<EmployeeSummaryDto> employees
) {}
