package com.sopheak.istadfinalems.model.dto.job_positon;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.math.BigDecimal;
import java.util.Set;

@Builder
public record JobPositionUpdateDto(
        @NotBlank(message = "Title is required")
        String title,
        @NotNull(message = "Minimum salary is required")
        @DecimalMin(value = "0.0", message = "Salary can not be negative")
        BigDecimal minSalary,
        @NotNull(message = "Maximum salary is required")
        @DecimalMin(value = "0.0", message = "Salary can not be negative")
        BigDecimal maxSalary,
        @NotNull(message = "Status is required")
        Boolean isDeleted,
        Set<String> employeeUuids
) {}
