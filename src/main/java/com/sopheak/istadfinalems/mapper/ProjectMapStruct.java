package com.sopheak.istadfinalems.mapper;
import com.sopheak.istadfinalems.entities.Employee;
import com.sopheak.istadfinalems.entities.Project;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeSummaryDto;
import com.sopheak.istadfinalems.model.dto.project.ProjectResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProjectMapStruct {
    ProjectResponseDto mapFromProjectToProjectResponseDto(Project project);
    @Mapping(source = "jobPosition.title", target = "positionName")
    EmployeeSummaryDto mapFromEmployeeToEmployeeSummaryDto(Employee employee);
}
