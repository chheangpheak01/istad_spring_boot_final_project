package com.sopheak.istadfinalems.model.dto.department;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record DepartmentCreateDto(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Description is required")
        String description
) {}
