package com.DM.dairyManagement.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DM.dairyManagement.repository.BillRepository;
import com.DM.dairyManagement.repository.PaymentRepository;
import com.DM.dairyManagement.repository.ProductRepository;
import com.DM.dairyManagement.repository.RetailerRepository;
import com.DM.dairyManagement.repository.StockRepository;

@Service
public class AnalyticsService {
    
    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private RetailerRepository retailerRepository;
    
    @Autowired
    private StockRepository stockRepository;
    
    /**
     * Get revenue trend data for charts
     */
    public List<Map<String, Object>> getRevenueTrend(LocalDate startDate, LocalDate endDate) {
        return billRepository.getRevenueTrend(startDate, endDate);
    }
    
    /**
     * Get top selling products
     */
    public List<Map<String, Object>> getTopProducts(int limit) {
        return productRepository.getTopProductsByQuantity(limit);
    }
    
    /**
     * Get customer analytics
     */
    public Map<String, Object> getCustomerAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("totalCustomers", retailerRepository.count());
        analytics.put("customerTypeDistribution", billRepository.getCustomerTypeDistribution());
        return analytics;
    }
    
    /**
     * Get payment analytics
     */
    public Map<String, Object> getPaymentAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("totalCollected", billRepository.getTotalCollectedPayments());
        analytics.put("pendingAmount", billRepository.getTotalPendingPayments());
        return analytics;
    }
    
    /**
     * Get product performance analytics
     */
    public Map<String, Object> getProductAnalytics() {
        Map<String, Object> analytics = new HashMap<>();
        analytics.put("totalProducts", productRepository.count());
        analytics.put("lowStockProducts", stockRepository.countByQuantityLessThanEqual(10));
        return analytics;
    }
    
    /**
     * Get comprehensive dashboard summary
     */
    public Map<String, Object> getDashboardSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalRevenue", billRepository.getTotalRevenue(LocalDate.now().withDayOfMonth(1), LocalDate.now()));
        summary.put("totalBills", billRepository.count());
        summary.put("totalCustomers", retailerRepository.count());
        summary.put("totalProducts", productRepository.count());
        summary.put("lowStockCount", stockRepository.countByQuantityLessThanEqual(10));
        summary.put("paymentAnalytics", getPaymentAnalytics());
        summary.put("customerAnalytics", getCustomerAnalytics());
        summary.put("productAnalytics", getProductAnalytics());
        return summary;
    }
}
