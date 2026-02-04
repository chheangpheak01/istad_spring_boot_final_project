package com.sopheak.istadfinalems.model.dto.leave_request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public record LeaveRequestCreateDto(
        @NotNull(message = "Start date is required")
        LocalDate startDate,

        @NotNull(message = "End date is required")
        LocalDate endDate,

        @NotBlank(message = "Reason for leave is required")
        String reason,

        @NotBlank(message = "Employee UUID is required")
        String employeeUuid
) {}
