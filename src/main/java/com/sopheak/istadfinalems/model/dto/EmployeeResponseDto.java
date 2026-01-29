package com.sopheak.istadfinalems.model.dto;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
public record EmployeeResponseDto(
        String uuid,
        String name,
        String phoneNumber,
        String positionName,
        String departmentName,
        String status,
        LocalDate hireDate,
        BigDecimal salary,
        String city,
        List<String> documentUrls,
        Set<String> projectNames
){}
