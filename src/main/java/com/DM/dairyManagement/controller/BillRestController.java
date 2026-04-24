package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.dto.*;
import com.DM.dairyManagement.exception.ResourceNotFoundException;
import com.DM.dairyManagement.mapper.BillMapper;
import com.DM.dairyManagement.model.Bill;
import com.DM.dairyManagement.model.Item;
import com.DM.dairyManagement.model.User;
import com.DM.dairyManagement.service.BillService;
import com.DM.dairyManagement.service.PdfGenerationService;
import com.DM.dairyManagement.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/bills")
@Tag(name = "Bill Management", description = "APIs for managing bills and invoices")
public class BillRestController {
    
    @Autowired
    private BillService billService;
    
    @Autowired
    private StockService stockService;
    
    @Autowired
    private BillMapper billMapper;
    
    @Autowired
    private PdfGenerationService pdfGenerationService;
    
    @Autowired
    private com.DM.dairyManagement.service.AuthorizationService authorizationService;
    
    @Autowired
    private com.DM.dairyManagement.service.DataExportService dataExportService;
    
    @GetMapping
    @Operation(summary = "Get all bills", description = "Retrieve paginated list of bills with optional filters")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getAllBills(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String customerType,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<Bill> billsPage;
        
        // Apply filters with pagination
        if (search != null && !search.isEmpty()) {
            billsPage = billService.searchBillsPaginated(search, pageable);
        } else if (customerType != null && !customerType.isEmpty()) {
            billsPage = billService.getBillsByCustomerTypePaginated(customerType, pageable);
        } else {
            billsPage = billService.getAllBillsPaginated(pageable);
        }
        
        List<Bill> bills = billsPage.getContent();
        
        // Apply status filter (in-memory as it's calculated)
        if (status != null && !status.isEmpty()) {
            bills = bills.stream()
                .filter(b -> {
                    java.math.BigDecimal balance = b.getBalanceDue();
                    if (status.equals("PAID")) return balance.compareTo(java.math.BigDecimal.ZERO) <= 0;
                    if (status.equals("PARTIAL")) return balance.compareTo(java.math.BigDecimal.ZERO) > 0 && b.getPaidAmount().compareTo(java.math.BigDecimal.ZERO) > 0;
                    if (status.equals("UNPAID")) return b.getPaidAmount().compareTo(java.math.BigDecimal.ZERO) == 0;
                    return true;
                })
                .collect(Collectors.toList());
        }
        
        List<BillResponse> responses = bills.stream()
            .map(billMapper::toResponse)
            .collect(Collectors.toList());
        
        Map<String, Object> result = Map.of(
            "bills", responses,
            "totalElements", billsPage.getTotalElements(),
            "totalPages", billsPage.getTotalPages(),
            "currentPage", billsPage.getNumber(),
            "pageSize", billsPage.getSize()
        );
        
        return ResponseEntity.ok(ApiResponse.success(result));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BillResponse>> getBillById(@PathVariable Long id) {
        Bill bill = billService.getBillById(id);
        return ResponseEntity.ok(ApiResponse.success(billMapper.toResponse(bill)));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<BillResponse>> createBill(
            @Valid @RequestBody CreateBillRequest request,
            HttpSession session) {
        
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Please login first"));
        }
        
        // Check if user has permission to create bills
        if (!authorizationService.hasPermission(user, "CREATE_BILL")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("You do not have permission to create bills"));
        }
        
        Bill bill = billMapper.toEntity(request);
        
        List<Item> items = new ArrayList<>();
        for (BillItemRequest itemRequest : request.items()) {
            Item item = new Item();
            item.setProductName(itemRequest.productName());
            item.setQuantity(itemRequest.quantity());
            item.setPrice(itemRequest.price());
            item.setBill(bill);
            items.add(item);
        }
        bill.setItems(items);
        
        Bill savedBill = billService.createBill(bill, user);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(billMapper.toResponse(savedBill), "Bill created successfully"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BillResponse>> updateBill(
            @PathVariable Long id,
            @Valid @RequestBody CreateBillRequest request,
            HttpSession session) {
        
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Please login first"));
        }
        
        // Check if user has permission using AuthorizationService
        if (!authorizationService.hasPermission(user, "EDIT_BILL")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("You do not have permission to update bills"));
        }
        
        Bill existingBill = billService.getBillById(id);
        
        // Update bill fields
        existingBill.setFullName(request.fullName());
        existingBill.setMobileNumber(request.mobileNumber());
        existingBill.setCustomerType(request.customerType());
        existingBill.setDiscount(request.discount());
        
        // Update items
        existingBill.getItems().clear();
        for (BillItemRequest itemRequest : request.items()) {
            Item item = new Item();
            item.setProductName(itemRequest.productName());
            item.setQuantity(itemRequest.quantity());
            item.setPrice(itemRequest.price());
            item.setBill(existingBill);
            existingBill.getItems().add(item);
        }
        
        Bill updatedBill = billService.updateBill(existingBill, user);
        return ResponseEntity.ok(ApiResponse.success(billMapper.toResponse(updatedBill), "Bill updated successfully"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBill(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Please login first"));
        }
        
        if (!authorizationService.hasPermission(user, "DELETE_BILL")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("You do not have permission to delete bills"));
        }
        
        billService.deleteBill(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Bill deleted successfully"));
    }
    
    @GetMapping("/{id}/pdf")
    @Operation(summary = "Download bill PDF", description = "Generate and download bill as PDF")
    public ResponseEntity<byte[]> downloadBillPdf(@PathVariable Long id) {
        Bill bill = billService.getBillById(id);
        
        byte[] pdfContent = pdfGenerationService.generateBillPdf(bill);
        
        String filename = "Bill_" + bill.getBillNo() + ".pdf";
        
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
            .body(pdfContent);
    }
    
    @GetMapping("/export/excel")
    @Operation(summary = "Export bills to Excel", description = "Download all bills as Excel file")
    public ResponseEntity<byte[]> exportBillsToExcel(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        try {
            List<Bill> bills = billService.getAllBills();
            byte[] excelData = dataExportService.exportBillsToExcel(bills);
            
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bills.xlsx")
                .body(excelData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/export/csv")
    @Operation(summary = "Export bills to CSV", description = "Download all bills as CSV file")
    public ResponseEntity<byte[]> exportBillsToCsv(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        try {
            List<Bill> bills = billService.getAllBills();
            byte[] csvData = dataExportService.exportBillsToCsv(bills);
            
            return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("text/csv"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bills.csv")
                .body(csvData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
