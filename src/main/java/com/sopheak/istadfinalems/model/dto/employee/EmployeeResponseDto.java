package com.sopheak.istadfinalems.model.dto.employee;
import com.sopheak.istadfinalems.model.dto.address.AddressResponseDto;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Builder
public record EmployeeResponseDto(
        String uuid,
        String employeeId,
        String name,
        String email,
        String phoneNumber,
        String profileImage,
        LocalDate dob,
        String gender,
        String biography,
        String positionName,
        String departmentName,
        String status,
        LocalDate hireDate,
        BigDecimal salary,
        AddressResponseDto address,
        List<String> documentUrls,
        Set<String> projectNames
){}
