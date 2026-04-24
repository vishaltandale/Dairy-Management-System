package com.DM.dairyManagement.mapper;

import com.DM.dairyManagement.dto.*;
import com.DM.dairyManagement.model.Bill;
import com.DM.dairyManagement.model.Item;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

@Component
public class BillMapper {
    
    public Bill toEntity(CreateBillRequest request) {
        Bill bill = new Bill();
        bill.setFullName(request.fullName());
        bill.setMobileNumber(request.mobileNumber());
        bill.setCustomerType(request.customerType());
        bill.setDate(request.date());
        bill.setDiscount(request.discount() != null ? request.discount() : BigDecimal.ZERO);
        bill.setPaidAmount(request.paidAmount() != null ? request.paidAmount() : BigDecimal.ZERO);
        return bill;
    }
    
    public BillResponse toResponse(Bill bill) {
        return new BillResponse(
            bill.getId(),
            bill.getBillNo(),
            bill.getFullName(),
            bill.getMobileNumber(),
            bill.getCustomerType(),
            bill.getDate(),
            bill.getItems().stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList()),
            bill.getSubtotal(),
            bill.getCgst(),
            bill.getSgst(),
            bill.getDiscount(),
            bill.getTotal(),
            bill.getPaidAmount(),
            bill.getBalanceDue()
        );
    }
    
    private BillItemResponse toItemResponse(Item item) {
        return new BillItemResponse(
            item.getId(),
            item.getProductName(),
            item.getQuantity(),
            item.getPrice(),
            item.getSubtotal()
        );
    }
}
