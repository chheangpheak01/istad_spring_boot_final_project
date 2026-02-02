package com.sopheak.istadfinalems.entities.report_exporting;
import com.sopheak.istadfinalems.entities.Employee;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {

    public static ByteArrayInputStream employeesToExcel(List<Employee> employees) throws IOException {
        String[] COLUMN_HEADERS = {"Employee ID", "Full Name", "Email", "Gender", "Position", "Department", "Hire Date", "Salary", "Status"};

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Employee Report");

            Row headerRow = sheet.createRow(0);
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            for (int i = 0; i < COLUMN_HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(COLUMN_HEADERS[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowIndex = 1;
            for (Employee emp : employees) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(emp.getEmployeeId());
                row.createCell(1).setCellValue(emp.getName());
                row.createCell(2).setCellValue(emp.getEmail());
                row.createCell(3).setCellValue(emp.getGender().name());

                row.createCell(4).setCellValue(emp.getJobPosition() != null ? emp.getJobPosition().getTitle() : "N/A");
                row.createCell(5).setCellValue(emp.getDepartment() != null ? emp.getDepartment().getName() : "N/A");

                row.createCell(6).setCellValue(emp.getHireDate().toString());
                row.createCell(7).setCellValue(emp.getSalary().doubleValue());
                row.createCell(8).setCellValue(emp.getStatus().name());
            }

            for (int i = 0; i < COLUMN_HEADERS.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}