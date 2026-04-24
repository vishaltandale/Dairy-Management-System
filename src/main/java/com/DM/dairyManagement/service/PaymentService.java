package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Payment;
import com.DM.dairyManagement.model.PaymentHistory;
import com.DM.dairyManagement.repository.PaymentRepository;
import com.DM.dairyManagement.repository.PaymentHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private PaymentHistoryRepository paymentHistoryRepository;
    
    public Payment recordPayment(Payment payment) {
        // Save payment
        Payment savedPayment = paymentRepository.save(payment);
        
        // Add to payment history
        PaymentHistory history = new PaymentHistory();
        history.setBill(payment.getBill());
        history.setAmount(payment.getAmount());
        history.setPaymentMethod(payment.getPaymentMethod());
        history.setReference(payment.getReference());
        history.setPaymentDate(LocalDateTime.now());
        paymentHistoryRepository.save(history);
        
        // Update bill's paid amount and balance
        updateBillPaymentStatus(payment);
        
        return savedPayment;
    }
    
    public Page<Payment> getPaymentsByBillId(Long billId, Pageable pageable) {
        return paymentRepository.findByBillId(billId, pageable);
    }
    
    public Page<Payment> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }
    
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }
    
    public Payment savePayment(Payment payment) {
        return recordPayment(payment);
    }
    
    public Payment updatePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
    
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
    
    public List<Payment> getPaymentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return paymentRepository.findByPaymentDateBetween(startDate, endDate);
    }
    
    private void updateBillPaymentStatus(Payment payment) {
        // This would update the associated bill
        // Implementation depends on your exact requirements
    }
}
