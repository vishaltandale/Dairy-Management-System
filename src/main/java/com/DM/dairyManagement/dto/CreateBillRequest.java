package com.DM.dairyManagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record CreateBillRequest(
    @NotBlank(message = "Customer name is required")
    @Size(min = 2, max = 100)
    String fullName,
    
    @NotBlank(message = "Mobile number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid mobile number")
    String mobileNumber,
    
    @NotBlank(message = "Customer type is required")
    String customerType,
    
    @NotNull(message = "Date is required")
    LocalDate date,
    
    @NotEmpty(message = "At least one item required")
    @Valid
    java.util.List<BillItemRequest> items,
    
    @DecimalMin("0")
    BigDecimal discount,
    
    @DecimalMin("0")
    BigDecimal paidAmount
) {}
