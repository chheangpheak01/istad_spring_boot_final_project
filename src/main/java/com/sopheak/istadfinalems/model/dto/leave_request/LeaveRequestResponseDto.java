package com.sopheak.istadfinalems.model.dto.leave_request;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeSummaryDto;
import lombok.Builder;
import java.time.LocalDate;

@Builder
public record LeaveRequestResponseDto(
       String uuid,
       LocalDate startDate,
       LocalDate endDate,
       String reason,
       String status,
       LocalDate submittedAt,
       EmployeeSummaryDto employee
) {}
