package com.sopheak.istadfinalems.entities.report_exporting;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sopheak.istadfinalems.entities.Employee;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfGenerator {
    public static ByteArrayInputStream employeesToPdf(List<Employee> employees) {
        Document document = new Document(PageSize.A4.rotate());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Employee Report", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);

            String[] headers = {"Employee_ID", "Employee_Name", "Employee_Email", "Position", "Dept", "Salary", "Status"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h));
                cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
                table.addCell(cell);
            }

            for (Employee emp : employees) {
                table.addCell(emp.getEmployeeId());
                table.addCell(emp.getName());
                table.addCell(emp.getEmail());
                table.addCell(emp.getJobPosition() != null ? emp.getJobPosition().getTitle() : "N/A");
                table.addCell(emp.getDepartment() != null ? emp.getDepartment().getName() : "N/A");
                table.addCell(emp.getSalary().toString());
                table.addCell(emp.getStatus().name());
            }

            document.add(table);
            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("PDF error", e);
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}