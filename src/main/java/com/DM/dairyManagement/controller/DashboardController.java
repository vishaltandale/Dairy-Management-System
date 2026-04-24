package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.model.Bill;
import com.DM.dairyManagement.model.User;
import com.DM.dairyManagement.service.BillService;
import com.DM.dairyManagement.service.ProductService;
import com.DM.dairyManagement.service.StockService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {
    
    @Autowired
    private BillService billService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private StockService stockService;
    
    @GetMapping
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        
        // Get statistics
        List<Bill> allBills = billService.getAllBills();
        long totalBills = allBills.size();
        
        // Today's bills
        List<Bill> todayBills = billService.getBillsByDateRange(LocalDate.now(), LocalDate.now());
        model.addAttribute("todayBills", todayBills.size());
        
        // Total bills
        model.addAttribute("totalBills", totalBills);
        
        // Recent bills
        model.addAttribute("recentBills", allBills.stream().limit(10).toList());
        
        // Products count
        model.addAttribute("totalProducts", productService.getAllProducts().size());
        
        // Low stock items
        model.addAttribute("lowStockCount", stockService.getLowStockItems(10).size());
        
        model.addAttribute("user", user);
        return "dashboard";
    }
}
