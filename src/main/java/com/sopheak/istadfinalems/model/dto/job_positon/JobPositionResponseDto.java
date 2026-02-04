package com.sopheak.istadfinalems.model.dto.job_positon;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeSummaryDto;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record JobPositionResponseDto(
        String uuid,
        String title,
        BigDecimal minSalary,
        BigDecimal maxSalary,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Set<EmployeeSummaryDto> employees
) {}
