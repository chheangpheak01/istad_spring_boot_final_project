package com.sopheak.istadfinalems.service;
import com.sopheak.istadfinalems.entities.Employee;
import com.sopheak.istadfinalems.entities.EmployeeStatus;
import com.sopheak.istadfinalems.exception.EmployeeNotFoundException;
import com.sopheak.istadfinalems.mapper.EmployeeMapStruct;
import com.sopheak.istadfinalems.model.dto.EmployeeCreateDto;
import com.sopheak.istadfinalems.model.dto.EmployeeResponseDto;
import com.sopheak.istadfinalems.model.dto.EmployeeUpdateDto;
import com.sopheak.istadfinalems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService{

    final EmployeeRepository employeeRepository;
    final EmployeeMapStruct employeeMapStruct;

    @Override
    public EmployeeResponseDto getEmployeeByUuid(String uuid) {
        Optional<Employee> employee = employeeRepository.findEmployeeByUuidAndIsDeletedIsFalse(uuid);
        if(employee.isEmpty()){
            throw new EmployeeNotFoundException("Employee not found");
        }
        return employeeMapStruct.mapFromEmployeeToEmployeeResponseDto(employee.get());
    }

    @Override
    public EmployeeResponseDto getEmployeeByName(String name) {
        Optional<Employee> employee = employeeRepository.findEmployeeByNameAndIsDeletedIsFalse(name);
        if(employee.isEmpty()){
            throw new EmployeeNotFoundException("Employee not found");
        }
        return employeeMapStruct.mapFromEmployeeToEmployeeResponseDto(employee.get());
    }

    @Override
    public EmployeeResponseDto getEmployeeByPhoneNumber(String phoneNumber) {
        Optional<Employee> employee = employeeRepository.findEmployeeByPhoneNumberAndIsDeletedIsFalse(phoneNumber);
        if(employee.isEmpty()){
            throw new EmployeeNotFoundException("Employee not found");
        }
        return employeeMapStruct.mapFromEmployeeToEmployeeResponseDto(employee.get());
    }

    @Override
    public Page<EmployeeResponseDto> getAllEmployeesByPagination(Pageable pageable) {
        Page<Employee> employeePage = employeeRepository.findAllEmployeeByIsDeletedIsFalse(pageable);
        return employeePage.map(employeeMapStruct::mapFromEmployeeToEmployeeResponseDto);
    }

    @Override
    public Page<EmployeeResponseDto> searchEmployeeByName(String name, Pageable pageable) {
        Page<Employee> employeePageSearchByName = employeeRepository.findEmployeeByNameContainingIgnoreCaseAndIsDeletedFalse(name, pageable);
        return employeePageSearchByName.map(employeeMapStruct::mapFromEmployeeToEmployeeResponseDto);
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDto> employeeResponseDto = new ArrayList<>();
        employees.forEach(e -> employeeResponseDto.add(employeeMapStruct.mapFromEmployeeToEmployeeResponseDto(e)));
        return employeeResponseDto;
    }

    @Override
    public EmployeeResponseDto createEmployee(EmployeeCreateDto employeeCreateDto) {

        Employee employee = new Employee();

        employee.setUuid(UUID.randomUUID().toString());
        employee.setName(employeeCreateDto.name());
        employee.setEmail(employeeCreateDto.email());
        employee.setPassword(employeeCreateDto.password());
        employee.setPhoneNumber(employeeCreateDto.phoneNumber());
        employee.setSalary(employeeCreateDto.salary());
        employee.setHireDate(employeeCreateDto.hireDate());
        employee.setIsDeleted(false);
        employee.setStatus(EmployeeStatus.ACTIVE);
        return null;
    }

    @Override
    public EmployeeResponseDto updateEmployeeByUuid(String uuid, EmployeeUpdateDto employeeUpdateDto) {
        return null;
    }

    @Override
    public void updateEmployeeStatus(String uuid, String status) {

    }

    @Override
    public String deleteEmployeeByUuid(String uuid) {
        return "";
    }


}
