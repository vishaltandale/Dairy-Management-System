package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.dto.ApiResponse;
import com.DM.dairyManagement.model.OtherExpense;
import com.DM.dairyManagement.service.ExpenseService;
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
@RequestMapping("/api/v1/expenses")
@Tag(name = "Expense Management", description = "APIs for managing expenses")
public class ExpenseRestController {
    
    @Autowired
    private ExpenseService expenseService;
    
    @GetMapping
    @Operation(summary = "Get all expenses", description = "Retrieve paginated list of expenses with optional search")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved expenses")
    })
    public ResponseEntity<ApiResponse<List<OtherExpense>>> getAllExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String search) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        Page<OtherExpense> expensesPage = expenseService.getAllExpenses(search, pageable);
        return ResponseEntity.ok(ApiResponse.success(expensesPage.getContent(), 
            "Found " + expensesPage.getTotalElements() + " expenses"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OtherExpense>> getExpenseById(@PathVariable Long id) {
        OtherExpense expense = expenseService.getExpenseById(id);
        return ResponseEntity.ok(ApiResponse.success(expense));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<OtherExpense>> createExpense(@RequestBody OtherExpense expense) {
        OtherExpense savedExpense = expenseService.saveExpense(expense);
        return ResponseEntity.ok(ApiResponse.success(savedExpense, "Expense created successfully"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OtherExpense>> updateExpense(
            @PathVariable Long id,
            @RequestBody OtherExpense expense) {
        expense.setId(id);
        OtherExpense updatedExpense = expenseService.updateExpense(expense);
        return ResponseEntity.ok(ApiResponse.success(updatedExpense, "Expense updated successfully"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Expense deleted successfully"));
    }
}
