package com.sopheak.istadfinalems.model.dto.project;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.util.Set;

@Builder
public record ProjectUpdateDto(
        @NotBlank(message = "Project name is required")
        String projectName,
        @NotBlank(message = "Description is required")
        String description,
        @NotNull(message = "IsDeleted status must be specified")
        Boolean isDeleted,
        Set<String> employeeUuids
) {}
