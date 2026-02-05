package com.sopheak.istadfinalems.service.impl;
import com.sopheak.istadfinalems.entities.*;
import com.sopheak.istadfinalems.entities.emun.AuditAction;
import com.sopheak.istadfinalems.entities.emun.EmployeeStatus;
import com.sopheak.istadfinalems.entities.emun.GenderStatus;
import com.sopheak.istadfinalems.exception.DepartmentNotFoundException;
import com.sopheak.istadfinalems.exception.EmployeeNotFoundException;
import com.sopheak.istadfinalems.exception.JobPositionNotFoundException;
import com.sopheak.istadfinalems.mapper.EmployeeMapStruct;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeCreateDto;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeResponseDto;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeUpdateDto;
import com.sopheak.istadfinalems.repository.*;
import com.sopheak.istadfinalems.service.AuditService;
import com.sopheak.istadfinalems.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    final private EmployeeRepository employeeRepository;
    final private EmployeeMapStruct employeeMapStruct;
    final private DepartmentRepository departmentRepository;
    final private JobPositionRepository jobPositionRepository;
    final private ProjectRepository projectRepository;
    final private EmployeeDocumentRepository employeeDocumentRepository;
    final private AuditService auditService;

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
        if (employeePage.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found for this page");
        }
        return employeePage.map(employeeMapStruct::mapFromEmployeeToEmployeeResponseDto);
    }

    @Override
    public Page<EmployeeResponseDto> searchEmployeeByName(String name, Pageable pageable) {
        Page<Employee> employeePageSearchByName = employeeRepository.findEmployeeByNameContainingIgnoreCaseAndIsDeletedFalse(name, pageable);
        if (employeePageSearchByName.isEmpty()) {
            throw new EmployeeNotFoundException("No employee found with this name: " + name);
        }
        return employeePageSearchByName.map(employeeMapStruct::mapFromEmployeeToEmployeeResponseDto);
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        if (employees.isEmpty()) {
            throw new EmployeeNotFoundException("No employees found in the system");
        }
        List<EmployeeResponseDto> employeeResponseDto = new ArrayList<>();
        employees.forEach(e -> employeeResponseDto.add(employeeMapStruct.mapFromEmployeeToEmployeeResponseDto(e)));
        return employeeResponseDto;
    }

    @Override
    public EmployeeResponseDto createEmployee(EmployeeCreateDto employeeCreateDto) {

        if (employeeRepository.existsEmployeesByEmail(employeeCreateDto.email())) {
            throw new IllegalArgumentException("Email already exists: " + employeeCreateDto.email());
        }
        if (employeeRepository.existsEmployeesByPhoneNumber(employeeCreateDto.phoneNumber())) {
            throw new IllegalArgumentException("Phone number already exists: " + employeeCreateDto.phoneNumber());
        }

        Employee employee = new Employee();

        employee.setUuid(UUID.randomUUID().toString());
        String generatedId = "EMP-" + java.time.Year.now().getValue() + "-" + (int)(Math.random() * 9000 + 1000);
        employee.setEmployeeId(generatedId);
        employee.setName(employeeCreateDto.name());
        employee.setEmail(employeeCreateDto.email());
        employee.setPassword(employeeCreateDto.password());
        employee.setPhoneNumber(employeeCreateDto.phoneNumber());
        employee.setProfileImage(employeeCreateDto.profileImage());
        employee.setDob(employeeCreateDto.dob());
        try {
            employee.setGender(GenderStatus.valueOf(employeeCreateDto.gender().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid gender. Use: MALE, FEMALE, or OTHER");
        }
        employee.setBiography(employeeCreateDto.biography());
        employee.setSalary(employeeCreateDto.salary());
        employee.setHireDate(employeeCreateDto.hireDate());
        employee.setIsDeleted(false);
        employee.setStatus(EmployeeStatus.ACTIVE);

        if (employeeCreateDto.departmentUuid() != null) {
            Department department = departmentRepository.findByUuidAndIsDeletedFalse(employeeCreateDto.departmentUuid())
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found with UUID: " + employeeCreateDto.departmentUuid()));
            employee.setDepartment(department);
        }

        if (employeeCreateDto.positionUuid() != null) {
            JobPosition jobPosition = jobPositionRepository.findByUuidAndIsDeletedFalse(employeeCreateDto.positionUuid())
                    .orElseThrow(() -> new JobPositionNotFoundException("JobPosition not found with UUID: " + employeeCreateDto.positionUuid()));
            employee.setJobPosition(jobPosition);
        }

        if(employeeCreateDto.address() != null){

            Address address = new Address();

            address.setUuid(UUID.randomUUID().toString());
            address.setStreet(employeeCreateDto.address().street());
            address.setCity(employeeCreateDto.address().city());
            address.setProvince(employeeCreateDto.address().province());
            address.setCountry(employeeCreateDto.address().country());
            address.setIsDeleted(false);
            employee.setAddress(address);
        }
        if (employeeCreateDto.documentUuids() != null && !employeeCreateDto.documentUuids().isEmpty()) {
            List<EmployeeDocument> documents = employeeDocumentRepository.findAllByUuidIn(
                    new ArrayList<>(employeeCreateDto.documentUuids())
            );
            if (documents.size() != employeeCreateDto.documentUuids().size()) {
                throw new IllegalArgumentException("One or more Document UUIDs are invalid.");
            }
            employee.setDocuments(documents);
        }

        if(employeeCreateDto.projectUuids() != null && !employeeCreateDto.projectUuids().isEmpty()){
            List<Project> projects = projectRepository.
                    findAllByUuidIn(new ArrayList<>(employeeCreateDto.projectUuids()));
            if (projects.size() != employeeCreateDto.projectUuids().size()) {
                throw new IllegalArgumentException("One or more Project UUIDs are invalid.");
            }
            employee.setProjects(new HashSet<>(projects));
        }
        employeeRepository.save(employee);
        auditService.log(
                "Employee",
                employee.getId().toString(),
                AuditAction.CREATE,
                "Created employee: " + employee.getName() + " with ID: " + employee.getEmployeeId()
        );
        return employeeMapStruct.mapFromEmployeeToEmployeeResponseDto(employee);
    }

    @Override
    public EmployeeResponseDto updateEmployeeByUuid(String uuid, EmployeeUpdateDto employeeUpdateDto) {
        Optional<Employee> employee = employeeRepository.findEmployeeByUuidAndIsDeletedIsFalse(uuid);
        if(employee.isEmpty()){
            throw new EmployeeNotFoundException("Employee not found with UUID: " + uuid);
        }

        employee.get().setName(employeeUpdateDto.name());
        employee.get().setEmail(employeeUpdateDto.email());
        employee.get().setPassword(employeeUpdateDto.password());
        employee.get().setPhoneNumber(employeeUpdateDto.phoneNumber());
        employee.get().setProfileImage(employeeUpdateDto.profileImage());
        employee.get().setDob(employeeUpdateDto.dob());
        try {
            employee.get().setGender(GenderStatus.valueOf(employeeUpdateDto.gender().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid gender. Use: MALE, FEMALE, or OTHER");
        }
        employee.get().setBiography(employeeUpdateDto.biography());
        employee.get().setSalary(employeeUpdateDto.salary());
        employee.get().setHireDate(employeeUpdateDto.hireDate());
        employee.get().setIsDeleted(employeeUpdateDto.isDeleted());

        try {
            employee.get().setStatus(EmployeeStatus.valueOf(employeeUpdateDto.status().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + employeeUpdateDto.status() +
                    " Allowed values are: " + Arrays.toString(EmployeeStatus.values()));
        }

        if (employeeUpdateDto.departmentUuid() != null) {
            Department department = departmentRepository.findByUuidAndIsDeletedFalse(employeeUpdateDto.departmentUuid())
                    .orElseThrow(() -> new DepartmentNotFoundException("Department not found with UUID: " + employeeUpdateDto.departmentUuid()));
            employee.get().setDepartment(department);
        }

        if (employeeUpdateDto.positionUuid() != null) {
            JobPosition jobPosition = jobPositionRepository.findByUuidAndIsDeletedFalse(employeeUpdateDto.positionUuid())
                    .orElseThrow(() -> new JobPositionNotFoundException("JobPosition not found with UUID: " + employeeUpdateDto.positionUuid()));
            employee.get().setJobPosition(jobPosition);
        }

        if (employeeUpdateDto.address() != null) {

            Address address = employee.get().getAddress();

            if (address == null) {

                address = new Address();

                address.setUuid(UUID.randomUUID().toString());
                address.setStreet(employeeUpdateDto.address().street());
                address.setCity(employeeUpdateDto.address().city());
                address.setProvince(employeeUpdateDto.address().province());
                address.setCountry(employeeUpdateDto.address().country());
                address.setIsDeleted(false);
                employee.get().setAddress(address);
            }
            address.setStreet(employeeUpdateDto.address().street());
            address.setCity(employeeUpdateDto.address().city());
            address.setProvince(employeeUpdateDto.address().province());
            address.setCountry(employeeUpdateDto.address().country());
            employee.get().setAddress(address);
        }

        if (employeeUpdateDto.documentUuids() != null) {
            List<EmployeeDocument> documents = employeeDocumentRepository.findAllByUuidIn(
                    new ArrayList<>(employeeUpdateDto.documentUuids())
            );
            if (documents.size() != employeeUpdateDto.documentUuids().size()) {
                throw new IllegalArgumentException("One or more Document UUIDs are invalid.");
            }
            employee.get().setDocuments(documents);
        }

        if (employeeUpdateDto.projectUuids() != null) {
            List<Project> projects = projectRepository.findAllByUuidIn(
                    new ArrayList<>(employeeUpdateDto.projectUuids())
            );
            if (projects.size() != employeeUpdateDto.projectUuids().size()) {
                throw new IllegalArgumentException("One or more Project UUIDs are invalid.");
            }
            employee.get().setProjects(new HashSet<>(projects));
        }
        employeeRepository.save(employee.get());
        auditService.log(
                "Employee",
                employee.get().getId().toString(),
                AuditAction.UPDATE,
                "Updated profile/status for employee: " + employee.get().getName()
        );
        return employeeMapStruct.mapFromEmployeeToEmployeeResponseDto(employee.get());
    }

    @Override
    public EmployeeResponseDto updateEmployeeStatus(String uuid, String status) {
        Employee employee = employeeRepository.findEmployeeByUuidAndIsDeletedIsFalse(uuid)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with UUID: " + uuid));
        try {
            employee.setStatus(EmployeeStatus.valueOf(status.toUpperCase()));
            employeeRepository.save(employee);
            return employeeMapStruct.mapFromEmployeeToEmployeeResponseDto(employee);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status +
                    " Allowed values are: " + Arrays.toString(EmployeeStatus.values()));
        }
    }

    @Override
    public String deleteEmployeeByUuid(String uuid) {
        Employee employee = employeeRepository.findEmployeeByUuidAndIsDeletedIsFalse(uuid)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with UUID: " + uuid));
        employee.setIsDeleted(true);
        employee.setStatus(EmployeeStatus.TERMINATED);
        employeeRepository.save(employee);
        auditService.log(
                "Employee",
                employee.getId().toString(),
                AuditAction.DELETE,
                "Terminated and soft-deleted employee: " + employee.getName()
        );
        return "Employee with UUID " + uuid + " has been deleted successfully";
    }
}
