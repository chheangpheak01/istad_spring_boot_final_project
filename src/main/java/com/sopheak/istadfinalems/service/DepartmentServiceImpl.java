package com.sopheak.istadfinalems.service;
import com.sopheak.istadfinalems.entities.Department;
import com.sopheak.istadfinalems.exception.DepartmentNotFoundException;
import com.sopheak.istadfinalems.mapper.DepartmentMapStruct;
import com.sopheak.istadfinalems.model.dto.department.DepartmentCreateDto;
import com.sopheak.istadfinalems.model.dto.department.DepartmentResponseDto;
import com.sopheak.istadfinalems.model.dto.department.DepartmentUpdateDto;
import com.sopheak.istadfinalems.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService{

    private final DepartmentRepository departmentRepository;
    private final DepartmentMapStruct departmentMapStruct;

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
        return null;
    }

    @Override
    public Page<DepartmentResponseDto> getAllDepartmentsByPagination(Pageable pageable) {
        return null;
    }

    @Override
    public DepartmentResponseDto createDepartment(DepartmentCreateDto departmentCreateDto) {
        return null;
    }

    @Override
    public DepartmentResponseDto updateDepartmentByUuid(String uuid, DepartmentUpdateDto departmentUpdateDto) {
        return null;
    }

    @Override
    public String deleteDepartmentByUuid(String uuid) {
        return "";
    }
}
