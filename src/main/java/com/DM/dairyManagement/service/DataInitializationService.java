package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.*;
import com.DM.dairyManagement.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DataInitializationService implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(DataInitializationService.class);
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private OtherExpenseRepository expenseRepository;
    
    @Autowired
    private StockRepository stockRepository;
    
    @Value("${app.admin.email:admin@gmail.com}")
    private String adminEmail;
    
    @Value("${app.admin.password:pass}")
    private String adminPassword;
    
    @Override
    public void run(String... args) {
        initializeAdminUser();
        initializeSampleData();
    }
    
    private void initializeAdminUser() {
        if (!userRepository.existsByEmail(adminEmail)) {
            logger.info("Creating admin user with email: {}", adminEmail);
            
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRole(User.Role.OWNER);
            admin.setActive(true);
            
            userRepository.save(admin);
            
            logger.info("Admin user created successfully!");
        } else {
            logger.info("Admin user already exists");
        }
        
        // Create sample employee user for testing RBAC
        String employeeEmail = "employee@gmail.com";
        if (!userRepository.existsByEmail(employeeEmail)) {
            logger.info("Creating sample employee user");
            
            User employee = new User();
            employee.setName("Sample Employee");
            employee.setEmail(employeeEmail);
            employee.setPassword(passwordEncoder.encode("employee123"));
            employee.setRole(User.Role.EMPLOYEE);
            employee.setActive(true);
            employee.setMobile("9876543210");
            
            userRepository.save(employee);
            logger.info("Sample employee user created with email: {}", employeeEmail);
        }
        
        // Create sample manager user for testing RBAC
        String managerEmail = "manager@gmail.com";
        if (!userRepository.existsByEmail(managerEmail)) {
            logger.info("Creating sample manager user");
            
            User manager = new User();
            manager.setName("Sample Manager");
            manager.setEmail(managerEmail);
            manager.setPassword(passwordEncoder.encode("manager123"));
            manager.setRole(User.Role.MANAGER);
            manager.setActive(true);
            manager.setMobile("9876543211");
            
            userRepository.save(manager);
            logger.info("Sample manager user created with email: {}", managerEmail);
        }
        
        // Create sample accountant user for testing RBAC
        String accountantEmail = "accountant@gmail.com";
        if (!userRepository.existsByEmail(accountantEmail)) {
            logger.info("Creating sample accountant user");
            
            User accountant = new User();
            accountant.setName("Sample Accountant");
            accountant.setEmail(accountantEmail);
            accountant.setPassword(passwordEncoder.encode("accountant123"));
            accountant.setRole(User.Role.ACCOUNTANT);
            accountant.setActive(true);
            accountant.setMobile("9876543212");
            
            userRepository.save(accountant);
            logger.info("Sample accountant user created with email: {}", accountantEmail);
        }
    }
    
    private void initializeSampleData() {
        if (productRepository.count() == 0) {
            logger.info("Creating sample data...");
            
            // Create sample products
            Product milk = createProduct("Fresh Milk (1L)", "Fresh dairy milk", 60, 45, 50, 55);
            Product curd = createProduct("Curd (500g)", "Fresh homemade curd", 40, 30, 35, 38);
            Product paneer = createProduct("Paneer (250g)", "Fresh cottage cheese", 90, 70, 75, 80);
            Product ghee = createProduct("Pure Ghee (1L)", "Pure desi ghee", 550, 450, 480, 500);
            Product butter = createProduct("Butter (500g)", "Fresh butter", 280, 220, 240, 260);
            
            // Create stock for products
            createStock(milk, 150, 50);
            createStock(curd, 80, 30);
            createStock(paneer, 45, 20);
            createStock(ghee, 120, 40);
            createStock(butter, 65, 25);
            
            // Create sample bills
            createSampleBill("Rajesh Kumar", "9876543210", "Customer", milk, 10, 60, 500);
            createSampleBill("Priya Sharma", "9876543211", "Retailer", curd, 20, 35, 600);
            createSampleBill("Amit Patel", "9876543212", "Customer", paneer, 5, 90, 400);
            createSampleBill("Sneha Reddy", "9876543213", "Wholesaler", ghee, 15, 480, 6500);
            createSampleBill("Vikram Singh", "9876543214", "Customer", butter, 8, 280, 2000);
            
            // Create sample employees
            createEmployee("Rajesh Kumar", "rajesh@milkmate.com", "9876543220", "Manager", 35000);
            createEmployee("Priya Sharma", "priya@milkmate.com", "9876543221", "Employee", 25000);
            createEmployee("Amit Patel", "amit@milkmate.com", "9876543222", "Accountant", 30000);
            
            // Create sample expenses
            createExpense("Electricity Bill", "Utilities", 5000, LocalDate.now().minusDays(5));
            createExpense("Transport", "Logistics", 3000, LocalDate.now().minusDays(3));
            createExpense("Packaging", "Supplies", 2000, LocalDate.now().minusDays(2));
            createExpense("Maintenance", "Equipment", 1500, LocalDate.now().minusDays(1));
            
            logger.info("Sample data created successfully!");
        }
    }
    
    private Product createProduct(String name, String description, double customerPrice, double companyPrice, double retailerPrice, double wholesalerPrice) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setCustomerPrice(customerPrice);
        product.setCompanyPrice(companyPrice);
        product.setRetailerPrice(retailerPrice);
        product.setWholesalerPrice(wholesalerPrice);
        return productRepository.save(product);
    }
    
    private void createStock(Product product, double quantity, double reorderLevel) {
        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setQuantity(quantity);
        stock.setReorderLevel(reorderLevel);
        stockRepository.save(stock);
    }
    
    private void createSampleBill(String fullName, String mobile, String customerType, Product product, int quantity, double price, double paidAmount) {
        Bill bill = new Bill();
        bill.setBillNo(billRepository.count() + 1001L);
        bill.setFullName(fullName);
        bill.setMobileNumber(mobile);
        bill.setCustomerType(customerType);
        bill.setDate(LocalDate.now());
        
        double subtotal = quantity * price;
        double cgst = subtotal * 0.09;
        double sgst = subtotal * 0.09;
        double total = subtotal + cgst + sgst;
        double balanceDue = total - paidAmount;
        
        bill.setSubtotal(BigDecimal.valueOf(subtotal));
        bill.setCgst(BigDecimal.valueOf(cgst));
        bill.setSgst(BigDecimal.valueOf(sgst));
        bill.setTotal(BigDecimal.valueOf(total));
        bill.setPaidAmount(BigDecimal.valueOf(paidAmount));
        bill.setBalanceDue(BigDecimal.valueOf(balanceDue));
        bill.setDiscount(BigDecimal.ZERO);
        
        bill = billRepository.save(bill);
        
        // Create bill item (Item is saved via cascade from Bill)
        Item item = new Item();
        item.setBill(bill);
        item.setProductName(product.getName());
        item.setQuantity(quantity);
        item.setPrice(BigDecimal.valueOf(price));
        item.setSubtotal(BigDecimal.valueOf(quantity * price));
        
        bill.getItems().add(item);
        billRepository.save(bill);
    }
    
    private void createEmployee(String name, String email, String mobile, String designation, double monthlySalary) {
        Employee employee = new Employee();
        employee.setName(name);
        employee.setEmail(email);
        employee.setMobile(mobile);
        employee.setDesignation(designation);
        employee.setMonthlySalary(monthlySalary);
        employee.setAdvance(0);
        employeeRepository.save(employee);
    }
    
    private void createExpense(String description, String category, double amount, LocalDate date) {
        OtherExpense expense = new OtherExpense();
        expense.setDate(date);
        expense.setRemarks(description + " - " + category);
        
        // Distribute amount across fields based on category
        if (category.equals("Utilities")) {
            expense.setLightBill(amount);
        } else if (category.equals("Logistics")) {
            expense.setDiesel(amount);
        } else {
            expense.setOtherExpense(amount);
        }
        
        expenseRepository.save(expense);
    }
}
