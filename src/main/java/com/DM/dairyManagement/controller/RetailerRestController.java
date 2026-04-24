package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.dto.ApiResponse;
import com.DM.dairyManagement.model.Retailer;
import com.DM.dairyManagement.service.RetailerService;
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
@RequestMapping("/api/v1/retailers")
@Tag(name = "Retailer Management", description = "APIs for managing retailers")
public class RetailerRestController {
    
    @Autowired
    private RetailerService retailerService;
    
    @GetMapping
    @Operation(summary = "Get all retailers", description = "Retrieve paginated list of retailers with optional search")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved retailers")
    })
    public ResponseEntity<ApiResponse<List<Retailer>>> getAllRetailers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Retailer> retailersPage = retailerService.getAllRetailers(search, pageable);
        return ResponseEntity.ok(ApiResponse.success(retailersPage.getContent(), 
            "Found " + retailersPage.getTotalElements() + " retailers"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Retailer>> getRetailerById(@PathVariable Long id) {
        Retailer retailer = retailerService.getRetailerById(id);
        return ResponseEntity.ok(ApiResponse.success(retailer));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Retailer>> createRetailer(@RequestBody Retailer retailer) {
        Retailer savedRetailer = retailerService.saveRetailer(retailer);
        return ResponseEntity.ok(ApiResponse.success(savedRetailer, "Retailer created successfully"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Retailer>> updateRetailer(
            @PathVariable Long id,
            @RequestBody Retailer retailer) {
        retailer.setId(id);
        Retailer updatedRetailer = retailerService.updateRetailer(retailer);
        return ResponseEntity.ok(ApiResponse.success(updatedRetailer, "Retailer updated successfully"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRetailer(@PathVariable Long id) {
        retailerService.deleteRetailer(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Retailer deleted successfully"));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Retailer>>> searchRetailers(
            @RequestParam String keyword) {
        List<Retailer> retailers = retailerService.searchRetailers(keyword);
        return ResponseEntity.ok(ApiResponse.success(retailers));
    }
}
