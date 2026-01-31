package com.sopheak.istadfinalems.mapper;
import com.sopheak.istadfinalems.entities.Department;
import com.sopheak.istadfinalems.entities.Employee;
import com.sopheak.istadfinalems.model.dto.department.DepartmentResponseDto;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapStruct {
    DepartmentResponseDto mapFromDepartmentToDepartmentResponseDto(Department department);
    @Mapping(source = "jobPosition.title", target = "positionName")
    EmployeeSummaryDto mapFromEmployeeToEmployeeSummaryDto(Employee employee);
}
