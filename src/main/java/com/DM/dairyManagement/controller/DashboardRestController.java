package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.dto.ApiResponse;
import com.DM.dairyManagement.model.User;
import com.DM.dairyManagement.service.AnalyticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard Analytics", description = "Dashboard analytics and business intelligence endpoints")
public class DashboardRestController {
    
    @Autowired
    private AnalyticsService analyticsService;
    
    /**
     * Get comprehensive dashboard summary
     */
    @GetMapping("/summary")
    @Operation(summary = "Get dashboard summary", description = "Returns comprehensive dashboard analytics including revenue, bills, customers, products, and stock alerts")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getDashboardSummary(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Please login first"));
        }
        
        Map<String, Object> summary = analyticsService.getDashboardSummary();
        return ResponseEntity.ok(ApiResponse.success(summary));
    }
    
    /**
     * Get revenue trend data for charts
     */
    @GetMapping("/revenue-trend")
    @Operation(summary = "Get revenue trend", description = "Returns revenue data grouped by date for the specified period")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getRevenueTrend(
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            HttpSession session) {
        
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Please login first"));
        }
        
        LocalDate start = startDate != null ? LocalDate.parse(startDate) : LocalDate.now().minusDays(30);
        LocalDate end = endDate != null ? LocalDate.parse(endDate) : LocalDate.now();
        
        Map<String, Object> data = Map.of(
            "trend", analyticsService.getRevenueTrend(start, end)
        );
        return ResponseEntity.ok(ApiResponse.success(data));
    }
    
    /**
     * Get top selling products
     */
    @GetMapping("/top-products")
    @Operation(summary = "Get top products", description = "Returns top selling products by quantity")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTopProducts(
            @RequestParam(defaultValue = "10") int limit,
            HttpSession session) {
        
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Please login first"));
        }
        
        Map<String, Object> data = Map.of(
            "products", analyticsService.getTopProducts(limit)
        );
        return ResponseEntity.ok(ApiResponse.success(data));
    }
    
    /**
     * Get customer analytics
     */
    @GetMapping("/customers")
    @Operation(summary = "Get customer analytics", description = "Returns customer distribution and analytics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCustomerAnalytics(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Please login first"));
        }
        
        Map<String, Object> analytics = analyticsService.getCustomerAnalytics();
        return ResponseEntity.ok(ApiResponse.success(analytics));
    }
    
    /**
     * Get payment analytics
     */
    @GetMapping("/payments")
    @Operation(summary = "Get payment analytics", description = "Returns payment collection and pending analytics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPaymentAnalytics(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Please login first"));
        }
        
        Map<String, Object> analytics = analyticsService.getPaymentAnalytics();
        return ResponseEntity.ok(ApiResponse.success(analytics));
    }
    
    /**
     * Get product analytics
     */
    @GetMapping("/products")
    @Operation(summary = "Get product analytics", description = "Returns product and stock analytics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getProductAnalytics(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.error("Please login first"));
        }
        
        Map<String, Object> analytics = analyticsService.getProductAnalytics();
        return ResponseEntity.ok(ApiResponse.success(analytics));
    }
}
