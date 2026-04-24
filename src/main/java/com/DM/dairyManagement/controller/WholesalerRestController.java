package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.dto.ApiResponse;
import com.DM.dairyManagement.model.Wholesaler;
import com.DM.dairyManagement.service.WholesalerService;
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
@RequestMapping("/api/v1/wholesalers")
@Tag(name = "Wholesaler Management", description = "APIs for managing wholesalers")
public class WholesalerRestController {
    
    @Autowired
    private WholesalerService wholesalerService;
    
    @GetMapping
    @Operation(summary = "Get all wholesalers", description = "Retrieve paginated list of wholesalers with optional search")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved wholesalers")
    })
    public ResponseEntity<ApiResponse<List<Wholesaler>>> getAllWholesalers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Wholesaler> wholesalersPage = wholesalerService.getAllWholesalers(search, pageable);
        return ResponseEntity.ok(ApiResponse.success(wholesalersPage.getContent(), 
            "Found " + wholesalersPage.getTotalElements() + " wholesalers"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Wholesaler>> getWholesalerById(@PathVariable Long id) {
        Wholesaler wholesaler = wholesalerService.getWholesalerById(id);
        return ResponseEntity.ok(ApiResponse.success(wholesaler));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Wholesaler>> createWholesaler(@RequestBody Wholesaler wholesaler) {
        Wholesaler savedWholesaler = wholesalerService.saveWholesaler(wholesaler);
        return ResponseEntity.ok(ApiResponse.success(savedWholesaler, "Wholesaler created successfully"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Wholesaler>> updateWholesaler(
            @PathVariable Long id,
            @RequestBody Wholesaler wholesaler) {
        wholesaler.setId(id);
        Wholesaler updatedWholesaler = wholesalerService.updateWholesaler(wholesaler);
        return ResponseEntity.ok(ApiResponse.success(updatedWholesaler, "Wholesaler updated successfully"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteWholesaler(@PathVariable Long id) {
        wholesalerService.deleteWholesaler(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Wholesaler deleted successfully"));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Wholesaler>>> searchWholesalers(
            @RequestParam String keyword) {
        List<Wholesaler> wholesalers = wholesalerService.searchWholesalers(keyword);
        return ResponseEntity.ok(ApiResponse.success(wholesalers));
    }
}
