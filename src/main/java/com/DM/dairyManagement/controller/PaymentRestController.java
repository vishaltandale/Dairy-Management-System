package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.dto.ApiResponse;
import com.DM.dairyManagement.model.Payment;
import com.DM.dairyManagement.service.PaymentService;
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
@RequestMapping("/api/v1/payments")
@Tag(name = "Payment Management", description = "APIs for managing payments")
public class PaymentRestController {
    
    @Autowired
    private PaymentService paymentService;
    
    @GetMapping
    @Operation(summary = "Get all payments", description = "Retrieve paginated list of payments")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Successfully retrieved payments")
    })
    public ResponseEntity<ApiResponse<List<Payment>>> getAllPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Long billId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("paymentDate").descending());
        Page<Payment> paymentsPage = billId != null 
            ? paymentService.getPaymentsByBillId(billId, pageable)
            : paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(ApiResponse.success(paymentsPage.getContent(), 
            "Found " + paymentsPage.getTotalElements() + " payments"));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Payment>> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.getPaymentById(id);
        return ResponseEntity.ok(ApiResponse.success(payment));
    }
    
    @PostMapping
    public ResponseEntity<ApiResponse<Payment>> createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.savePayment(payment);
        return ResponseEntity.ok(ApiResponse.success(savedPayment, "Payment recorded successfully"));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Payment>> updatePayment(
            @PathVariable Long id,
            @RequestBody Payment payment) {
        payment.setId(id);
        Payment updatedPayment = paymentService.updatePayment(payment);
        return ResponseEntity.ok(ApiResponse.success(updatedPayment, "Payment updated successfully"));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Payment deleted successfully"));
    }
}
