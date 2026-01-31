package com.sopheak.istadfinalems.service;
import com.sopheak.istadfinalems.model.dto.department.DepartmentCreateDto;
import com.sopheak.istadfinalems.model.dto.department.DepartmentResponseDto;
import com.sopheak.istadfinalems.model.dto.department.DepartmentUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface DepartmentService {
    DepartmentResponseDto getDepartmentByUuid(String uuid);
    DepartmentResponseDto getDepartmentByName(String name);
    Page<DepartmentResponseDto> getAllDepartmentsByPagination(Pageable pageable);
    DepartmentResponseDto createDepartment(DepartmentCreateDto departmentCreateDto);
    DepartmentResponseDto updateDepartmentByUuid(String uuid, DepartmentUpdateDto departmentUpdateDto);
    String deleteDepartmentByUuid(String uuid);
}
