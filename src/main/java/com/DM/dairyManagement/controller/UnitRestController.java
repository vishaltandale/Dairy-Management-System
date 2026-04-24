package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.dto.ApiResponse;
import com.DM.dairyManagement.model.Unit;
import com.DM.dairyManagement.service.UnitService;
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
@RequestMapping("/api/v1/units")
@Tag(name = "Unit Management", description = "APIs for managing units of measurement")
public class UnitRestController {
    
    @Autowired
    private UnitService unitService;
    
    @GetMapping
    @Operation(summary = "Get all units", description = "Retrieve paginated list of units")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved units")
    })
    public ResponseEntity<ApiResponse<List<Unit>>> getAllUnits(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Unit> unitsPage = unitService.getAllUnits(search, pageable);
        return ResponseEntity.ok(ApiResponse.success(unitsPage.getContent(), 
            "Found " + unitsPage.getTotalElements() + " units"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Unit>> getUnitById(@PathVariable Long id) {
        Unit unit = unitService.getUnitById(id);
        return ResponseEntity.ok(ApiResponse.success(unit));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Unit>> createUnit(@RequestBody Unit unit) {
        Unit savedUnit = unitService.saveUnit(unit);
        return ResponseEntity.ok(ApiResponse.success(savedUnit, "Unit created successfully"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Unit>> updateUnit(
            @PathVariable Long id,
            @RequestBody Unit unit) {
        unit.setId(id);
        Unit updatedUnit = unitService.updateUnit(unit);
        return ResponseEntity.ok(ApiResponse.success(updatedUnit, "Unit updated successfully"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUnit(@PathVariable Long id) {
        unitService.deleteUnit(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Unit deleted successfully"));
    }
}
