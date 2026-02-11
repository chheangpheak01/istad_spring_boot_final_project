package com.sopheak.istadfinalems.controller;
import com.sopheak.istadfinalems.model.dto.department.DepartmentCreateDto;
import com.sopheak.istadfinalems.model.dto.department.DepartmentUpdateDto;
import com.sopheak.istadfinalems.service.DepartmentService;
import com.sopheak.istadfinalems.utils.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@EnableMethodSecurity
@RequestMapping("/api/v1/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('user')")
    public ResponseTemplate<Object> getDepartmentByUuid(@PathVariable String uuid){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Department data retrieved successfully")
                .data(departmentService.getDepartmentByUuid(uuid))
                .build();
    }

    @GetMapping("/name/{name}")
    @PreAuthorize("hasRole('user')")
    public ResponseTemplate<Object> getDepartmentByName(@PathVariable String name){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Department data retrieved successfully")
                .data(departmentService.getDepartmentByName(name))
                .build();
    }

    @GetMapping("/pagination")
    @PreAuthorize("hasRole('user')")
    public ResponseTemplate<Object> getAllDepartmentsByPagination(Pageable pageable){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Department data retrieved by pagination successfully")
                .data(departmentService.getAllDepartmentsByPagination(pageable))
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('admin','super_admin')")
    public ResponseTemplate<Object> createDepartment(@RequestBody @Validated DepartmentCreateDto departmentCreateDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.CREATED.toString())
                .message("Department has been created successfully")
                .data(departmentService.createDepartment(departmentCreateDto))
                .build();
    }

    @PutMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('admin','super_admin')")
    public ResponseTemplate<Object> updateDepartmentByUuid(@PathVariable String uuid, @RequestBody @Validated DepartmentUpdateDto departmentUpdateDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Department has been updated successfully")
                .data(departmentService.updateDepartmentByUuid(uuid, departmentUpdateDto))
                .build();
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasAnyRole('admin','super_admin')")
    public ResponseTemplate<Object> deleteDepartmentByUuid(@PathVariable String uuid){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message(departmentService.deleteDepartmentByUuid(uuid))
                .data(null)
                .build();
    }
}
