package com.sopheak.istadfinalems.service;
import com.sopheak.istadfinalems.entities.Employee;
import com.sopheak.istadfinalems.entities.emun.EmployeeStatus;
import com.sopheak.istadfinalems.entities.report_exporting.CsvGenerator;
import com.sopheak.istadfinalems.entities.report_exporting.ExcelGenerator;
import com.sopheak.istadfinalems.entities.report_exporting.PdfGenerator;
import com.sopheak.istadfinalems.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService{

    private final EmployeeRepository employeeRepository;

    @Override
    public ByteArrayInputStream exportEmployeeExcel(String departmentName, String jobTitle, EmployeeStatus status) {
        List<Employee> employees = fetchEmployees(departmentName, jobTitle, status);
        if (employees.isEmpty()) return null;

        try {
            return ExcelGenerator.employeesToExcel(employees);
        } catch (IOException e) {
            throw new RuntimeException("Excel generation failed", e);
        }
    }

    @Override
    public ByteArrayInputStream exportEmployeePdf(String departmentName, String jobTitle, EmployeeStatus status) {
        List<Employee> employees = fetchEmployees(departmentName, jobTitle, status);
        if (employees.isEmpty()) return null;

        return PdfGenerator.employeesToPdf(employees);
    }

    @Override
    public ByteArrayInputStream exportEmployeeCsv(String departmentName, String jobTitle, EmployeeStatus status) {
        List<Employee> employees = fetchEmployees(departmentName, jobTitle, status);
        if (employees.isEmpty()) return null;

        return CsvGenerator.employeesToCsv(employees);
    }

    private List<Employee> fetchEmployees(String dept, String title, EmployeeStatus status) {
        if (dept != null && title != null && status != null) {
            return employeeRepository.findEmployeeByDepartmentNameAndJobPositionTitleAndStatus(dept, title, status);
        } else if (dept != null && title != null) {
            return employeeRepository.findEmployeeByDepartmentNameAndJobPositionTitle(dept, title);
        } else if (dept != null) {
            return employeeRepository.findEmployeeByDepartmentName(dept);
        } else if (title != null) {
            return employeeRepository.findEmployeeByJobPositionTitle(title);
        } else if (status != null) {
            return employeeRepository.findEmployeeByStatus(status);
        }
        return employeeRepository.findAll();
    }
}
