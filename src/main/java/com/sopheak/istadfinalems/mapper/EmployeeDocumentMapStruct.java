package com.sopheak.istadfinalems.mapper;
import com.sopheak.istadfinalems.entities.EmployeeDocument;
import com.sopheak.istadfinalems.model.dto.employee_document.EmployeeDocumentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeDocumentMapStruct {
    @Mapping(target = "employee.positionName", source = "employee.jobPosition.title")
    EmployeeDocumentResponseDto mapEmployeeDocumentToEmployeeDocumentResponseDto(EmployeeDocument employeeDocument);
}
