package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.dto.ApiResponse;
import com.DM.dairyManagement.model.Stock;
import com.DM.dairyManagement.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@Tag(name = "Stock Management", description = "APIs for managing stock inventory")
public class StockRestController {
    
    @Autowired
    private StockService stockService;
    
    @GetMapping
    @Operation(summary = "Get all stock", description = "Retrieve paginated list of stock items")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved stock")
    })
    public ResponseEntity<ApiResponse<List<Stock>>> getAllStock(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("productName").ascending());
        Page<Stock> stockPage = stockService.getAllStock(search, pageable);
        return ResponseEntity.ok(ApiResponse.success(stockPage.getContent(), 
            "Found " + stockPage.getTotalElements() + " stock items"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Stock>> getStockById(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);
        return ResponseEntity.ok(ApiResponse.success(stock));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Stock>> updateStock(
            @PathVariable Long id,
            @RequestBody Stock stock) {
        stock.setId(id);
        Stock updatedStock = stockService.updateStock(stock);
        return ResponseEntity.ok(ApiResponse.success(updatedStock, "Stock updated successfully"));
    }
    
    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<Stock>>> getLowStock() {
        List<Stock> lowStock = stockService.getLowStockItems();
        return ResponseEntity.ok(ApiResponse.success(lowStock));
    }
}
