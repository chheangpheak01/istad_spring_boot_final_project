package com.sopheak.istadfinalems.service;
import com.sopheak.istadfinalems.model.dto.project.ProjectCreateDto;
import com.sopheak.istadfinalems.model.dto.project.ProjectResponseDto;
import com.sopheak.istadfinalems.model.dto.project.ProjectUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    ProjectResponseDto getProjectByUuid(String uuid);
    Page<ProjectResponseDto> getAllProjectsByPagination(Pageable pageable);
    ProjectResponseDto createProject(ProjectCreateDto createDto);
    ProjectResponseDto updateProjectByUuid(String uuid, ProjectUpdateDto updateDto);
    String deleteProjectByUuid(String uuid);
}