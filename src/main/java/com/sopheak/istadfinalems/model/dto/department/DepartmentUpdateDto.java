package com.sopheak.istadfinalems.model.dto.department;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record DepartmentUpdateDto(
        @NotBlank(message = "Name is required if updating")
        String name,
        @NotBlank(message = "Description is required if updating")
        String description
) {}
