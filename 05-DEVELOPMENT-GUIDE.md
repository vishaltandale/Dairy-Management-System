# 💻 MilkMate Development Guide

Comprehensive guide for developers working on the MilkMate Dairy Management System. Covers coding standards, workflows, testing, and contribution guidelines.

---

## 📋 Table of Contents
1. [Development Environment Setup](#-development-environment-setup)
2. [Project Structure](#-project-structure)
3. [Coding Standards](#-coding-standards)
4. [Adding New Features](#-adding-new-features)
5. [Database Changes](#-database-changes)
6. [Testing Guidelines](#-testing-guidelines)
7. [Git Workflow](#-git-workflow)
8. [Code Review Process](#-code-review-process)
9. [Common Development Tasks](#-common-development-tasks)
10. [Debugging Tips](#-debugging-tips)
11. [Performance Optimization](#-performance-optimization)
12. [Deployment Process](#-deployment-process)

---

## 🛠️ Development Environment Setup

### Prerequisites

Ensure you have installed all required software. See [Installation Guide](INSTALLATION_GUIDE.md) for detailed instructions.

**Quick Checklist:**
- [ ] Java JDK 17+
- [ ] Maven 3.6+ (or use Maven Wrapper)
- [ ] Git 2.30+
- [ ] IDE (IntelliJ IDEA recommended)
- [ ] MySQL 8.0+ (optional, H2 for development)

### IDE Setup

#### IntelliJ IDEA (Recommended)

**Step 1: Import Project**
```
1. File → Open → Select Milkmate folder
2. Select "pom.xml" when prompted
3. Wait for Maven to download dependencies
4. Enable auto-import for dependencies
```

**Step 2: Configure Run Configuration**
```
1. Run → Edit Configurations
2. Click + → Spring Boot
3. Name: MilkMate-Dev
4. Main class: com.DM.dairyManagement.DairyManagementApplication
5. Environment variables: Load from .env file
6. Apply → OK
```

**Step 3: Enable Hot Reload**
```
1. Settings → Build, Execution, Deployment → Compiler
   ☑ Build project automatically

2. Help → Find Action → "Registry"
   ☑ compiler.automake.allow.when.app.running

3. Install plugin: "Spring Boot Assistant"
```

#### VS Code

**Required Extensions:**
- Extension Pack for Java
- Spring Boot Extension Pack
- Maven for Java
- Project Manager for Java

**Setup:**
```
1. File → Open Folder → Select Milkmate
2. Wait for Java language server initialization
3. Open DairyManagementApplication.java
4. Click Run button above main() method
```

---

## 📁 Project Structure

### Complete Directory Layout

```
Milkmate/
├── .env                           # Environment variables (create from .env.example)
├── .env.example                   # Example environment configuration
├── .gitignore                     # Git ignore rules
├── pom.xml                        # Maven build configuration
├── mvnw / mvnw.cmd               # Maven Wrapper scripts
│
├── src/
│   ├── main/
│   │   ├── java/com/DM/dairyManagement/
│   │   │   ├── DairyManagementApplication.java    # Main entry point
│   │   │   │
│   │   │   ├── configure/         # Configuration classes
│   │   │   │   ├── AppConfig.java
│   │   │   │   └── MailConfig.java
│   │   │   │
│   │   │   ├── controller/        # Web controllers (HTTP handlers)
│   │   │   │   ├── BillController.java
│   │   │   │   ├── CompanyController.java
│   │   │   │   ├── DashboardController.java
│   │   │   │   ├── EmployeeExpenseController.java
│   │   │   │   ├── FileController.java
│   │   │   │   ├── ForgotPasswordController.java
│   │   │   │   ├── HomeController.java
│   │   │   │   ├── MasterController.java
│   │   │   │   ├── OtherExpenseController.java
│   │   │   │   ├── PaymentController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   ├── RetailerController.java
│   │   │   │   ├── SellController.java
│   │   │   │   ├── StockController.java
│   │   │   │   ├── TotalExpenseController.java
│   │   │   │   ├── UnitController.java
│   │   │   │   ├── UserController.java
│   │   │   │   └── WholesalerController.java
│   │   │   │
│   │   │   ├── model/             # JPA entities (Database tables)
│   │   │   │   ├── Bill.java
│   │   │   │   ├── Company.java
│   │   │   │   ├── Employee.java
│   │   │   │   ├── Item.java
│   │   │   │   ├── Master.java
│   │   │   │   ├── OtherExpense.java
│   │   │   │   ├── Payment.java
│   │   │   │   ├── PaymentHistory.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── Retailer.java
│   │   │   │   ├── Sell.java
│   │   │   │   ├── Stock.java
│   │   │   │   ├── Unit.java
│   │   │   │   ├── User.java
│   │   │   │   └── Wholesaler.java
│   │   │   │
│   │   │   ├── repository/        # Data access layer (JPA)
│   │   │   │   ├── BillRepository.java
│   │   │   │   ├── CompanyRepository.java
│   │   │   │   ├── EmployeeRepository.java
│   │   │   │   ├── MasterRepository.java
│   │   │   │   ├── OtherExpenseRepository.java
│   │   │   │   ├── PaymentHistoryRepository.java
│   │   │   │   ├── PaymentRepository.java
│   │   │   │   ├── ProductRepository.java
│   │   │   │   ├── RetailerRepository.java
│   │   │   │   ├── SellRepository.java
│   │   │   │   ├── StockRepository.java
│   │   │   │   ├── UnitRepository.java
│   │   │   │   ├── UserRepository.java
│   │   │   │   └── WholesalerRepository.java
│   │   │   │
│   │   │   └── service/           # Business logic layer
│   │   │       ├── BillService.java
│   │   │       ├── CompanyService.java
│   │   │       ├── DataInitializationService.java
│   │   │       ├── ExpenseService.java
│   │   │       ├── PaymentService.java
│   │   │       ├── ProductService.java
│   │   │       ├── RetailerService.java
│   │   │       ├── SellService.java
│   │   │       ├── StockService.java
│   │   │       ├── UnitService.java
│   │   │       ├── UserService.java
│   │   │       └── WholesalerService.java
│   │   │
│   │   └── resources/
│   │       ├── application.properties    # Spring Boot configuration
│   │       ├── static/                   # Static resources
│   │       │   ├── css/                  # Stylesheets
│   │       │   ├── js/                   # JavaScript files
│   │       │   └── images/              # Images and logos
│   │       └── templates/               # Thymeleaf HTML templates
│   │           ├── login.html
│   │           ├── signup.html
│   │           ├── dashboard.html
│   │           ├── createBill.html
│   │           ├── listBill.html
│   │           └── ... (40+ templates)
│   │
│   └── test/
│       └── java/com/DM/dairyManagement/
│           └── DairyManagementApplicationTests.java
│
└── target/                        # Build output (generated)
```

### Architecture Layers

For detailed architecture, see [Architecture Documentation](ARCHITECTURE.md).

**Layer Responsibilities:**

| Layer | Package | Purpose | Example |
|-------|---------|---------|---------|
| **Controller** | `controller/` | Handle HTTP requests | `BillController` |
| **Service** | `service/` | Business logic | `BillService` |
| **Repository** | `repository/` | Database access | `BillRepository` |
| **Model** | `model/` | Data entities | `Bill` entity |

---

## 📝 Coding Standards

### Java Code Standards

#### Naming Conventions

```java
// Classes: PascalCase
public class ProductService { }
public class BillController { }

// Methods: camelCase
public void calculateTotalAmount() { }
public List<Bill> getBillsByDate() { }

// Variables: camelCase
private String productName;
private int billCount;
private BigDecimal totalAmount;

// Constants: UPPER_SNAKE_CASE
public static final String DEFAULT_ADMIN_EMAIL = "admin@gmail.com";
public static final int MAX_LOGIN_ATTEMPTS = 5;

// Packages: lowercase
package com.DM.dairyManagement.service;
```

#### Class Structure

```java
package com.DM.dairyManagement.service;

// 1. Imports (organized by type)
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.DM.dairyManagement.model.Bill;
import com.DM.dairyManagement.repository.BillRepository;

// 2. Class declaration
@Service
@Transactional
public class BillService {
    
    // 3. Constants
    private static final BigDecimal GST_RATE = new BigDecimal("0.18");
    
    // 4. Dependencies (injection)
    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private StockService stockService;
    
    // 5. Public methods
    public Bill createBill(Bill bill) {
        // Implementation
    }
    
    // 6. Private helper methods
    private void calculateTotals(Bill bill) {
        // Implementation
    }
}
```

#### JPA Entity Standards

```java
package com.DM.dairyManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bills")
public class Bill {
    
    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Required fields with validation
    @Column(unique = true, nullable = false)
    private Long billNo;
    
    @NotBlank(message = "Customer name is required")
    @Size(min = 2, max = 100)
    private String fullName;
    
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid mobile number")
    private String mobileNumber;
    
    // Use proper data types
    private LocalDate date;              // ✅ Not String
    private BigDecimal price;            // ✅ Not double
    
    // Enumerations
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;
    
    // Relationships
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> items = new ArrayList<>();
    
    // Constructors
    public Bill() {
    }
    
    public Bill(String fullName, String mobileNumber) {
        this.fullName = fullName;
        this.mobileNumber = mobileNumber;
        this.date = LocalDate.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    // ... other getters/setters
    
    // Helper methods
    public void addItem(BillItem item) {
        items.add(item);
        item.setBill(this);
    }
    
    // Business logic
    public BigDecimal getBalanceDue() {
        return total.subtract(paidAmount);
    }
}
```

#### Controller Standards

```java
package com.DM.dairyManagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/bills")
public class BillController {
    
    @Autowired
    private BillService billService;
    
    @Autowired
    private AuthorizationService authorizationService;
    
    // LIST - GET request
    @GetMapping
    public String listBills(Model model, HttpSession session) {
        User user = getUserFromSession(session);
        
        if (!authorizationService.hasPermission(user, "VIEW_BILL")) {
            return "redirect:/unauthorized";
        }
        
        model.addAttribute("bills", billService.getAllBills());
        model.addAttribute("userRole", user.getRole());
        return "listBill";
    }
    
    // CREATE FORM - GET request
    @GetMapping("/new")
    public String showCreateForm(Model model, HttpSession session) {
        if (!hasPermission(session, "CREATE_BILL")) {
            return "redirect:/unauthorized";
        }
        
        model.addAttribute("bill", new Bill());
        model.addAttribute("products", productService.getAllProducts());
        return "createBill";
    }
    
    // CREATE - POST request
    @PostMapping
    public String createBill(@Valid Bill bill,
                            BindingResult result,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "createBill";
        }
        
        try {
            User user = getUserFromSession(session);
            Bill savedBill = billService.createBill(bill, user);
            
            redirectAttributes.addFlashAttribute("success", 
                "Bill #" + savedBill.getBillNo() + " created successfully!");
            return "redirect:/bills";
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", 
                "Failed to create bill: " + e.getMessage());
            return "redirect:/bills/new";
        }
    }
    
    // UPDATE - POST request
    @PostMapping("/{id}")
    public String updateBill(@PathVariable Long id,
                            @Valid Bill bill,
                            BindingResult result,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        // Similar pattern to create
    }
    
    // DELETE - POST request (prevent accidental deletes)
    @PostMapping("/delete/{id}")
    public String deleteBill(@PathVariable Long id,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        User user = getUserFromSession(session);
        
        if (!authorizationService.hasPermission(user, "DELETE_BILL")) {
            redirectAttributes.addFlashAttribute("error", 
                "❌ You don't have permission to delete bills.");
            return "redirect:/bills";
        }
        
        try {
            billService.deleteBill(id, user);
            redirectAttributes.addFlashAttribute("success", "Bill deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to delete bill: " + e.getMessage());
        }
        
        return "redirect:/bills";
    }
    
    // Helper method
    private User getUserFromSession(HttpSession session) {
        return (User) session.getAttribute("loggedInUser");
    }
    
    private boolean hasPermission(HttpSession session, String action) {
        User user = getUserFromSession(session);
        return user != null && authorizationService.hasPermission(user, action);
    }
}
```

#### Service Standards

```java
package com.DM.dairyManagement.service;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
    private AuditLogService auditLogService;
    
    /**
     * Creates a new bill with validation and stock deduction.
     * 
     * @param bill The bill to create
     * @param user The user creating the bill
     * @return The saved bill with generated ID and bill number
     * @throws IllegalArgumentException if bill is invalid
     * @throws InsufficientStockException if stock is not available
     */
    public Bill createBill(Bill bill, User user) {
        // 1. Validate input
        validateBill(bill);
        
        // 2. Generate bill number
        bill.setBillNo(generateBillNumber());
        bill.setDate(LocalDate.now());
        
        // 3. Check stock availability
        checkStockAvailability(bill);
        
        // 4. Calculate totals
        calculateTotals(bill);
        
        // 5. Deduct stock
        stockService.deductStock(bill.getItems());
        
        // 6. Save bill
        Bill savedBill = billRepository.save(bill);
        
        // 7. Log action
        auditLogService.logAction(user, "CREATE_BILL", 
            "Bill", savedBill.getId(), "Created bill #" + savedBill.getBillNo());
        
        return savedBill;
    }
    
    /**
     * Retrieves all bills ordered by date (newest first).
     * 
     * @return List of all bills
     */
    @Transactional(readOnly = true)
    public List<Bill> getAllBills() {
        return billRepository.findAllByOrderByDateDesc();
    }
    
    /**
     * Retrieves a bill by its ID.
     * 
     * @param id The bill ID
     * @return The bill
     * @throws IllegalArgumentException if bill not found
     */
    @Transactional(readOnly = true)
    public Bill getBillById(Long id) {
        return billRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Bill not found with id: " + id));
    }
    
    // Private helper methods
    private void validateBill(Bill bill) {
        if (bill == null) {
            throw new IllegalArgumentException("Bill cannot be null");
        }
        if (bill.getItems() == null || bill.getItems().isEmpty()) {
            throw new IllegalArgumentException("Bill must have at least one item");
        }
    }
    
    private Long generateBillNumber() {
        LocalDate today = LocalDate.now();
        long count = billRepository.countByDate(today);
        return today.getYear() * 10000L + count + 1;
    }
    
    private void calculateTotals(Bill bill) {
        BigDecimal subtotal = bill.getItems().stream()
            .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal cgst = subtotal.multiply(new BigDecimal("0.09"));
        BigDecimal sgst = subtotal.multiply(new BigDecimal("0.09"));
        BigDecimal total = subtotal.add(cgst).add(sgst)
            .subtract(bill.getDiscount() != null ? bill.getDiscount() : BigDecimal.ZERO);
        
        bill.setSubtotal(subtotal);
        bill.setCgst(cgst);
        bill.setSgst(sgst);
        bill.setTotal(total);
        bill.setBalanceDue(total.subtract(bill.getPaidAmount()));
    }
}
```

### HTML/Thymeleaf Standards

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title th:text="${pageTitle}">MilkMate</title>
    
    <!-- CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link th:href="@{/css/custom.css}" rel="stylesheet">
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" th:href="@{/dashboard}">
                <img th:src="@{/images/logo.png}" alt="MilkMate" height="30">
            </a>
        </div>
    </nav>
    
    <!-- Main Content -->
    <div class="container mt-4">
        <!-- Success/Error Messages -->
        <div th:if="${success}" class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="fas fa-check-circle me-2"></i>
            <span th:text="${success}"></span>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        
        <div th:if="${error}" class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="fas fa-exclamation-circle me-2"></i>
            <span th:text="${error}"></span>
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
        
        <!-- Page Content -->
        <div class="row">
            <div class="col-md-12">
                <h1 class="mb-4">Page Title</h1>
                
                <!-- Forms with validation -->
                <form th:action="@{/bills}" th:object="${bill}" method="post">
                    <div class="mb-3">
                        <label for="fullName" class="form-label">Customer Name</label>
                        <input type="text" 
                               class="form-control" 
                               id="fullName" 
                               th:field="*{fullName}"
                               th:classappend="${#fields.hasErrors('fullName')} ? 'is-invalid' : ''"
                               required>
                        <div class="invalid-feedback" th:if="${#fields.hasErrors('fullName')}">
                            <span th:errors="*{fullName}"></span>
                        </div>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save me-2"></i>Save
                    </button>
                </form>
                
                <!-- Tables -->
                <table class="table table-striped table-hover">
                    <thead class="table-primary">
                        <tr>
                            <th>Bill #</th>
                            <th>Customer</th>
                            <th>Amount</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr th:each="bill : ${bills}">
                            <td th:text="${bill.billNo}"></td>
                            <td th:text="${bill.fullName}"></td>
                            <td th:text="${'₹' + bill.total}"></td>
                            <td>
                                <a th:href="@{/bills/{id}(id=${bill.id})}" class="btn btn-sm btn-info">
                                    <i class="fas fa-eye"></i>
                                </a>
                                <a th:if="${userRole == 'OWNER' or userRole == 'MANAGER'}"
                                   th:href="@{/bills/{id}/edit(id=${bill.id})}" 
                                   class="btn btn-sm btn-warning">
                                    <i class="fas fa-edit"></i>
                                </a>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    
    <!-- Scripts -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script th:src="@{/js/custom.js}"></script>
</body>
</html>
```

---

## 🚀 Adding New Features

### Step-by-Step Process

#### Example: Adding "Supplier Management" Feature

**Step 1: Create Entity**

```java
// model/Supplier.java
package com.DM.dairyManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "suppliers")
public class Supplier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Supplier name is required")
    private String name;
    
    @Email(message = "Invalid email format")
    private String email;
    
    private String mobile;
    private String address;
    private String gstin;
    
    // Getters and Setters
}
```

**Step 2: Create Repository**

```java
// repository/SupplierRepository.java
package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    
    List<Supplier> findByNameContainingIgnoreCase(String name);
    
    Supplier findByEmail(String email);
}
```

**Step 3: Create Service**

```java
// service/SupplierService.java
package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Supplier;
import com.DM.dairyManagement.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    
    @Autowired
    private SupplierRepository supplierRepository;
    
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
    
    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Supplier not found"));
    }
    
    public Supplier saveSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }
    
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
```

**Step 4: Create Controller**

```java
// controller/SupplierController.java
package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.model.Supplier;
import com.DM.dairyManagement.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {
    
    @Autowired
    private SupplierService supplierService;
    
    @GetMapping
    public String listSuppliers(Model model) {
        model.addAttribute("suppliers", supplierService.getAllSuppliers());
        return "suppliers/list";
    }
    
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "suppliers/form";
    }
    
    @PostMapping
    public String createSupplier(@ModelAttribute Supplier supplier) {
        supplierService.saveSupplier(supplier);
        return "redirect:/suppliers";
    }
}
```

**Step 5: Create Thymeleaf Templates**

```html
<!-- templates/suppliers/list.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Suppliers - MilkMate</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h1>Suppliers</h1>
        <a href="/suppliers/new" class="btn btn-primary mb-3">Add Supplier</a>
        
        <table class="table">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Mobile</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr th:each="supplier : ${suppliers}">
                    <td th:text="${supplier.name}"></td>
                    <td th:text="${supplier.email}"></td>
                    <td th:text="${supplier.mobile}"></td>
                    <td>
                        <a th:href="@{/suppliers/{id}/edit(id=${supplier.id})}" class="btn btn-sm btn-warning">Edit</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>
```

---

## 🗄️ Database Changes

### Adding New Table

**Step 1: Create Entity** (see above)

**Step 2: Update application.properties**

```properties
# Hibernate will auto-create table
spring.jpa.hibernate.ddl-auto=update
```

**Step 3: Run Application**

Table will be created automatically on startup.

### Modifying Existing Table

**Option 1: Automatic (Development)**

```properties
spring.jpa.hibernate.ddl-auto=update
```

**Option 2: Manual SQL (Production)**

```sql
-- Add new column
ALTER TABLE bills ADD COLUMN invoice_number VARCHAR(50);

-- Modify column type
ALTER TABLE bills MODIFY COLUMN total DECIMAL(10,2);

-- Add index
CREATE INDEX idx_bills_date ON bills(date);
```

For detailed database schema, see [Architecture Documentation](ARCHITECTURE.md#database-architecture).

---

## 🧪 Testing Guidelines

### Unit Testing

```java
// test/java/com/DM/dairyManagement/service/BillServiceTest.java
package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Bill;
import com.DM.dairyManagement.repository.BillRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BillServiceTest {
    
    @Autowired
    private BillService billService;
    
    @Autowired
    private BillRepository billRepository;
    
    @Test
    void testCreateBill() {
        // Given
        Bill bill = new Bill();
        bill.setFullName("Test Customer");
        bill.setMobileNumber("9876543210");
        
        // When
        Bill savedBill = billService.createBill(bill, testUser);
        
        // Then
        assertNotNull(savedBill.getId());
        assertNotNull(savedBill.getBillNo());
        assertEquals("Test Customer", savedBill.getFullName());
    }
    
    @Test
    void testGetBillById_NotFound() {
        // When & Then
        assertThrows(IllegalArgumentException.class, 
            () -> billService.getBillById(999L));
    }
}
```

### Running Tests

```bash
# Run all tests
mvn test

# Run specific test class
mvn -Dtest=BillServiceTest test

# Run with coverage
mvn clean test jacoco:report
```

---

## 🔄 Git Workflow

### Branch Strategy

```
main (production)
  ├── develop (development)
  │     ├── feature/bill-export
  │     ├── feature/gst-invoicing
  │     └── bugfix/stock-calculation
  └── hotfix/critical-security-fix
```

### Workflow Steps

**1. Start New Feature**
```bash
# Update develop branch
git checkout develop
git pull origin develop

# Create feature branch
git checkout -b feature/supplier-management
```

**2. Make Changes**
```bash
# Edit files
# ...

# Stage changes
git add .

# Commit with message
git commit -m "feat: add supplier management module"
```

**3. Push and Create PR**
```bash
# Push to remote
git push origin feature/supplier-management

# Create Pull Request on GitHub
# Request review from team members
```

**4. Code Review & Merge**
```bash
# After approval, merge to develop
git checkout develop
git merge feature/supplier-management
git push origin develop
```

### Commit Message Convention

```
feat: add new feature
fix: fix bug
docs: update documentation
style: code style changes
refactor: code refactoring
test: add tests
chore: maintenance tasks

Examples:
feat: add supplier management module
fix: correct stock calculation on bill deletion
docs: update installation guide
refactor: extract bill calculation to service method
```

---

## 🔍 Debugging Tips

### Enable Debug Logging

**application.properties:**
```properties
logging.level.com.DM.dairyManagement=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
```

### Common Debugging Scenarios

**Problem: Bill not saving**

```java
// Add logging
logger.debug("Creating bill: {}", bill);
logger.debug("Validation errors: {}", result.getAllErrors());

// Check in browser console
// Network tab → Check POST request payload
```

**Problem: Database query slow**

```java
// Enable SQL logging
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

// Check generated SQL in console
// Add indexes if needed
```

---

## 📦 Deployment Process

See [Installation Guide](INSTALLATION_GUIDE.md) for deployment steps.

**Quick Checklist:**
- [ ] Update `.env` with production values
- [ ] Set `spring.jpa.hibernate.ddl-auto=update`
- [ ] Disable H2 console
- [ ] Build with `mvn clean package -DskipTests`
- [ ] Deploy JAR file
- [ ] Verify application starts
- [ ] Test critical features
- [ ] Monitor logs for errors

---

<div align="center">

**Happy Coding! 🚀**

[Installation Guide](INSTALLATION_GUIDE.md) • [Architecture](ARCHITECTURE.md) • [User Manual](USER_MANUAL.md) • [Complete Documentation](COMPLETE_DOCUMENTATION.md) • [Contributing Guide](CONTRIBUTING.md)

</div>
