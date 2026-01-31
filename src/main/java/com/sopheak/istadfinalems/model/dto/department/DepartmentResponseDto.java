package com.sopheak.istadfinalems.model.dto.department;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeSummaryDto;
import lombok.Builder;
import java.util.Set;

@Builder
public record DepartmentResponseDto(
        String uuid,
        String name,
        String description,
        Set<EmployeeSummaryDto> employees
) {}
