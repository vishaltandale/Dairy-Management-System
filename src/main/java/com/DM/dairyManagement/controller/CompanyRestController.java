package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.dto.ApiResponse;
import com.DM.dairyManagement.model.Company;
import com.DM.dairyManagement.model.User;
import com.DM.dairyManagement.service.CompanyService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
@Tag(name = "Company Management", description = "APIs for managing supplier companies")
public class CompanyRestController {
    
    @Autowired
    private CompanyService companyService;
    
    @GetMapping
    @Operation(summary = "Get all companies", description = "Retrieve paginated list of companies with optional search")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved companies"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized - Login required")
    })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<Company>>> getAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Company> companiesPage = companyService.getAllCompanies(search, pageable);
        
        return ResponseEntity.ok(ApiResponse.success(companiesPage.getContent(), 
            "Found " + companiesPage.getTotalElements() + " companies"));
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get company by ID", description = "Retrieve a specific company by its ID")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Company found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Company not found")
    })
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<Company>> getCompanyById(@PathVariable Long id) {
        Company company = companyService.getCompanyById(id);
        return ResponseEntity.ok(ApiResponse.success(company));
    }
    
    @PostMapping
    @Operation(summary = "Create company", description = "Add a new supplier company")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Company created successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Owner/Manager role required")
    })
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    public ResponseEntity<ApiResponse<Company>> createCompany(
            @Valid @RequestBody Company company,
            HttpSession session) {
        
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Please login first"));
        }
        
        if (!"OWNER".equals(user.getRole().name()) && !"MANAGER".equals(user.getRole().name())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("Only owners and managers can create companies"));
        }
        
        Company savedCompany = companyService.saveCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(savedCompany, "Company created successfully"));
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update company", description = "Update an existing company")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Company updated successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Company not found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Owner/Manager role required")
    })
    @PreAuthorize("hasAnyRole('OWNER', 'MANAGER')")
    public ResponseEntity<ApiResponse<Company>> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody Company companyDetails,
            HttpSession session) {
        
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Please login first"));
        }
        
        if (!"OWNER".equals(user.getRole().name()) && !"MANAGER".equals(user.getRole().name())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("Only owners and managers can update companies"));
        }
        
        Company updatedCompany = companyService.updateCompany(id, companyDetails);
        return ResponseEntity.ok(ApiResponse.success(updatedCompany, "Company updated successfully"));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete company", description = "Delete a company (Owner only)")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Company deleted successfully"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Company not found"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "403", description = "Forbidden - Owner role required")
    })
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Please login first"));
        }
        
        if (!"OWNER".equals(user.getRole().name())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(ApiResponse.error("Only owners can delete companies"));
        }
        
        companyService.deleteCompany(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Company deleted successfully"));
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search companies", description = "Search companies by name")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<List<Company>>> searchCompanies(@RequestParam String query) {
        List<Company> companies = companyService.searchCompaniesByName(query);
        return ResponseEntity.ok(ApiResponse.success(companies));
    }
}
