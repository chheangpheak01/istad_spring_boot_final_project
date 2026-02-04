package com.sopheak.istadfinalems.service.project;
import com.sopheak.istadfinalems.entities.Employee;
import com.sopheak.istadfinalems.entities.Project;
import com.sopheak.istadfinalems.entities.emun.AuditAction;
import com.sopheak.istadfinalems.exception.ProjectNotFoundException;
import com.sopheak.istadfinalems.mapper.ProjectMapStruct;
import com.sopheak.istadfinalems.model.dto.project.ProjectCreateDto;
import com.sopheak.istadfinalems.model.dto.project.ProjectResponseDto;
import com.sopheak.istadfinalems.model.dto.project.ProjectUpdateDto;
import com.sopheak.istadfinalems.repository.EmployeeRepository;
import com.sopheak.istadfinalems.repository.ProjectRepository;
import com.sopheak.istadfinalems.service.audit.AuditService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapStruct projectMapStruct;
    private final EmployeeRepository employeeRepository;
    private final AuditService auditService;

    @Override
    public ProjectResponseDto getProjectByUuid(String uuid) {
        Optional<Project> project = projectRepository.findByUuidAndIsDeletedFalse(uuid);
        if (project.isEmpty()) {
            throw new ProjectNotFoundException("Project not found with this uuid: " + uuid);
        }
        return projectMapStruct.mapFromProjectToProjectResponseDto(project.get());
    }

    @Override
    public Page<ProjectResponseDto> getAllProjectsByPagination(Pageable pageable) {
        Page<Project> projectPage = projectRepository.findAllByIsDeletedFalse(pageable);
        if (projectPage.isEmpty()) {
            throw new ProjectNotFoundException("No Project found for this page");
        }
        return projectPage.map(projectMapStruct::mapFromProjectToProjectResponseDto);
    }

    @Override
    @Transactional
    public ProjectResponseDto createProject(ProjectCreateDto createDto) {
        if (projectRepository.existsByProjectNameAndIsDeletedFalse(createDto.projectName())) {
            throw new IllegalArgumentException("Project name already exists: " + createDto.projectName());
        }

        Project project = new Project();

        project.setUuid(UUID.randomUUID().toString());
        project.setProjectName(createDto.projectName());
        project.setDescription(createDto.description());
        project.setIsDeleted(false);

        if (createDto.employeeUuids() != null && !createDto.employeeUuids().isEmpty()) {
            List<Employee> employees = employeeRepository.findAllByUuidIn(
                    new ArrayList<>(createDto.employeeUuids())
            );
            if (employees.size() != createDto.employeeUuids().size()) {
                throw new IllegalArgumentException("One or more Employee UUIDs are invalid.");
            }
            project.setEmployees(new HashSet<>(employees));
            for (Employee employee : employees) {
                employee.getProjects().add(project);
            }
        }
        projectRepository.save(project);
        auditService.log(
                "Project",
                project.getId().toString(),
                AuditAction.CREATE,
                "Created project: " + project.getProjectName()
        );
        return projectMapStruct.mapFromProjectToProjectResponseDto(project);
    }

    @Override
    public ProjectResponseDto updateProjectByUuid(String uuid, ProjectUpdateDto updateDto) {
        Project project = projectRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with UUID: " + uuid));

        project.setProjectName(updateDto.projectName());
        project.setDescription(updateDto.description());
        project.setIsDeleted(updateDto.isDeleted());

        if (updateDto.employeeUuids() != null) {
            for (Employee emp : project.getEmployees()) {
                emp.getProjects().remove(project);
            }
            project.getEmployees().clear();

            List<Employee> newEmployees = employeeRepository.findAllByUuidIn(
                    new ArrayList<>(updateDto.employeeUuids()));
            if (newEmployees.size() != updateDto.employeeUuids().size()) {
                throw new IllegalArgumentException("One or more Employee UUIDs are invalid.");
            }
            project.setEmployees(new HashSet<>(newEmployees));
            for (Employee emp : newEmployees) {
                emp.getProjects().add(project);
            }
        }
        projectRepository.save(project);
        auditService.log(
                "Project",
                project.getId().toString(),
                AuditAction.UPDATE,
                "Updated project: " + project.getProjectName()
        );
        return projectMapStruct.mapFromProjectToProjectResponseDto(project);
    }

    @Override
    public String deleteProjectByUuid(String uuid) {
        Project project = projectRepository.findByUuidAndIsDeletedFalse(uuid)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with UUID: " + uuid));
        project.setIsDeleted(true);
        for (Employee emp : project.getEmployees()) {
            emp.getProjects().remove(project);
        }
        project.getEmployees().clear();
        projectRepository.save(project);
        auditService.log(
                "Project",
                project.getId().toString(),
                AuditAction.DELETE,
                "Soft deleted project: " + project.getProjectName()
        );
        return "Project with UUID " + uuid + " has been deleted successfully";
    }
}
