package com.sopheak.istadfinalems.entities.report_exporting;
import com.opencsv.CSVWriter;
import com.sopheak.istadfinalems.entities.Employee;
import java.io.*;
import java.util.List;

public class CsvGenerator {
    public static ByteArrayInputStream employeesToCsv(List<Employee> employees) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(out))) {

            CSVWriter csvWriter = new CSVWriter(writer);
            csvWriter.writeNext(new String[]{"ID", "Name", "Email", "Department", "Position", "Status"});

            for (Employee emp : employees) {
                csvWriter.writeNext(new String[]{
                        emp.getEmployeeId(), emp.getName(), emp.getEmail(),
                        emp.getDepartment() != null ? emp.getDepartment().getName() : "N/A",
                        emp.getJobPosition() != null ? emp.getJobPosition().getTitle() : "N/A",
                        emp.getStatus().name()
                });
            }
            csvWriter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("CSV error", e);
        }
    }
}
