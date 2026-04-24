package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.dto.ApiResponse;
import com.DM.dairyManagement.model.Sell;
import com.DM.dairyManagement.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sells")
public class SellRestController {
    
    @Autowired
    private SellService sellService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Sell>>> getAllSells() {
        List<Sell> sells = sellService.getAllSells();
        return ResponseEntity.ok(ApiResponse.success(sells));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Sell>> getSellById(@PathVariable Long id) {
        Sell sell = sellService.getSellById(id);
        return ResponseEntity.ok(ApiResponse.success(sell));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Sell>> createSell(@RequestBody Sell sell) {
        Sell savedSell = sellService.saveSell(sell);
        return ResponseEntity.ok(ApiResponse.success(savedSell, "Sell recorded successfully"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSell(@PathVariable Long id) {
        sellService.deleteSell(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Sell deleted successfully"));
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<ApiResponse<List<Sell>>> getSellsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Sell> sells = sellService.getSellsByDateRange(startDate, endDate);
        return ResponseEntity.ok(ApiResponse.success(sells));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Sell>>> searchSells(
            @RequestParam String customerName) {
        List<Sell> sells = sellService.getSellsByCustomerName(customerName);
        return ResponseEntity.ok(ApiResponse.success(sells));
    }
}
