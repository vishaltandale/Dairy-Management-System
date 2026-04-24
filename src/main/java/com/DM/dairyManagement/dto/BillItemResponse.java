package com.DM.dairyManagement.dto;

import java.math.BigDecimal;

public record BillItemResponse(
    Long id,
    String productName,
    Integer quantity,
    BigDecimal price,
    BigDecimal subtotal
) {}
