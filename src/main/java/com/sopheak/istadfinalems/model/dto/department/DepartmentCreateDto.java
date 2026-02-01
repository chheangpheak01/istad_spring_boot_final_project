package com.sopheak.istadfinalems.model.dto.department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import java.util.Set;

@Builder
public record DepartmentCreateDto(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Description is required")
        String description,
        @NotEmpty(message = "At least one employees UUID is required")
        Set<String> employeeUuids
) {}
