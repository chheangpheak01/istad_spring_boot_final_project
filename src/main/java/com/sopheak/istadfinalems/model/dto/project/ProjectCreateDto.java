package com.sopheak.istadfinalems.model.dto.project;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import java.util.Set;

@Builder
public record ProjectCreateDto(
        @NotBlank(message = "Project name is required")
        String projectName,
        @NotBlank(message = "Description is required")
        String description,
        Set<String> employeeUuids
) {}
