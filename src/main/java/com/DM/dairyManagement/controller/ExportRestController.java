package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.model.User;
import com.DM.dairyManagement.service.DataExportService;
import com.DM.dairyManagement.service.EmployeeService;
import com.DM.dairyManagement.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/export")
@Tag(name = "Data Export", description = "Export data to Excel/CSV formats")
public class ExportRestController {
    
    @Autowired
    private DataExportService dataExportService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping("/products/excel")
    @Operation(summary = "Export products to Excel", description = "Download all products as Excel file")
    public ResponseEntity<byte[]> exportProductsToExcel(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        try {
            Pageable pageable = PageRequest.of(0, 10000, Sort.by("name").ascending());
            byte[] excelData = dataExportService.exportProductsToExcel(
                productService.getAllProducts(null, pageable).getContent());
            
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=products.xlsx")
                .body(excelData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/employees/excel")
    @Operation(summary = "Export employees to Excel", description = "Download all employees as Excel file")
    public ResponseEntity<byte[]> exportEmployeesToExcel(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        try {
            Pageable pageable = PageRequest.of(0, 10000, Sort.by("name").ascending());
            byte[] excelData = dataExportService.exportEmployeesToExcel(
                employeeService.getAllEmployees(null, pageable).getContent());
            
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=employees.xlsx")
                .body(excelData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
