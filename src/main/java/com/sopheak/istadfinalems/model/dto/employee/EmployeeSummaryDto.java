package com.sopheak.istadfinalems.model.dto.employee;
import lombok.Builder;

@Builder
public record EmployeeSummaryDto(
        String uuid,
        String name,
        String positionName,
        String status,
        String profileImage
) {}
