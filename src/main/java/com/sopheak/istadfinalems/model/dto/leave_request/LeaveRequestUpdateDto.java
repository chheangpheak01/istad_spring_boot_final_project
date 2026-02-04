package com.sopheak.istadfinalems.model.dto.leave_request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public record LeaveRequestUpdateDto(
        @NotNull(message = "Start date is required for update")
        LocalDate startDate,

        @NotNull(message = "End date is required for update")
        LocalDate endDate,

        @NotBlank(message = "Reason can not be blank")
        String reason,

        @NotBlank(message = "Status is required (e.g., PENDING, APPROVED, REJECTED, CANCELLED)")
        String status,

        @NotNull(message = "IsDeleted status must be specified")
        Boolean isDeleted
) {}
