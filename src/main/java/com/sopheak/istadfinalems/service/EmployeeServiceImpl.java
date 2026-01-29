package com.sopheak.istadfinalems.service;
import com.sopheak.istadfinalems.entities.*;
import com.sopheak.istadfinalems.exception.DepartmentNotFoundException;
import com.sopheak.istadfinalems.exception.EmployeeNotFoundException;
import com.sopheak.istadfinalems.exception.JobPositionNotFoundException;
import com.sopheak.istadfinalems.mapper.EmployeeMapStruct;
import com.sopheak.istadfinalems.model.dto.EmployeeCreateDto;
import com.sopheak.istadfinalems.model.dto.EmployeeResponseDto;
import com.sopheak.istadfinalems.model.dto.EmployeeUpdateDto;
import com.sopheak.istadfinalems.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    final EmployeeRepository employeeRepository;
    final EmployeeMapStruct employeeMapStruct;
    final DepartmentRepository departmentRepository;
    final JobPositionRepository jobPositionRepository;
    final ProjectRepository projectRepository;
    final EmployeeDocumentRepository employeeDocumentRepository;

    @Override
    public EmployeeResponseDto getEmployeeByUuid(String uuid) {
        Optional<Employee> employee = employeeRepository.findEmployeeByUuidAndIsDeletedIsFalse(uuid);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        return employeeMapStruct.mapFromEmployeeToEmployeeResponseDto(employee.get());
    }

    @Override
    public EmployeeResponseDto getEmployeeByName(String name) {
        Optional<Employee> employee = employeeRepository.findEmployeeByNameAndIsDeletedIsFalse(name);
        if (employee.isEmpty()) {
            throw new EmployeeNotFoundException("Employee not found");
        }
        return employeeMapStruct.mapFromEmployeeToEmployeeResponseDto(employee.get());
    }

    @Override
    public EmployeeResponseDto getEmployeeByPhoneNumber(String phoneNumber) {
        Optional<Employee> employee = employeeRepository.findEmployeeByPhoneNumberAndIsDeletedIsFalse(phoneNumber);
        if (employee.isEmpty()) {
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

        if (employeeCreateDto.departmentUuid() != null) {
            Department department = departmentRepository.findByUuid(employeeCreateDto.departmentUuid())
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found with UUID: " + employeeCreateDto.departmentUuid()));
            employee.setDepartment(department);
        }

        if (employeeCreateDto.positionUuid() != null) {
            JobPosition jobPosition = jobPositionRepository.findByUuid(employeeCreateDto.positionUuid())
                    .orElseThrow(() -> new JobPositionNotFoundException("JobPosition not found with UUID: " + employeeCreateDto.positionUuid()));
            employee.setJobPosition(jobPosition);
        }

        if(employeeCreateDto.address() != null){

            Address address = new Address();

            address.setUuid(UUID.randomUUID().toString());
            address.setStreet(employeeCreateDto.address().street());
            address.setCity(employeeCreateDto.address().city());
            address.setProvince(employeeCreateDto.address().province());
            address.setIsDeleted(false);
            employee.setAddress(address);
        }
        if (employeeCreateDto.documentUuids() != null && !employeeCreateDto.documentUuids().isEmpty()) {
            List<EmployeeDocument> documents = employeeDocumentRepository.findAllByUuidIn(
                    new ArrayList<>(employeeCreateDto.documentUuids())
            );
            employee.setDocuments(documents);
        }

        if(employeeCreateDto.projectUuids() != null && !employeeCreateDto.projectUuids().isEmpty()){
            List<Project> projects = projectRepository.
                    findAllByUuidIn(new ArrayList<>(employeeCreateDto.projectUuids()));
            employee.setProjects(new HashSet<>(projects));
        }
        employeeRepository.save(employee);
        return employeeMapStruct.mapFromEmployeeToEmployeeResponseDto(employee);
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
