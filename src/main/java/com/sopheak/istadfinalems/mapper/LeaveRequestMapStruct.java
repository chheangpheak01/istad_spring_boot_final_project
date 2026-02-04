package com.sopheak.istadfinalems.mapper;
import com.sopheak.istadfinalems.entities.Employee;
import com.sopheak.istadfinalems.entities.LeaveRequest;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeSummaryDto;
import com.sopheak.istadfinalems.model.dto.leave_request.LeaveRequestResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeaveRequestMapStruct {
    LeaveRequestResponseDto mapLeaveRequestToLeaveRequestResponseDto(LeaveRequest leaveRequest);
    @Mapping(source = "jobPosition.title", target = "positionName")
    EmployeeSummaryDto mapFromEmployeeToEmployeeSummaryDto(Employee employee);
}
