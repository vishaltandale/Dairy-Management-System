package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByBillId(Long billId);
    Page<Payment> findByBillId(Long billId, Pageable pageable);
    List<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);
    List<Payment> findAllByOrderByPaymentDateDesc();
    Page<Payment> findAll(Pageable pageable);
    
    // Analytics queries
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalPayments(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(p) FROM Payment p WHERE p.paymentDate BETWEEN :startDate AND :endDate")
    Long getPaymentCount(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
