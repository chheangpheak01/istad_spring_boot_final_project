package com.sopheak.istadfinalems.controller;
import com.sopheak.istadfinalems.model.dto.department.DepartmentCreateDto;
import com.sopheak.istadfinalems.model.dto.department.DepartmentUpdateDto;
import com.sopheak.istadfinalems.service.DepartmentService;
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
@RequestMapping("/api/v1/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/get-department-by-uuid/{uuid}")
    public ResponseTemplate<Object> getDepartmentByUuid(@PathVariable String uuid){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Department data retrieved successfully")
                .data(departmentService.getDepartmentByUuid(uuid))
                .build();
    }

    @GetMapping("/get-department-by-name/{name}")
    public ResponseTemplate<Object> getDepartmentByName(@PathVariable String name){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Department data retrieved successfully")
                .data(departmentService.getDepartmentByName(name))
                .build();
    }

    @GetMapping("/get-department-by-pagination/pagination")
    public ResponseTemplate<Object> getAllDepartmentsByPagination(Pageable pageable){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Department data retrieved by pagination successfully")
                .data(departmentService.getAllDepartmentsByPagination(pageable))
                .build();
    }

    @PostMapping("/create-department")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTemplate<Object> createDepartment(@RequestBody @Validated DepartmentCreateDto departmentCreateDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.CREATED.toString())
                .message("Department has been created successfully")
                .data(departmentService.createDepartment(departmentCreateDto))
                .build();
    }

    @PutMapping("/update-department-by-uuid/{uuid}")
    public ResponseTemplate<Object> updateDepartmentByUuid(@PathVariable String uuid, @RequestBody @Validated DepartmentUpdateDto departmentUpdateDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Department has been updated successfully")
                .data(departmentService.updateDepartmentByUuid(uuid, departmentUpdateDto))
                .build();
    }
    @DeleteMapping("/delete-department-by-uuid/{uuid}")
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
