package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.model.Bill;
import com.DM.dairyManagement.model.Item;
import com.DM.dairyManagement.model.User;
import com.DM.dairyManagement.service.BillService;
import com.DM.dairyManagement.service.ProductService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/bills")
public class BillController {
    
    @Autowired
    private BillService billService;
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public String listBills(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("bills", billService.getAllBills());
        model.addAttribute("user", user);
        return "listBill";
    }
    
    @GetMapping("/new")
    public String showCreateBillForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("bill", new Bill());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("user", user);
        return "createBill";
    }
    
    @PostMapping
    public String createBill(@ModelAttribute Bill bill,
                            @RequestParam(required = false) String[] productNames,
                            @RequestParam(required = false) Integer[] quantities,
                            @RequestParam(required = false) BigDecimal[] prices,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        
        // Create items from form data
        List<Item> items = new ArrayList<>();
        if (productNames != null) {
            for (int i = 0; i < productNames.length; i++) {
                Item item = new Item();
                item.setProductName(productNames[i]);
                item.setQuantity(quantities != null && i < quantities.length ? quantities[i] : 0);
                item.setPrice(prices != null && i < prices.length ? prices[i] : BigDecimal.ZERO);
                items.add(item);
            }
        }
        
        bill.setItems(items);
        
        try {
            billService.createBill(bill, user);
            redirectAttributes.addFlashAttribute("success", "Bill created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to create bill: " + e.getMessage());
        }
        
        return "redirect:/bills";
    }
    
    @GetMapping("/{id}")
    public String viewBill(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) {
            return "redirect:/login";
        }
        
        Bill bill = billService.getBillById(id);
        model.addAttribute("bill", bill);
        model.addAttribute("user", user);
        return "viewBill";
    }
    
    @PostMapping("/delete/{id}")
    public String deleteBill(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null || user.getRole() != User.Role.OWNER) {
            redirectAttributes.addFlashAttribute("error", "You don't have permission to delete bills");
            return "redirect:/bills";
        }
        
        billService.deleteBill(id);
        redirectAttributes.addFlashAttribute("success", "Bill deleted successfully!");
        return "redirect:/bills";
    }
}
