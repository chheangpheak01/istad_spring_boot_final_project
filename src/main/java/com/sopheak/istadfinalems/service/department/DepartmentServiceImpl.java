package com.sopheak.istadfinalems.service.department;
import com.sopheak.istadfinalems.entities.Department;
import com.sopheak.istadfinalems.entities.Employee;
import com.sopheak.istadfinalems.entities.emun.AuditAction;
import com.sopheak.istadfinalems.exception.DepartmentNotFoundException;
import com.sopheak.istadfinalems.mapper.DepartmentMapStruct;
import com.sopheak.istadfinalems.model.dto.department.DepartmentCreateDto;
import com.sopheak.istadfinalems.model.dto.department.DepartmentResponseDto;
import com.sopheak.istadfinalems.model.dto.department.DepartmentUpdateDto;
import com.sopheak.istadfinalems.repository.DepartmentRepository;
import com.sopheak.istadfinalems.repository.EmployeeRepository;
import com.sopheak.istadfinalems.service.audit.AuditService;
import jakarta.transaction.Transactional;
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
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapStruct departmentMapStruct;
    private final EmployeeRepository employeeRepository;
    private final AuditService auditService;

    @Override
    public DepartmentResponseDto getDepartmentByUuid(String uuid) {
        Optional<Department> department = departmentRepository.findByUuidAndIsDeletedFalse(uuid);
        if (department.isEmpty()) {
            throw new DepartmentNotFoundException("Department not found with this uuid: " + uuid);
        }
        return departmentMapStruct.mapFromDepartmentToDepartmentResponseDto(department.get());
    }

    @Override
    public DepartmentResponseDto getDepartmentByName(String name) {
        Optional<Department> department = departmentRepository.findByNameAndIsDeletedFalse(name);
        if (department.isEmpty()) {
            throw new DepartmentNotFoundException("Department not found with this name: " + name);
        }
        return departmentMapStruct.mapFromDepartmentToDepartmentResponseDto(department.get());
    }

    @Override
    public Page<DepartmentResponseDto> getAllDepartmentsByPagination(Pageable pageable) {
        Page<Department> departmentPage = departmentRepository.findAllByIsDeletedFalse(pageable);
        if (departmentPage.isEmpty()) {
            throw new DepartmentNotFoundException("No Departments found for this page");
        }
        return departmentPage.map(departmentMapStruct::mapFromDepartmentToDepartmentResponseDto);
    }

    @Override
    @Transactional
    public DepartmentResponseDto createDepartment(DepartmentCreateDto departmentCreateDto) {
        if (departmentRepository.existsByNameAndIsDeletedFalse(departmentCreateDto.name())) {
            throw new IllegalArgumentException("Name already exists: " + departmentCreateDto.name());
        }

        Department department = new Department();

        department.setUuid(UUID.randomUUID().toString());
        department.setName(departmentCreateDto.name());
        department.setDescription(departmentCreateDto.description());
        department.setIsDeleted(false);

        Department savedDepartment = departmentRepository.save(department);

        if (departmentCreateDto.employeeUuids() != null) {
            if (department.getEmployees() == null) {
                department.setEmployees(new ArrayList<>());
            }

            for (String empUuid : departmentCreateDto.employeeUuids()) {
                Employee employee = employeeRepository.findEmployeeByUuidAndIsDeletedIsFalse(empUuid)
                        .orElseThrow(() -> new RuntimeException("Employee not found with UUID: " + empUuid));
                employee.setDepartment(savedDepartment);
                savedDepartment.getEmployees().add(employee);
                employeeRepository.save(employee);
            }
        }
        auditService.log(
                "Department",
                savedDepartment.getId().toString(),
                AuditAction.CREATE,
                "Created department: " + savedDepartment.getName()
        );
        return departmentMapStruct.mapFromDepartmentToDepartmentResponseDto(savedDepartment);
    }

    @Override
    @Transactional
    public DepartmentResponseDto updateDepartmentByUuid(String uuid, DepartmentUpdateDto departmentUpdateDto) {
        Department department = departmentRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with UUID: " + uuid));

        department.setName(departmentUpdateDto.name());
        department.setDescription(departmentUpdateDto.description());
        department.setIsDeleted(departmentUpdateDto.isDeleted());

        if (departmentUpdateDto.employeeUuids() != null) {
            List<Employee> currentEmployees = employeeRepository.findAllByDepartment(department);
            for (Employee emp : currentEmployees) {
                emp.setDepartment(null);
                employeeRepository.save(emp);
            }
            department.getEmployees().clear();

            for (String empUuid : departmentUpdateDto.employeeUuids()) {
                Employee employee = employeeRepository.findEmployeeByUuidAndIsDeletedIsFalse(empUuid)
                        .orElseThrow(() -> new RuntimeException("Employee not found with UUID: " + empUuid));
                employee.setDepartment(department);
                department.getEmployees().add(employee);
                employeeRepository.save(employee);
            }
        }
        auditService.log(
                "Department",
                department.getId().toString(),
                AuditAction.UPDATE,
                "Updated department details for: " + department.getName()
        );
        return departmentMapStruct.mapFromDepartmentToDepartmentResponseDto(departmentRepository.save(department));
    }

    @Override
    public String deleteDepartmentByUuid(String uuid) {
        Department department = departmentRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with UUID: " + uuid));
        department.setIsDeleted(true);
        departmentRepository.save(department);
        auditService.log(
                "Department",
                department.getId().toString(),
                AuditAction.DELETE,
                "Soft deleted department: " + department.getName()
        );
        return "Department with UUID " + uuid + " has been deleted successfully";
    }
}
