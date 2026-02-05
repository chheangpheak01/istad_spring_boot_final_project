package com.sopheak.istadfinalems.controller;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeCreateDto;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeResponseDto;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeUpdateDto;
import com.sopheak.istadfinalems.service.EmployeeService;
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
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/{uuid}")
    public ResponseTemplate<Object> getEmployeeByUuid(@PathVariable String uuid){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee data retrieved successfully")
                .data(employeeService.getEmployeeByUuid(uuid))
                .build();
    }

    @GetMapping("/name/{name}")
    public ResponseTemplate<Object> getEmployeeByName(@PathVariable String name){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee data retrieved successfully")
                .data(employeeService.getEmployeeByName(name))
                .build();
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    public ResponseTemplate<Object> getEmployeeByPhoneNumber(@PathVariable String phoneNumber){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee data retrieved successfully")
                .data(employeeService.getEmployeeByPhoneNumber(phoneNumber))
                .build();
    }

    @GetMapping("/pagination")
    public ResponseTemplate<Object> getAllEmployeesByPagination(Pageable pageable){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee data retrieved by pagination successfully")
                .data(employeeService.getAllEmployeesByPagination(pageable))
                .build();
    }

    @GetMapping("/search")
    public ResponseTemplate<Object> getAllEmployeesBySearchingNamePagination(@RequestParam String searchingName, Pageable pageable){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee data retrieved by name searching successfully")
                .data(employeeService.searchEmployeeByName(searchingName, pageable))
                .build();
    }

    @GetMapping
    public ResponseTemplate<Object> getAllEmployees(){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("All employee data are retrieved successfully")
                .data(employeeService.getAllEmployees())
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseTemplate<Object> createEmployee(@RequestBody @Validated EmployeeCreateDto employeeCreateDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.CREATED.toString())
                .message("Employee has been created successfully")
                .data(employeeService.createEmployee(employeeCreateDto))
                .build();
    }

    @PutMapping("/{uuid}")
    public ResponseTemplate<Object> updateEmployeeByUuid(@PathVariable String uuid, @RequestBody @Validated EmployeeUpdateDto employeeUpdateDto){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee has been updated successfully")
                .data(employeeService.updateEmployeeByUuid(uuid, employeeUpdateDto))
                .build();
    }

    @PatchMapping("/{uuid}")
    public ResponseTemplate<Object> updateEmployeeStatus(@PathVariable String uuid, @RequestParam String status) {
        EmployeeResponseDto updatedEmployee = employeeService.updateEmployeeStatus(uuid, status);
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee status has been updated successfully" + status.toUpperCase())
                .data(updatedEmployee)
                .build();
    }

    @DeleteMapping("/{uuid}")
    public ResponseTemplate<Object> deleteEmployeeByUuid(@PathVariable String uuid) {
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message(employeeService.deleteEmployeeByUuid(uuid))
                .data(null)
                .build();
    }
}
