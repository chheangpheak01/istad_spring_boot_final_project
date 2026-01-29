package com.sopheak.istadfinalems.controller;
import com.sopheak.istadfinalems.model.dto.EmployeeCreateDto;
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
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;


    @GetMapping("/get-employee-by-uuid/{uuid}")
    public ResponseTemplate<Object> getEmployeeByUuid(@PathVariable String uuid){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee data retrieved successfully")
                .data(employeeService.getEmployeeByUuid(uuid))
                .build();
    }

    @GetMapping("/get-employee-by-name/{name}")
    public ResponseTemplate<Object> getEmployeeByName(@PathVariable String name){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee data retrieved successfully")
                .data(employeeService.getEmployeeByName(name))
                .build();
    }

    @GetMapping("/get-employee-by-phoneNumber/{phoneNumber}")
    public ResponseTemplate<Object> getEmployeeByPhoneNumber(@PathVariable String phoneNumber){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee data retrieved successfully")
                .data(employeeService.getEmployeeByPhoneNumber(phoneNumber))
                .build();
    }

    @GetMapping("/get-employee-by-pagination/pagination")
    public ResponseTemplate<Object> getAllEmployeesByPagination(Pageable pageable){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee data retrieved by pagination successfully")
                .data(employeeService.getAllEmployeesByPagination(pageable))
                .build();
    }

    @GetMapping("/get-employee-by-name-searching")
    public ResponseTemplate<Object> getAllEmployeesBySearchingNamePagination(@RequestParam String searchingName, Pageable pageable){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("Employee data retrieved by name searching successfully")
                .data(employeeService.searchEmployeeByName(searchingName, pageable))
                .build();
    }

    @GetMapping("/get-all-employees")
    public ResponseTemplate<Object> getAllEmployees(){
        return ResponseTemplate
                .builder()
                .date(Date.from(Instant.now()))
                .staus(HttpStatus.OK.toString())
                .message("All employee data are retrieved successfully")
                .data(employeeService.getAllEmployees())
                .build();
    }

    @PostMapping("/create-employee")
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

}
