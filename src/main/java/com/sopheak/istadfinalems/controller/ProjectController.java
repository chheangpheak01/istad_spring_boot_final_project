package com.sopheak.istadfinalems.controller;
import com.sopheak.istadfinalems.model.dto.project.ProjectCreateDto;
import com.sopheak.istadfinalems.model.dto.project.ProjectUpdateDto;
import com.sopheak.istadfinalems.service.project.ProjectService;
import com.sopheak.istadfinalems.utils.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/get-project-by-uuid/{uuid}")
    public ResponseTemplate<Object> getProjectByUuid(@PathVariable String uuid){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Project data retrieved successfully")
                .data(projectService.getProjectByUuid(uuid))
                .build();
    }

    @GetMapping("/get-project-by-pagination/pagination")
    public ResponseTemplate<Object> getAllProjectsByPagination(Pageable pageable){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Project data retrieved by pagination successfully")
                .data(projectService.getAllProjectsByPagination(pageable))
                .build();
    }

    @PostMapping("/create-project")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTemplate<Object> createProject(@RequestBody @Validated ProjectCreateDto createDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.CREATED.toString())
                .message("Project data has been created successfully")
                .data(projectService.createProject(createDto))
                .build();
    }

    @PutMapping("/update-project-by-uuid/{uuid}")
    public ResponseTemplate<Object> updateProjectByUuid(@PathVariable String uuid, @RequestBody @Validated ProjectUpdateDto updateDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Project data has been updated successfully")
                .data(projectService.updateProjectByUuid(uuid, updateDto))
                .build();
    }

    @DeleteMapping("/delete-project-by-uuid/{uuid}")
    public ResponseTemplate<Object> deleteProjectByUuid(@PathVariable String uuid){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message(projectService.deleteProjectByUuid(uuid))
                .data(null)
                .build();
    }
}
