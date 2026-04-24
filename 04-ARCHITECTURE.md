# 🏗️ MilkMate System Architecture

Technical architecture documentation for the MilkMate Dairy Management System, including system design, component relationships, data flow, and deployment architecture.

---

## 📋 Table of Contents
1. [System Overview](#-system-overview)
2. [Architecture Style](#-architecture-style)
3. [Layered Architecture](#-layered-architecture)
4. [Component Diagram](#-component-diagram)
5. [Technology Stack](#-technology-stack)
6. [Database Architecture](#-database-architecture)
7. [Security Architecture](#-security-architecture)
8. [Data Flow](#-data-flow)
9. [Deployment Architecture](#-deployment-architecture)
10. [Design Patterns](#-design-patterns)
11. [API Architecture](#-api-architecture)
12. [Frontend Architecture (Planned)](#-frontend-architecture-planned)
13. [Scalability Considerations](#-scalability-considerations)

---

## 🌐 System Overview

MilkMate is a **comprehensive dairy business management system** built using a **monolithic architecture** with clear separation of concerns. The system follows the **Model-View-Controller (MVC)** pattern with a **layered architecture** approach.

### Key Characteristics
- **Architecture Type**: Monolithic (planned migration to microservices in v2.0)
- **Pattern**: MVC (Model-View-Controller)
- **Communication**: Synchronous HTTP requests
- **Database**: Relational (MySQL/H2)
- **Authentication**: Session-based (planned JWT migration)

### System Capabilities
```
┌─────────────────────────────────────────────────────┐
│                MilkMate System                       │
├─────────────────────────────────────────────────────┤
│  • Bill Management (Multi-item, GST-compliant)     │
│  • Inventory & Stock Tracking                       │
│  • Customer Management (Retailers/Wholesalers)      │
│  • Payment Processing & Tracking                    │
│  • Expense Management                               │
│  • Employee Management with RBAC                    │
│  • Sales Analytics & Reports                        │
│  • Email Notifications                              │
│  • Data Export (Excel, PDF, CSV)                    │
└─────────────────────────────────────────────────────┘
```

---

## 🏛️ Architecture Style

### Current Architecture: Layered Monolith

```
┌──────────────────────────────────────────────────────┐
│              Presentation Layer                       │
│         (Thymeleaf Templates + Bootstrap)             │
└──────────────────┬───────────────────────────────────┘
                   │ HTTP Requests/Responses
┌──────────────────▼───────────────────────────────────┐
│              Controller Layer                         │
│    (REST Controllers + Web Controllers)               │
└──────────────────┬───────────────────────────────────┘
                   │ Method Calls
┌──────────────────▼───────────────────────────────────┐
│              Service Layer                            │
│         (Business Logic + Validation)                 │
└──────────────────┬───────────────────────────────────┘
                   │ Repository Methods
┌──────────────────▼───────────────────────────────────┐
│              Repository Layer                         │
│         (Data Access + JPA/Hibernate)                 │
└──────────────────┬───────────────────────────────────┘
                   │ SQL Queries
┌──────────────────▼───────────────────────────────────┐
│              Database Layer                           │
│            (MySQL / H2 Database)                      │
└──────────────────────────────────────────────────────┘
```

### Future Architecture: Microservices (v2.0)

```
┌─────────────────────────────────────────────────────┐
│                 API Gateway                           │
│            (Spring Cloud Gateway)                     │
└────┬──────┬──────┬──────┬──────┬──────┬────┬────────┘
     │      │      │      │      │      │      │
     ▼      ▼      ▼      ▼      ▼      ▼      ▼
   ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐ ┌────┐
   │Auth│ │Bill│ │Stock│ │Pay │ │Cust│ │Expn│ │Rpt │
   │Svc │ │Svc │ │Svc  │ │Svc │ │Svc │ │Svc │ │Svc │
   └────┘ └────┘ └────┘ └────┘ └────┘ └────┘ └────┘
     │      │      │      │      │      │      │
     └──────┴──────┴──────┴──────┴──────┴──────┘
                          │
                ┌─────────▼─────────┐
                │   Message Queue    │
                │  (RabbitMQ/Kafka)  │
                └───────────────────┘
```

---

## 🏗️ Layered Architecture

### 1. Presentation Layer

**Technology**: Thymeleaf + Bootstrap 5  
**Purpose**: User interface and client interaction

#### Components:
- **Thymeleaf Templates** (`.html` files in `resources/templates/`)
- **Static Resources** (CSS, JS, Images in `resources/static/`)
- **Bootstrap Components** (Forms, Tables, Modals, Cards)
- **Chart.js** (Data visualization)

#### Structure:
```
resources/
├── templates/
│   ├── login.html
│   ├── dashboard.html
│   ├── createBill.html
│   ├── listBill.html
│   ├── companies/
│   ├── products/
│   └── ...
└── static/
    ├── css/
    ├── js/
    └── images/
```

#### Responsibilities:
- Render HTML pages
- Handle user input forms
- Display data from backend
- Client-side validation
- Responsive design

---

### 2. Controller Layer

**Technology**: Spring MVC Controllers  
**Purpose**: Handle HTTP requests and route to appropriate services

#### Controllers:
```
controller/
├── BillController.java              # Bill CRUD operations
├── CompanyController.java           # Company management
├── DashboardController.java         # Dashboard analytics
├── EmployeeExpenseController.java   # Employee expenses
├── FileController.java              # File uploads
├── ForgotPasswordController.java    # Password reset
├── HomeController.java              # Home page routing
├── MasterController.java            # Master data management
├── OtherExpenseController.java      # Other expenses
├── PaymentController.java           # Payment processing
├── ProductController.java           # Product management
├── RetailerController.java          # Retailer management
├── SellController.java              # Sales tracking
├── StockController.java             # Stock management
├── TotalExpenseController.java      # Expense consolidation
├── UnitController.java              # Unit management
├── UserController.java              # User authentication
└── WholesalerController.java        # Wholesaler management
```

#### Example Controller:
```java
@Controller
@RequestMapping("/bills")
public class BillController {
    
    @Autowired
    private BillService billService;
    
    @Autowired
    private AuthorizationService authorizationService;
    
    @GetMapping
    public String listBills(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        
        if (!authorizationService.hasPermission(user, "VIEW_BILL")) {
            return "redirect:/unauthorized";
        }
        
        model.addAttribute("bills", billService.getAllBills());
        return "listBill";
    }
    
    @PostMapping
    public String createBill(@Valid Bill bill, 
                            BindingResult result,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        // Validation and business logic
    }
}
```

#### Responsibilities:
- Receive HTTP requests
- Validate input data
- Call service layer methods
- Return responses (views or JSON)
- Handle exceptions
- Manage sessions

---

### 3. Service Layer

**Technology**: Spring Services  
**Purpose**: Business logic and transaction management

#### Services:
```
service/
├── BillService.java                 # Bill business logic
├── CompanyService.java              # Company operations
├── DataInitializationService.java   # Demo data setup
├── ExpenseService.java              # Expense calculations
├── PaymentService.java              # Payment processing
├── ProductService.java              # Product operations
├── RetailerService.java             # Retailer management
├── SellService.java                 # Sales logic
├── StockService.java                # Inventory management
├── UnitService.java                 # Unit conversions
├── UserService.java                 # User authentication
├── WholesalerService.java           # Wholesaler operations
└── AuthorizationService.java        # RBAC permissions
```

#### Example Service:
```java
@Service
@Transactional
public class BillService {
    
    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private StockService stockService;
    
    @Autowired
    private AuditLogService auditLogService;
    
    public Bill createBill(Bill bill, User user) {
        // Generate bill number
        bill.setBillNo(generateBillNumber());
        
        // Calculate totals
        calculateTotals(bill);
        
        // Deduct stock
        stockService.deductStock(bill.getItem(), bill.getQty());
        
        // Save bill
        Bill savedBill = billRepository.save(bill);
        
        // Log action
        auditLogService.logAction(user, "CREATE_BILL", 
            "Bill", savedBill.getId(), "Created bill #" + savedBill.getBillNo());
        
        return savedBill;
    }
    
    private void calculateTotals(Bill bill) {
        BigDecimal subtotal = bill.getPrice().multiply(new BigDecimal(bill.getQty()));
        BigDecimal cgst = subtotal.multiply(new BigDecimal("0.09"));
        BigDecimal sgst = subtotal.multiply(new BigDecimal("0.09"));
        BigDecimal total = subtotal.add(cgst).add(sgst).subtract(bill.getDiscount());
        
        bill.setSubtotal(subtotal);
        bill.setCgst(cgst);
        bill.setSgst(sgst);
        bill.setTotal(total);
        bill.setBalanceDue(total.subtract(bill.getPaidAmount()));
    }
}
```

#### Responsibilities:
- Implement business rules
- Transaction management
- Data validation
- Call multiple repositories
- Complex calculations
- Security checks
- Audit logging

---

### 4. Repository Layer

**Technology**: Spring Data JPA  
**Purpose**: Data access and database operations

#### Repositories:
```
repository/
├── BillRepository.java
├── CompanyRepository.java
├── EmployeeRepository.java
├── MasterRepository.java
├── OtherExpenseRepository.java
├── PaymentHistoryRepository.java
├── PaymentRepository.java
├── ProductRepository.java
├── RetailerRepository.java
├── SellRepository.java
├── StockRepository.java
├── UnitRepository.java
├── UserRepository.java
└── WholesalerRepository.java
```

#### Example Repository:
```java
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    
    // Derived query methods
    List<Bill> findByCustomerType(String customerType);
    List<Bill> findByDateBetween(LocalDate start, LocalDate end);
    Optional<Bill> findByBillNo(Long billNo);
    
    // Custom JPQL queries
    @Query("SELECT b FROM Bill b WHERE b.fullName LIKE %:name%")
    List<Bill> searchByCustomerName(@Param("name") String name);
    
    @Query("SELECT COUNT(b) FROM Bill b WHERE b.date = :date")
    long countBillsByDate(@Param("date") LocalDate date);
    
    @Query("SELECT SUM(b.total) FROM Bill b WHERE b.date BETWEEN :start AND :end")
    BigDecimal getTotalSales(@Param("start") LocalDate start, 
                            @Param("end") LocalDate end);
}
```

#### Responsibilities:
- CRUD operations
- Custom queries
- Database indexing
- Pagination
- Data aggregation

---

### 5. Model Layer (Entities)

**Technology**: JPA/Hibernate Entities  
**Purpose**: Database schema representation

#### Entities:
```
model/
├── Bill.java                        # Bill records
├── Company.java                     # Company information
├── Employee.java                    # Employee data
├── Item.java                        # Bill line items
├── Master.java                      # Master data
├── OtherExpense.java                # Expense records
├── Payment.java                     # Payment transactions
├── PaymentHistory.java              # Payment history
├── Product.java                     # Product catalog
├── Retailer.java                    # Retailer customers
├── Sell.java                        # Sales records
├── Stock.java                       # Inventory levels
├── Unit.java                        # Measurement units
├── User.java                        # User accounts
└── Wholesaler.java                  # Wholesaler customers
```

#### Example Entity:
```java
@Entity
@Table(name = "bills")
public class Bill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private Long billNo;
    
    private String fullName;
    private String mobileNumber;
    private LocalDate date;
    
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;
    
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private List<BillItem> items = new ArrayList<>();
    
    private BigDecimal subtotal;
    private BigDecimal cgst;
    private BigDecimal sgst;
    private BigDecimal discount;
    private BigDecimal total;
    private BigDecimal paidAmount;
    private BigDecimal balanceDue;
    
    // Getters and Setters
}
```

---

## 🔧 Component Diagram

```
┌──────────────────────────────────────────────────────────────┐
│                        Client Browser                         │
└──────────────────────┬───────────────────────────────────────┘
                       │ HTTP/HTTPS
┌──────────────────────▼───────────────────────────────────────┐
│                   Spring Boot Application                     │
│                                                                │
│  ┌────────────────────────────────────────────────────────┐  │
│  │              Embedded Tomcat Server                     │  │
│  └───────────────────┬────────────────────────────────────┘  │
│                      │                                        │
│  ┌───────────────────▼────────────────────────────────────┐  │
│  │              DispatcherServlet                          │  │
│  └───────────────────┬────────────────────────────────────┘  │
│                      │                                        │
│  ┌───────────────────▼────────────────────────────────────┐  │
│  │              Controllers (17)                           │  │
│  │  • BillController    • ProductController                │  │
│  │  • PaymentController • UserController                   │  │
│  │  • DashboardController • etc.                           │  │
│  └───────────────────┬────────────────────────────────────┘  │
│                      │                                        │
│  ┌───────────────────▼────────────────────────────────────┐  │
│  │              Services (13)                              │  │
│  │  • BillService       • ProductService                   │  │
│  │  • PaymentService    • UserService                      │  │
│  │  • AuthorizationService                                 │  │
│  └───────────────────┬────────────────────────────────────┘  │
│                      │                                        │
│  ┌───────────────────▼────────────────────────────────────┐  │
│  │              Repositories (15)                          │  │
│  │  • BillRepository    • ProductRepository                │  │
│  │  • UserRepository    • etc.                             │  │
│  └───────────────────┬────────────────────────────────────┘  │
│                      │                                        │
│  ┌───────────────────▼────────────────────────────────────┐  │
│  │              Hibernate ORM                              │  │
│  └───────────────────┬────────────────────────────────────┘  │
└──────────────────────┼───────────────────────────────────────┘
                       │ JDBC
┌──────────────────────▼───────────────────────────────────────┐
│                    Database                                   │
│              ┌─────────────────┐                              │
│              │   MySQL / H2    │                              │
│              │   (15 tables)   │                              │
│              └─────────────────┘                              │
└──────────────────────────────────────────────────────────────┘

External Services:
┌──────────────┐  ┌──────────────┐  ┌──────────────┐
│ Gmail SMTP   │  │ File System  │  │ Email Service│
│ (Email)      │  │ (Uploads)    │  │ (Notifications)│
└──────────────┘  └──────────────┘  └──────────────┘
```

---

## 🛠️ Technology Stack

### Backend Technologies

| Category | Technology | Version | Purpose |
|----------|-----------|---------|---------|
| **Framework** | Spring Boot | 3.4.2 | Application framework |
| **Language** | Java | 17+ | Programming language |
| **Build Tool** | Maven | 3.6+ | Dependency management |
| **ORM** | Hibernate | 6.6.5 | Object-relational mapping |
| **Database** | MySQL / H2 | 8.0 / 2.3 | Data persistence |
| **Security** | Spring Security Crypto | 6.4.2 | Password encryption |
| **Validation** | Jakarta Validation | 3.0 | Input validation |
| **Email** | Spring Mail | 3.4.2 | Email notifications |
| **API Docs** | SpringDoc OpenAPI | 2.3.0 | Swagger documentation |

### Frontend Technologies

| Category | Technology | Version | Purpose |
|----------|-----------|---------|---------|
| **Template Engine** | Thymeleaf | 3.1.2 | Server-side rendering |
| **CSS Framework** | Bootstrap | 5.3.0 | UI components |
| **Icons** | Font Awesome | 6.0 | Icon library |
| **Charts** | Chart.js | 4.4 | Data visualization |
| **JavaScript** | Vanilla JS | ES6 | Client-side logic |

### Development Tools

| Tool | Purpose |
|------|---------|
| **Spring Boot DevTools** | Hot reload during development |
| **H2 Console** | In-memory database browser |
| **JUnit 5** | Unit testing |
| **Mockito** | Mocking framework |
| **Lombok** | Reduce boilerplate code |

---

## 🗄️ Database Architecture

### Entity Relationship Diagram

```
┌──────────┐
│   User   │
│──────────│
│ id       │
│ name     │
│ email    │
│ role     │
│ active   │
└────┬─────┘
     │ 1
     │
     │ N
┌────▼──────────┐      ┌──────────────┐      ┌──────────────┐
│   Company     │─────>│  Product     │<─────│    Unit      │
│───────────    │  1 N  │──────────────│ N 1  │──────────────│
│ id            │      │ id           │      │ id           │
│ name          │      │ name         │      │ name         │
│ contact_person│      │ company_id   │      │ kg           │
│ contact_no    │      │ unit_id      │      │ ltr          │
└───────────────┘      │ prices       │      └──────────────┘
                       │ hsn_code     │
                       └──────┬───────┘
                              │ 1
                              │
                    ┌─────────┴─────────┐
                    │                   │
              ┌─────▼─────┐      ┌─────▼──────┐
              │   Stock   │      │ Bill       │
              │───────────│      │────────────│
              │ id        │      │ id         │
              │ product_id│      │ bill_no    │
              │ quantity  │      │ full_name  │
              │ reorder_pt│      │ date       │
              └───────────┘      │ customer   │
                                 │ items (N)  │
                                 │ totals     │
                                 └─────┬──────┘
                                       │ 1
                                       │
                                 ┌─────▼──────┐
                                 │  Payment   │
                                 │────────────│
                                 │ id         │
                                 │ bill_id    │
                                 │ amount     │
                                 │ method     │
                                 │ date       │
                                 └────────────┘

┌──────────────┐      ┌──────────────┐
│  Retailer    │      │ Wholesaler   │
│──────────────│      │──────────────│
│ id           │      │ id           │
│ name         │      │ name         │
│ mobile       │      │ mobile       │
│ company_id   │      │ company_id   │
│ credit_limit │      │ credit_limit │
└──────────────┘      └──────────────┘

┌──────────────┐      ┌──────────────┐
│  Employee    │      │OtherExpense  │
│──────────────│      │──────────────│
│ id           │      │ id           │
│ name         │      │ date         │
│ salary       │      │ shop_rent    │
│ advance      │      │ light_bill   │
└──────────────┘      │ diesel       │
                      └──────────────┘

┌──────────────┐
│  AuditLog    │
│──────────────│
│ id           │
│ user_email   │
│ action       │
│ entity_type  │
│ timestamp    │
└──────────────┘
```

### Database Tables (15 Total)

| Table | Records | Purpose |
|-------|---------|---------|
| `users` | User accounts | Authentication and authorization |
| `companies` | Supplier companies | Company management |
| `products` | Product catalog | Product information |
| `units` | Measurement units | Unit conversions |
| `stocks` | Inventory levels | Stock tracking |
| `bills` | Invoice records | Billing system |
| `bill_items` | Line items | Multi-item support |
| `payments` | Payment records | Transaction tracking |
| `payment_history` | Payment logs | Payment history |
| `retailers` | Retail customers | Customer management |
| `wholesalers` | Wholesale customers | Customer management |
| `employees` | Staff records | Employee management |
| `other_expenses` | Expense records | Expense tracking |
| `audit_logs` | Action logs | Security auditing |
| `masters` | Master data | Configuration data |

---

## 🔐 Security Architecture

### Authentication Flow

```
┌──────────┐
│  User    │
└────┬─────┘
     │ 1. Login Request (email + password)
     ▼
┌──────────────────┐
│ UserController   │
│ /login POST      │
└────┬─────────────┘
     │ 2. Find user by email
     ▼
┌──────────────────┐
│ UserService      │
│ getUserByEmail() │
└────┬─────────────┘
     │ 3. Return User entity
     ▼
┌──────────────────┐
│ Password Check   │
│ BCrypt.matches() │
└────┬─────────────┘
     │ 4. Password valid?
     ├─ No → Return error
     │
     │ Yes
     ▼
┌──────────────────┐
│ Create Session   │
│ session.setAttribute()│
│ - loggedInUser   │
│ - userRole       │
└────┬─────────────┘
     │ 5. Session ID in cookie
     ▼
┌──────────────────┐
│ Redirect to      │
│ /dashboard       │
└──────────────────┘
```

### Authorization Flow (RBAC)

```
┌──────────────────┐
│ HTTP Request     │
└────┬─────────────┘
     │
     ▼
┌──────────────────┐
│ Check Session    │
│ loggedInUser?    │
└────┬─────────────┘
     │ No → Redirect to /login
     │
     │ Yes
     ▼
┌──────────────────┐
│ Get User Role    │
│ user.getRole()   │
└────┬─────────────┘
     │
     ▼
┌──────────────────┐
│ AuthorizationSvc │
│ hasPermission()  │
└────┬─────────────┘
     │
     ├─ OWNER → All permissions
     ├─ MANAGER → Limited admin
     ├─ EMPLOYEE → Restricted
     ├─ ACCOUNTANT → Financial only
     │
     ▼
┌──────────────────┐
│ Allow/Deny       │
│ Action           │
└──────────────────┘
```

### Security Layers

```
┌─────────────────────────────────────────┐
│ Layer 1: Input Validation               │
│ • Jakarta Validation annotations        │
│ • @Valid, @NotBlank, @Email             │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│ Layer 2: Authentication                 │
│ • BCrypt password hashing               │
│ • Session management                    │
│ • Session timeout (30 min)              │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│ Layer 3: Authorization (RBAC)           │
│ • Role-based permissions                │
│ • Method-level security checks          │
│ • UI element visibility control         │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│ Layer 4: Audit Logging                  │
│ • All critical actions logged           │
│ • User, action, timestamp, IP           │
│ • Tamper-evident logs                   │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│ Layer 5: SQL Injection Prevention       │
│ • JPA parameterized queries             │
│ • No raw SQL concatenation              │
│ • Input sanitization                    │
└──────────────┬──────────────────────────┘
               │
┌──────────────▼──────────────────────────┐
│ Layer 6: XSS Prevention                 │
│ • Thymeleaf auto-escaping               │
│ • Content Security Policy               │
│ • HTTP-only cookies                     │
└─────────────────────────────────────────┘
```

---

## 🔄 Data Flow

### Bill Creation Flow

```
User Action Flow:
┌──────┐     ┌──────────┐     ┌───────────┐     ┌─────────┐
│ User │────>│ Browser  │────>│Controller │────>│ Service │
└──────┘     └──────────┘     └───────────┘     └────┬────┘
                                                     │
Database Operations:                                 │
┌──────────┐     ┌──────────┐     ┌──────────┐      │
│ Stock DB │<────│ Bill DB  │<────│Product DB│<─────┘
└──────────┘     └──────────┘     └──────────┘

Detailed Flow:
1. User fills bill form (customer info + items)
2. Browser submits POST request to /bills
3. BillController receives request
4. Validates input data (@Valid)
5. BillService.createBill() called
6. Check stock availability
7. Calculate totals (subtotal, tax, discount)
8. Save bill to database
9. Deduct stock quantities
10. Create audit log entry
11. Return success response
12. Redirect to bill list page
13. Show success message
```

### Payment Processing Flow

```
┌─────────┐
│  User   │
└────┬────┘
     │ Select bill and enter payment amount
     ▼
┌──────────────┐
│ Payment Form │
└────┬─────────┘
     │ POST /payments
     ▼
┌──────────────────┐
│PaymentController │
└────┬─────────────┘
     │ Validate payment amount <= balance due
     ▼
┌──────────────────┐
│ PaymentService   │
│ recordPayment()  │
└────┬─────────────┘
     │ 1. Create Payment record
     │ 2. Update bill.paidAmount
     │ 3. Update bill.balanceDue
     │ 4. Add to payment_history
     ▼
┌──────────────┐
│  Database    │
│  (2 tables)  │
└────┬─────────┘
     │ Success
     ▼
┌──────────────────┐
│ Success Message  │
│ Updated balance  │
└──────────────────┘
```

---

## 🚀 Deployment Architecture

### Development Environment

```
┌─────────────────────────────────────┐
│         Developer Machine            │
│                                      │
│  ┌──────────────────────────────┐   │
│  │  Spring Boot Application     │   │
│  │  (Embedded Tomcat)           │   │
│  │  Port: 8080                  │   │
│  └──────────────┬───────────────┘   │
│                 │                    │
│  ┌──────────────▼───────────────┐   │
│  │  H2 In-Memory Database       │   │
│  │  (No external setup needed)  │   │
│  └──────────────────────────────┘   │
│                                      │
│  Browser: http://localhost:8080     │
└─────────────────────────────────────┘
```

### Production Environment (Single Server)

```
┌───────────────────────────────────────────────┐
│            Production Server                   │
│            (VPS / Cloud VM)                    │
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │  Nginx (Reverse Proxy + SSL)             │ │
│  │  Port: 443 (HTTPS)                       │ │
│  └──────────────┬───────────────────────────┘ │
│                 │                              │
│  ┌──────────────▼───────────────────────────┐ │
│  │  Spring Boot Application                 │ │
│  │  java -jar milkmate.jar                  │ │
│  │  Port: 8080                              │ │
│  └──────────────┬───────────────────────────┘ │
│                 │                              │
│  ┌──────────────▼───────────────────────────┐ │
│  │  MySQL Database                          │ │
│  │  Port: 3306                              │ │
│  │  Data: /var/lib/mysql                    │ │
│  └──────────────────────────────────────────┘ │
│                                                │
│  ┌──────────────────────────────────────────┐ │
│  │  File Storage                            │ │
│  │  /opt/milkmate/uploads                   │ │
│  └──────────────────────────────────────────┘ │
└───────────────────────────────────────────────┘

External Services:
┌──────────────┐     ┌──────────────┐
│ Gmail SMTP   │     │ Backup S3    │
│ (Email)      │     │ (Backups)    │
└──────────────┘     └──────────────┘
```

### Production Environment (Docker)

```
┌──────────────────────────────────────────────┐
│             Docker Host                       │
│                                               │
│  ┌─────────────────────────────────────────┐ │
│  │  Nginx Container                         │ │
│  │  - SSL Termination                       │ │
│  │  - Load Balancing                        │ │
│  │  - Port: 443                             │ │
│  └──────────────┬──────────────────────────┘ │
│                 │                             │
│  ┌──────────────▼──────────────────────────┐ │
│  │  Spring Boot Container                   │ │
│  │  - milkmate:latest                       │ │
│  │  - Port: 8080                            │ │
│  │  - Environment Variables                 │ │
│  └──────────────┬──────────────────────────┘ │
│                 │                             │
│  ┌──────────────▼──────────────────────────┐ │
│  │  MySQL Container                         │ │
│  │  - mysql:8.0                             │ │
│  │  - Port: 3306                            │ │
│  │  - Volume: mysql-data                    │ │
│  └─────────────────────────────────────────┘ │
│                                               │
│  Docker Networks:                             │
│  - milkmate-network (internal)                │
│                                               │
│  Docker Volumes:                              │
│  - mysql-data (persistent)                    │
│  - uploads (persistent)                       │
└───────────────────────────────────────────────┘
```

---

## 🎨 Design Patterns

### Patterns Used

| Pattern | Implementation | Purpose |
|---------|---------------|---------|
| **MVC** | Controllers, Services, Repositories | Separation of concerns |
| **Repository** | Spring Data JPA | Data access abstraction |
| **Service Layer** | @Service classes | Business logic encapsulation |
| **Dependency Injection** | @Autowired | Loose coupling |
| **Singleton** | Spring Beans | Single instance management |
| **Factory** | Bean creation | Object creation |
| **Strategy** | AuthorizationService | Role-based behavior |
| **Observer** | Event listeners | Event handling |
| **Template Method** | BaseController | Common controller logic |

### Example: Strategy Pattern (RBAC)

```java
// Strategy Interface
public interface PermissionStrategy {
    boolean hasPermission(User user, String action);
}

// Concrete Strategies
@Component
public class OwnerPermissionStrategy implements PermissionStrategy {
    public boolean hasPermission(User user, String action) {
        return true; // Owner has all permissions
    }
}

@Component
public class EmployeePermissionStrategy implements PermissionStrategy {
    public boolean hasPermission(User user, String action) {
        return action.equals("CREATE_BILL") || 
               action.equals("VIEW_BILL");
    }
}

// Context
@Service
public class AuthorizationService {
    @Autowired
    private Map<Role, PermissionStrategy> strategies;
    
    public boolean hasPermission(User user, String action) {
        return strategies.get(user.getRole()).hasPermission(user, action);
    }
}
```

---

## 🔌 API Architecture

### REST API Design (Planned)

```
API Versioning:
/api/v1/bills
/api/v1/products
/api/v1/payments

Standard Response Format:
{
  "success": true,
  "data": { ... },
  "message": "Operation successful",
  "timestamp": "2024-01-15T10:30:00Z"
}

Error Response Format:
{
  "success": false,
  "error": {
    "code": "VALIDATION_ERROR",
    "message": "Invalid input data",
    "details": [
      {
        "field": "email",
        "message": "Invalid email format"
      }
    ]
  },
  "timestamp": "2024-01-15T10:30:00Z"
}
```

### Endpoint Organization

```
Authentication:
POST   /api/v1/auth/login
POST   /api/v1/auth/register
POST   /api/v1/auth/logout
GET    /api/v1/auth/me

Bills:
GET    /api/v1/bills              # List with pagination
GET    /api/v1/bills/{id}         # Get by ID
POST   /api/v1/bills              # Create
PUT    /api/v1/bills/{id}         # Update
DELETE /api/v1/bills/{id}         # Delete (Owner only)
GET    /api/v1/bills/{id}/print   # Print PDF

Products:
GET    /api/v1/products
POST   /api/v1/products
PUT    /api/v1/products/{id}
DELETE /api/v1/products/{id}

Payments:
GET    /api/v1/payments
POST   /api/v1/payments
GET    /api/v1/payments/bill/{billId}

Analytics:
GET    /api/v1/analytics/sales
GET    /api/v1/analytics/top-products
GET    /api/v1/analytics/customer-distribution
```

---

## 💻 Frontend Architecture (Planned)

### React + TypeScript Architecture

```
milkmate-frontend/
├── src/
│   ├── components/
│   │   ├── ui/              # shadcn/ui primitives
│   │   │   ├── button.tsx
│   │   │   ├── input.tsx
│   │   │   └── ...
│   │   ├── layout/          # Layout components
│   │   │   ├── Header.tsx
│   │   │   ├── Sidebar.tsx
│   │   │   └── MainLayout.tsx
│   │   └── common/          # Shared components
│   │       ├── DataTable.tsx
│   │       ├── StatCard.tsx
│   │       └── ...
│   ├── features/            # Feature-based modules
│   │   ├── auth/
│   │   ├── bills/
│   │   ├── products/
│   │   └── ...
│   ├── hooks/               # Custom React hooks
│   ├── stores/              # Zustand stores
│   ├── services/            # API services
│   ├── types/               # TypeScript types
│   └── utils/               # Utility functions
```

### State Management

```
┌──────────────┐
│   Zustand    │
│   Stores     │
│──────────────│
│ authStore    │ ← User authentication state
│ billStore    │ ← Bill data and filters
│ uiStore      │ ← UI state (sidebar, modals)
└──────────────┘

┌──────────────┐
│  React Query │
│──────────────│
│ Caching      │ ← Automatic API response caching
│ Refetching   │ ← Background data refresh
│ Mutations    │ ← Create/Update/Delete operations
└──────────────┘
```

---

## 📈 Scalability Considerations

### Current Limitations

1. **Monolithic Architecture**
   - Single deployment unit
   - Hard to scale individual components
   - Technology lock-in

2. **Session-Based Auth**
   - Not suitable for distributed systems
   - Requires sticky sessions
   - Limited mobile support

3. **Synchronous Processing**
   - Email sending blocks request
   - No background job processing
   - Poor user experience for long operations

### Future Scalability Improvements

1. **Microservices Migration**
   - Split into domain services
   - Independent deployment
   - Technology diversity

2. **Message Queue**
   - Asynchronous email sending
   - Background job processing
   - Event-driven architecture

3. **Caching Layer**
   - Redis for session storage
   - API response caching
   - Database query caching

4. **Load Balancing**
   - Multiple application instances
   - Horizontal scaling
   - High availability

5. **CDN Integration**
   - Static asset delivery
   - Image optimization
   - Global performance

---

## 📊 Monitoring & Observability

### Application Monitoring

```
┌──────────────────────────────────────┐
│         Monitoring Stack              │
│                                      │
│  ┌────────────────────────────────┐  │
│  │  Spring Boot Actuator          │  │
│  │  - /health                     │  │
│  │  - /metrics                    │  │
│  │  - /info                       │  │
│  └────────────────────────────────┘  │
│                                      │
│  ┌────────────────────────────────┐  │
│  │  Logging (SLF4J + Logback)     │  │
│  │  - Application logs            │  │
│  │  - Audit logs                  │  │
│  │  - Error tracking              │  │
│  └────────────────────────────────┘  │
│                                      │
│  ┌────────────────────────────────┐  │
│  │  Database Monitoring           │  │
│  │  - Query performance           │  │
│  │  - Connection pool             │  │
│  │  - Slow query log              │  │
│  └────────────────────────────────┘  │
└──────────────────────────────────────┘
```

---

<div align="center">

**Architecture Documentation Complete**

[Installation Guide](INSTALLATION_GUIDE.md) • [Complete Documentation](COMPLETE_DOCUMENTATION.md) • [Development Guide](DEVELOPMENT_GUIDE.md)

</div>
