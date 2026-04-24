package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByBillNo(Long billNo);
    List<Bill> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Bill> findByCustomerType(String customerType);
    List<Bill> findAllByOrderByDateDesc();
    Page<Bill> findAllByOrderByDateDesc(Pageable pageable);
    long countByDate(LocalDate date);
    List<Bill> findByFullNameContainingIgnoreCase(String name);
    Page<Bill> findByFullNameContainingIgnoreCaseOrBillNoContainingIgnoreCase(
        String fullName, String billNo, Pageable pageable);
    Page<Bill> findByCustomerType(String customerType, Pageable pageable);
    Page<Bill> findByFullNameContainingIgnoreCase(String name, Pageable pageable);
    
    // Analytics queries
    @Query("SELECT DATE(b.date) as date, SUM(b.total) as revenue, COUNT(b) as billCount " +
           "FROM Bill b WHERE b.date BETWEEN :startDate AND :endDate " +
           "GROUP BY DATE(b.date) ORDER BY DATE(b.date)")
    List<Map<String, Object>> getRevenueTrend(@Param("startDate") LocalDate startDate, 
                                               @Param("endDate") LocalDate endDate);
    
    @Query("SELECT SUM(b.total) FROM Bill b WHERE b.date BETWEEN :startDate AND :endDate")
    BigDecimal getTotalRevenue(@Param("startDate") LocalDate startDate, 
                               @Param("endDate") LocalDate endDate);
    
    @Query("SELECT b.customerType, COUNT(b) as count, SUM(b.total) as total " +
           "FROM Bill b GROUP BY b.customerType")
    List<Map<String, Object>> getCustomerTypeDistribution();
    
    @Query("SELECT SUM(b.balanceDue) FROM Bill b")
    BigDecimal getTotalPendingPayments();
    
    @Query("SELECT SUM(b.paidAmount) FROM Bill b")
    BigDecimal getTotalCollectedPayments();
    
    long countByCustomerType(String customerType);
}
