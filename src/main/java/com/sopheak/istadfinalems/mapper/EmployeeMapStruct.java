package com.sopheak.istadfinalems.mapper;
import com.sopheak.istadfinalems.entities.Employee;
import com.sopheak.istadfinalems.entities.EmployeeDocument;
import com.sopheak.istadfinalems.model.dto.EmployeeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface EmployeeMapStruct {
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "jobPosition.title", target = "positionName")
    @Mapping(source = "address.city", target = "city")
    @Mapping(target = "projectNames", expression = "java(employee.getProjects() != null ? " +
            "employee.getProjects().stream().map(p -> " +
            "p.getProjectName()).collect(java.util.stream.Collectors.toSet()) : null)")
    @Mapping(target = "documentUrls", expression = "java(mapDocumentsToUrls(employee.getDocuments()))")

    EmployeeResponseDto mapFromEmployeeToEmployeeResponseDto(Employee employee);

    default List<String> mapDocumentsToUrls(List<EmployeeDocument> documents) {
        if (documents == null || documents.isEmpty()) {
            return null;
        }
        return documents.stream()
                .map(EmployeeDocument::getDownloadUrl)
                .collect(Collectors.toList());
    }
}

