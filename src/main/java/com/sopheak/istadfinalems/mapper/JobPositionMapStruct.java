package com.sopheak.istadfinalems.mapper;
import com.sopheak.istadfinalems.entities.Employee;
import com.sopheak.istadfinalems.entities.JobPosition;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeSummaryDto;
import com.sopheak.istadfinalems.model.dto.job_positon.JobPositionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface JobPositionMapStruct {
    JobPositionResponseDto mapFromJobPositionToJobPositionResponseDto(JobPosition jobPosition);
    @Mapping(source = "jobPosition.title", target = "positionName")
    EmployeeSummaryDto mapFromEmployeeToEmployeeSummaryDto(Employee employee);
}
