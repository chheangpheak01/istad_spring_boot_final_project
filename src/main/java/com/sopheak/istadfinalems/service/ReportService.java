package com.sopheak.istadfinalems.service;
import com.sopheak.istadfinalems.entities.emun.EmployeeStatus;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;

@Service
public interface ReportService {
    ByteArrayInputStream exportEmployeeExcel(String departmentName, String jobTitle, EmployeeStatus status);
    ByteArrayInputStream exportEmployeePdf(String departmentName, String jobTitle, EmployeeStatus status);
    ByteArrayInputStream exportEmployeeCsv(String departmentName, String jobTitle, EmployeeStatus status);
}
