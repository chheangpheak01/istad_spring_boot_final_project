package com.sopheak.istadfinalems.service;
import com.sopheak.istadfinalems.model.dto.EmployeeCreateDto;
import com.sopheak.istadfinalems.model.dto.EmployeeResponseDto;
import com.sopheak.istadfinalems.model.dto.EmployeeUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface EmployeeService {
    EmployeeResponseDto getEmployeeByUuid(String uuid);
    EmployeeResponseDto getEmployeeByName(String name);
    EmployeeResponseDto getEmployeeByPhoneNumber(String phoneNumber);
    Page<EmployeeResponseDto> getAllEmployeesByPagination(Pageable pageable);
    Page<EmployeeResponseDto> searchEmployeeByName(String name, Pageable pageable);
    List<EmployeeResponseDto> getAllEmployees();
    EmployeeResponseDto createEmployee(EmployeeCreateDto employeeCreateDto);
    EmployeeResponseDto updateEmployeeByUuid(String uuid, EmployeeUpdateDto employeeUpdateDto);
    void updateEmployeeStatus(String uuid, String status);
    String deleteEmployeeByUuid(String uuid);
}
