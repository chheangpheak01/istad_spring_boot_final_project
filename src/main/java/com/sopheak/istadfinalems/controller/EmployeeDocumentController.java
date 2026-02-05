package com.sopheak.istadfinalems.controller;
import com.sopheak.istadfinalems.model.dto.employee_document.EmployeeDocumentCreateDto;
import com.sopheak.istadfinalems.model.dto.employee_document.EmployeeDocumentUpdateDto;
import com.sopheak.istadfinalems.service.EmployeeDocumentService;
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
@RequestMapping("/api/v1/employeeDocument")
public class EmployeeDocumentController {

    private final EmployeeDocumentService employeeDocumentService;

    @GetMapping("/get-employeeDocument-by-uuid/{uuid}")
    public ResponseTemplate<Object> findDocumentByUuid(@PathVariable String uuid){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee document data retrieved successfully")
                .data(employeeDocumentService.findDocumentByUuid(uuid))
                .build();
    }

    @GetMapping("/get-employeeDocument-by-employeeId/{uuid}")
    public ResponseTemplate<Object> findDocumentsByEmployeeUuid(@PathVariable String uuid) {
        return ResponseTemplate.builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee documents data retrieved successfully")
                .data(employeeDocumentService.findDocumentsByEmployeeUuid(uuid))
                .build();
    }

    @GetMapping("/get-employeeDocument-by-pagination/pagination")
    public ResponseTemplate<Object> findAllDocuments(Pageable pageable){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee documents data retrieved by pagination successfully")
                .data(employeeDocumentService.findAllDocuments(pageable))
                .build();
    }

    @PostMapping("/create-employeeDocument")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTemplate<Object> createDocument(@RequestBody @Validated EmployeeDocumentCreateDto createDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.CREATED.toString())
                .message("Employee documents data has been created successfully")
                .data(employeeDocumentService.createDocument(createDto))
                .build();
    }

    @PutMapping("/update-employeeDocument-by-uuid/{uuid}")
    public ResponseTemplate<Object> updateDocumentByUuid(@PathVariable String uuid, @RequestBody @Validated EmployeeDocumentUpdateDto updateDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee documents data has been updated successfully")
                .data(employeeDocumentService.updateDocumentByUuid(uuid, updateDto))
                .build();
    }

    @DeleteMapping("/delete-employeeDocument-by-uuid/{uuid}")
    public ResponseTemplate<Object> deleteDocumentByUuid(@PathVariable String uuid){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message(employeeDocumentService.deleteDocumentByUuid(uuid))
                .data(null)
                .build();
    }

}
