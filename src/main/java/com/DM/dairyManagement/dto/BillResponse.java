package com.DM.dairyManagement.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BillResponse(
    Long id,
    Long billNo,
    String fullName,
    String mobileNumber,
    String customerType,
    LocalDate date,
    List<BillItemResponse> items,
    BigDecimal subtotal,
    BigDecimal cgst,
    BigDecimal sgst,
    BigDecimal discount,
    BigDecimal total,
    BigDecimal paidAmount,
    BigDecimal balanceDue
) {}
