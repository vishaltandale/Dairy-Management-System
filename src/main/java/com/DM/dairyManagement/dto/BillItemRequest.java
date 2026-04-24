package com.DM.dairyManagement.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record BillItemRequest(
    @NotBlank(message = "Product name is required")
    String productName,
    
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    Integer quantity,
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0", message = "Price must be positive")
    BigDecimal price
) {}
