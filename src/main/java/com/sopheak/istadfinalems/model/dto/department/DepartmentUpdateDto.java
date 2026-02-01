package com.sopheak.istadfinalems.model.dto.department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.util.Set;

@Builder
public record DepartmentUpdateDto(
        @NotBlank(message = "Name is required if updating")
        String name,
        @NotBlank(message = "Description is required if updating")
        String description,
        @NotNull(message = "IsDeleted status must be specified")
        Boolean isDeleted,
        @NotEmpty(message = "At least one employees UUID is required")
        Set<String> employeeUuids
) {}
