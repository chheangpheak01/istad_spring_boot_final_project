package com.sopheak.istadfinalems.model.dto;
import jakarta.validation.constraints.*;
import lombok.Builder;
import java.math.BigDecimal;

@Builder
public record EmployeeUpdateDto(

        @NotBlank(message = "Name is required if updating")
        String name,

        @Email(message = "Email format is invalid")
        String email,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @Positive(message = "Salary must be greater than zero")
        BigDecimal salary,

        @NotBlank(message = "Department UUID is required")
        String departmentUuid,

        @NotBlank(message = "Position UUID is required")
        String positionUuid,
        AddressUpdateDto address
) {}