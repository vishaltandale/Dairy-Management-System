package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Bill;
import com.DM.dairyManagement.model.Item;
import com.DM.dairyManagement.model.User;
import com.DM.dairyManagement.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BillService {
    
    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private StockService stockService;
    
    public Bill createBill(Bill bill, User user) {
        // Generate bill number
        long count = billRepository.countByDate(LocalDate.now()) + 1;
        long billNo = LocalDate.now().getYear() * 10000L + count;
        bill.setBillNo(billNo);
        
        // Calculate totals
        calculateTotals(bill);
        
        // Check and deduct stock
        for (Item item : bill.getItems()) {
            stockService.deductStock(item.getProductName(), item.getQuantity());
        }
        
        return billRepository.save(bill);
    }
    
    public List<Bill> getAllBills() {
        return billRepository.findAllByOrderByDateDesc();
    }
    
    public Page<Bill> getAllBillsPaginated(Pageable pageable) {
        return billRepository.findAllByOrderByDateDesc(pageable);
    }
    
    public Page<Bill> searchBillsPaginated(String searchTerm, Pageable pageable) {
        return billRepository.findByFullNameContainingIgnoreCaseOrBillNoContainingIgnoreCase(
            searchTerm, searchTerm, pageable);
    }
    
    public Page<Bill> getBillsByCustomerTypePaginated(String customerType, Pageable pageable) {
        return billRepository.findByCustomerType(customerType, pageable);
    }
    
    public Bill getBillById(Long id) {
        return billRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Bill not found with id: " + id));
    }
    
    public Bill getBillByBillNo(Long billNo) {
        return billRepository.findByBillNo(billNo)
            .orElseThrow(() -> new RuntimeException("Bill not found with number: " + billNo));
    }
    
    public List<Bill> getBillsByDateRange(LocalDate startDate, LocalDate endDate) {
        return billRepository.findByDateBetween(startDate, endDate);
    }
    
    public List<Bill> searchBills(String customerName) {
        return billRepository.findByFullNameContainingIgnoreCase(customerName);
    }
    
    public Bill updateBill(Bill bill) {
        calculateTotals(bill);
        return billRepository.save(bill);
    }
    
    public Bill updateBill(Bill bill, User user) {
        // Recalculate totals
        calculateTotals(bill);
        
        // TODO: Add audit logging
        // auditLogService.logAction(user, "UPDATE_BILL", "Bill", bill.getId(), "Updated bill #" + bill.getBillNo());
        
        return billRepository.save(bill);
    }
    
    public void deleteBill(Long id) {
        billRepository.deleteById(id);
    }
    
    public List<Bill> getBillsByCustomerType(String customerType) {
        return billRepository.findByCustomerType(customerType);
    }
    
    private void calculateTotals(Bill bill) {
        BigDecimal subtotal = BigDecimal.ZERO;
        
        for (Item item : bill.getItems()) {
            BigDecimal itemSubtotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            item.setSubtotal(itemSubtotal);
            subtotal = subtotal.add(itemSubtotal);
        }
        
        // GST Calculation (9% CGST + 9% SGST = 18% total GST)
        BigDecimal cgst = subtotal.multiply(BigDecimal.valueOf(0.09));
        BigDecimal sgst = subtotal.multiply(BigDecimal.valueOf(0.09));
        BigDecimal discount = bill.getDiscount() != null ? bill.getDiscount() : BigDecimal.ZERO;
        BigDecimal total = subtotal.add(cgst).add(sgst).subtract(discount);
        BigDecimal paidAmount = bill.getPaidAmount() != null ? bill.getPaidAmount() : BigDecimal.ZERO;
        
        bill.setSubtotal(subtotal);
        bill.setCgst(cgst);
        bill.setSgst(sgst);
        bill.setTotal(total);
        bill.setBalanceDue(total.subtract(paidAmount));
    }
    
    /**
     * Calculate GST for a specific product based on customer type
     * GST rate varies: Customer (18%), Retailer (5%), Wholesaler (0%)
     */
    public BigDecimal calculateGSTForCustomerType(BigDecimal subtotal, String customerType) {
        if (customerType == null) {
            return subtotal.multiply(BigDecimal.valueOf(0.18)); // Default 18%
        }
        
        return switch (customerType.toUpperCase()) {
            case "CUSTOMER" -> subtotal.multiply(BigDecimal.valueOf(0.18)); // 18% GST
            case "RETAILER" -> subtotal.multiply(BigDecimal.valueOf(0.05)); // 5% GST
            case "WHOLESALER" -> BigDecimal.ZERO; // 0% GST (B2B)
            default -> subtotal.multiply(BigDecimal.valueOf(0.18));
        };
    }
}
