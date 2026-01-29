package com.sopheak.istadfinalems.model.dto;
import jakarta.validation.constraints.*;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record EmployeeCreateDto(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email format is invalid")
        String email,

        @NotBlank(message = "Password is required")
        @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Password must contain at least 8 characters, including uppercase, lowercase, number, and special character")
        String password,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @NotNull(message = "Salary is required")
        @Positive(message = "Salary must be greater than zero")
        BigDecimal salary,

        @NotNull(message = "Hire date is required")
        LocalDate hireDate,

        @NotBlank(message = "Department UUID is required")
        String departmentUuid,

        @NotBlank(message = "Position UUID is required")
        String positionUuid,

        @NotNull(message = "Address details are required")
        AddressCreateDto address
) {}