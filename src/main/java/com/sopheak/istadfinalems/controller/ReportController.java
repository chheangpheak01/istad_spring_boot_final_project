package com.sopheak.istadfinalems.controller;
import com.sopheak.istadfinalems.entities.emun.EmployeeStatus;
import com.sopheak.istadfinalems.service.report.ReportService;
import com.sopheak.istadfinalems.utils.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/employees/excel")
    public ResponseEntity<?> downloadEmployeeExcel(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) EmployeeStatus status) {

        ByteArrayInputStream bis = reportService.exportEmployeeExcel(department, title, status);
        if (bis == null) return getNotFoundResponse();

        String fileName = "employees_report_" + System.currentTimeMillis() + ".xlsx";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/employees/pdf")
    public ResponseEntity<?> downloadEmployeePdf(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) EmployeeStatus status) {

        ByteArrayInputStream bis = reportService.exportEmployeePdf(department, title, status);
        if (bis == null) return getNotFoundResponse();

        String fileName = "employees_report_" + System.currentTimeMillis() + ".pdf";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

    @GetMapping("/employees/csv")
    public ResponseEntity<?> downloadEmployeeCsv(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) EmployeeStatus status) {

        ByteArrayInputStream bis = reportService.exportEmployeeCsv(department, title, status);
        if (bis == null) return getNotFoundResponse();

        String fileName = "employees_report_" + System.currentTimeMillis() + ".csv";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(new InputStreamResource(bis));
    }

    private ResponseEntity<ResponseTemplate<Object>> getNotFoundResponse() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ResponseTemplate.builder()
                        .date(Date.from(Instant.now()))
                        .staus(HttpStatus.NOT_FOUND.toString())
                        .message("No employees found matching the provided filters.")
                        .data(null)
                        .build()
        );
    }
}
