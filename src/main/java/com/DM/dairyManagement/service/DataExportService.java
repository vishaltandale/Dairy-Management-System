package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Bill;
import com.DM.dairyManagement.model.Employee;
import com.DM.dairyManagement.model.Product;
import com.opencsv.CSVWriter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DataExportService {
    
    /**
     * Export bills to Excel
     */
    public byte[] exportBillsToExcel(List<Bill> bills) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            Sheet sheet = workbook.createSheet("Bills");
            Row headerRow = sheet.createRow(0);
            
            // Create headers
            String[] headers = {"Bill No", "Customer Name", "Mobile", "Date", "Customer Type", 
                               "Subtotal", "CGST", "SGST", "Discount", "Total", "Paid", "Balance"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }
            
            // Add data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (int i = 0; i < bills.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Bill bill = bills.get(i);
                row.createCell(0).setCellValue(bill.getBillNo());
                row.createCell(1).setCellValue(bill.getFullName());
                row.createCell(2).setCellValue(bill.getMobileNumber());
                row.createCell(3).setCellValue(bill.getDate().format(formatter));
                row.createCell(4).setCellValue(bill.getCustomerType());
                row.createCell(5).setCellValue(bill.getSubtotal().doubleValue());
                row.createCell(6).setCellValue(bill.getCgst().doubleValue());
                row.createCell(7).setCellValue(bill.getSgst().doubleValue());
                row.createCell(8).setCellValue(bill.getDiscount() != null ? bill.getDiscount().doubleValue() : 0);
                row.createCell(9).setCellValue(bill.getTotal().doubleValue());
                row.createCell(10).setCellValue(bill.getPaidAmount().doubleValue());
                row.createCell(11).setCellValue(bill.getBalanceDue().doubleValue());
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            workbook.write(out);
            return out.toByteArray();
        }
    }
    
    /**
     * Export bills to CSV
     */
    public byte[] exportBillsToCsv(List<Bill> bills) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVWriter writer = new CSVWriter(new OutputStreamWriter(out))) {
            
            // Write headers
            writer.writeNext(new String[]{"Bill No", "Customer Name", "Mobile", "Date", 
                                         "Customer Type", "Subtotal", "CGST", "SGST", 
                                         "Discount", "Total", "Paid", "Balance"});
            
            // Write data
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (Bill bill : bills) {
                writer.writeNext(new String[]{
                    bill.getBillNo().toString(),
                    bill.getFullName(),
                    bill.getMobileNumber(),
                    bill.getDate().format(formatter),
                    bill.getCustomerType(),
                    bill.getSubtotal().toString(),
                    bill.getCgst().toString(),
                    bill.getSgst().toString(),
                    bill.getDiscount() != null ? bill.getDiscount().toString() : "0",
                    bill.getTotal().toString(),
                    bill.getPaidAmount().toString(),
                    bill.getBalanceDue().toString()
                });
            }
            
            writer.flush();
            return out.toByteArray();
        }
    }
    
    /**
     * Export products to Excel
     */
    public byte[] exportProductsToExcel(List<Product> products) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            Sheet sheet = workbook.createSheet("Products");
            Row headerRow = sheet.createRow(0);
            
            String[] headers = {"ID", "Name", "Description", "HSN Code", "Company", "Unit",
                               "Customer Price", "Retailer Price", "Wholesaler Price"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }
            
            for (int i = 0; i < products.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Product product = products.get(i);
                row.createCell(0).setCellValue(product.getId());
                row.createCell(1).setCellValue(product.getName());
                row.createCell(2).setCellValue(product.getDescription());
                row.createCell(3).setCellValue(product.getHsnCode());
                row.createCell(4).setCellValue(product.getCompany() != null ? product.getCompany().getName() : "");
                row.createCell(5).setCellValue(product.getUnit() != null ? product.getUnit().getName() : "");
                row.createCell(6).setCellValue(product.getCustomerPrice());
                row.createCell(7).setCellValue(product.getRetailerPrice());
                row.createCell(8).setCellValue(product.getWholesalerPrice());
            }
            
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            workbook.write(out);
            return out.toByteArray();
        }
    }
    
    /**
     * Export employees to Excel
     */
    public byte[] exportEmployeesToExcel(List<Employee> employees) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            
            Sheet sheet = workbook.createSheet("Employees");
            Row headerRow = sheet.createRow(0);
            
            String[] headers = {"ID", "Name", "Mobile", "Email", "Designation", "Monthly Salary", "Advance"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }
            
            for (int i = 0; i < employees.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Employee employee = employees.get(i);
                row.createCell(0).setCellValue(employee.getId());
                row.createCell(1).setCellValue(employee.getName());
                row.createCell(2).setCellValue(employee.getMobile());
                row.createCell(3).setCellValue(employee.getEmail());
                row.createCell(4).setCellValue(employee.getDesignation());
                row.createCell(5).setCellValue(employee.getMonthlySalary());
                row.createCell(6).setCellValue(employee.getAdvance());
            }
            
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            workbook.write(out);
            return out.toByteArray();
        }
    }
}
