package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.dto.ApiResponse;
import com.DM.dairyManagement.model.Employee;
import com.DM.dairyManagement.service.EmployeeService;
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
@RequestMapping("/api/v1/employees")
@Tag(name = "Employee Management", description = "APIs for managing employees")
public class EmployeeRestController {
    
    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping
    @Operation(summary = "Get all employees", description = "Retrieve paginated list of employees with optional search")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved employees")
    })
    public ResponseEntity<ApiResponse<List<Employee>>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        Page<Employee> employeesPage = employeeService.getAllEmployees(search, pageable);
        return ResponseEntity.ok(ApiResponse.success(employeesPage.getContent(), 
            "Found " + employeesPage.getTotalElements() + " employees"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(ApiResponse.success(employee));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@RequestBody Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(ApiResponse.success(savedEmployee, "Employee created successfully"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {
        employee.setId(id);
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        return ResponseEntity.ok(ApiResponse.success(updatedEmployee, "Employee updated successfully"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Employee deleted successfully"));
    }
}
