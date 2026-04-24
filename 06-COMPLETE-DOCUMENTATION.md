# 🐄 MilkMate Dairy Management System - Complete Project Documentation

## 📋 Table of Contents
1. [Project Overview](#-project-overview)
2. [System Requirements](#-system-requirements)
3. [Installation Guide](#-installation-guide)
4. [Project Structure](#-project-structure)
5. [Technology Stack](#-technology-stack)
6. [UI/UX Design System](#-uiux-design-system)
7. [Frontend Architecture](#-frontend-architecture)
8. [Page Specifications](#-page-specifications)
9. [Database Setup](#-database-setup)
10. [Configuration](#-configuration)
11. [Running the Application](#-running-the-application)
12. [Core Features](#-core-features)
13. [Advanced Features (Production-Ready)](#-advanced-features-production-ready)
14. [Multi-Login & Role-Based Access Control](#-multi-login--role-based-access-control)
15. [API Endpoints](#-api-endpoints)
16. [Database Schema](#-database-schema)
17. [Development Guide](#-development-guide)
18. [Testing](#-testing)
19. [Deployment](#-deployment)
20. [Troubleshooting](#-troubleshooting)
21. [Modernization Guide](#-modernization-guide)

---

## 📖 Project Overview

**MilkMate** is a comprehensive Dairy Business Management System built with Spring Boot 3.4.2. It provides end-to-end solutions for dairy businesses to manage their operations including inventory, billing, customer management, payments, and financial tracking.

### Key Features
- 📊 **Dashboard Analytics** - Real-time business metrics, sales trends, and insights
- 🧾 **Multi-Item Bill Management** - Create bills with multiple products, GST-compliant
- 📦 **Smart Inventory Tracking** - Monitor stock levels with automatic alerts and reorder points
- 💰 **Payment Management** - Track transactions, credit limits, and automated payment reminders
- 👥 **Customer Management** - Maintain retailer/wholesaler records with credit tracking
- 👨‍💼 **Employee Management** - Handle staff payroll, advances, and role-based permissions
- 📈 **Expense Tracking** - Monitor operational costs with category-wise breakdown
- 🔐 **Multi-Login with RBAC** - Owner, Manager, Employee, Accountant roles with granular permissions
- 🛡️ **Security & Audit** - Prevent unauthorized actions, comprehensive audit trail
- 📧 **Email Notifications** - Automated payment reminders and stock alerts
- 🖨️ **Professional Bill Printing** - Preview before print, GST-compliant invoices
- 📤 **Data Export** - Export to Excel, PDF, CSV for accounting and backups
- 🔍 **Advanced Search** - Multi-criteria filtering with date ranges and customer types
- 📊 **Sales Analytics** - Top products, customer analysis, revenue trends

### Demo Credentials

**Owner/Admin Account:**
- **Email**: `admin@gmail.com`
- **Password**: `pass`
- **Role**: OWNER (Full access to everything)

**Employee Account:**
- **Email**: `employee@gmail.com`
- **Password**: `employee123`
- **Role**: EMPLOYEE (Limited access - can only create bills, view stock)

---

## 💻 System Requirements

### Minimum Requirements
- **Java**: JDK 17 or higher (Project uses Java 23)
- **Maven**: 3.6+ (or use included Maven Wrapper)
- **Memory**: 512MB RAM minimum, 1GB recommended
- **Disk Space**: 500MB for project and dependencies
- **Operating System**: Windows, macOS, or Linux

### Optional (For Production)
- **MySQL**: 8.0+ (H2 database used for development)
- **Git**: For version control
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code (recommended)

---

## 🚀 Installation Guide

### Step 1: Get the Source Code

#### Option A: Clone from GitHub
```bash
git clone https://github.com/vishaltandale/Milkmate.git
cd Milkmate
```

#### Option B: Download ZIP
1. Go to: https://github.com/vishaltandale/Milkmate
2. Click "Code" → "Download ZIP"
3. Extract to your preferred location
4. Open terminal in the extracted folder

### Step 2: Verify Java Installation

```bash
# Check Java version
java -version

# Expected output: java version "17" or higher
```

**If Java is not installed:**
- Download from: https://adoptium.net/
- Install JDK 17 or higher
- Set JAVA_HOME environment variable

### Step 3: Verify Maven (Optional)

```bash
# Check Maven version
mvn -version

# If Maven is not installed, you can use the Maven Wrapper (mvnw) included in the project
```

### Step 4: Configure Environment

Create a `.env` file in the project root:

```properties
# Database Configuration (H2 - Development)
DB_URL=jdbc:h2:mem:dairy_management_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
DB_DRIVER=org.h2.Driver
DB_USERNAME=sa
DB_PASSWORD=

# Database Configuration (MySQL - Production)
# DB_URL=jdbc:mysql://localhost:3306/dairy_management?createDatabaseIfNotExist=true
# DB_DRIVER=com.mysql.cj.jdbc.Driver
# DB_USERNAME=your_mysql_username
# DB_PASSWORD=your_mysql_password

# Email Configuration (Gmail SMTP)
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password

# Admin Credentials
ADMIN_EMAIL=admin@gmail.com
ADMIN_PASSWORD=pass
```

### Step 5: Build the Project

```bash
# Using Maven (if installed)
mvn clean install

# OR using Maven Wrapper (recommended if Maven not installed)
# Windows
.\mvnw.cmd clean install

# Mac/Linux
./mvnw clean install
```

This will:
- Download all dependencies
- Compile the source code
- Run tests
- Package the application

---

## 📁 Project Structure

```
Milkmate/
├── .env                          # Environment configuration (create this)
├── .env.example                  # Example environment file
├── pom.xml                       # Maven build configuration
├── mvnw / mvnw.cmd              # Maven Wrapper scripts
│
├── src/
│   ├── main/
│   │   ├── java/com/DM/dairyManagement/
│   │   │   ├── DairyManagementApplication.java    # Main application entry point
│   │   │   │
│   │   │   ├── configure/        # Configuration classes
│   │   │   │   ├── AppConfig.java
│   │   │   │   └── MailConfig.java
│   │   │   │
│   │   │   ├── controller/       # Web controllers (HTTP request handlers)
│   │   │   │   ├── BillController.java            # Actually in DashboardController
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
│   │   │   ├── model/            # JPA Entity classes (Database tables)
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
│   │   │   ├── repository/       # Data Access Layer (JPA Repositories)
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
│   │   │   └── service/          # Business Logic Layer
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
│   │       ├── application.properties    # Application configuration
│   │       ├── static/                   # Static resources
│   │       │   └── images/              # Image files (logos, etc.)
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
└── target/                       # Build output (generated after build)
```

---

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.4.2
- **Language**: Java 23
- **Build Tool**: Maven 3.6+
- **ORM**: Spring Data JPA with Hibernate 6.6.5
- **Security**: Spring Security Crypto 6.4.2 (BCrypt)
- **Validation**: Spring Boot Starter Validation (Jakarta Validation)
- **Email**: Spring Boot Starter Mail
- **API Documentation**: SpringDoc OpenAPI 2.3.0 (Swagger)

### Database
- **Development**: H2 Database 2.3.232 (In-memory)
- **Production**: MySQL 8.0+ or PostgreSQL 15+ (mysql-connector-j)
- **Connection Pool**: HikariCP 5.1.0
- **Migration**: Flyway 10.6.0 (optional, for production)

### Frontend (Modern Architecture - UPGRADED)
- **Framework**: React 18.2+ with Vite 5.0 (replacing Thymeleaf)
- **Language**: TypeScript 5.3+ (type-safe development)
- **CSS Framework**: Tailwind CSS 3.4+ (utility-first, modern design)
- **UI Components**: shadcn/ui (accessible, customizable components)
- **State Management**: Zustand 4.4+ (lightweight, simple)
- **HTTP Client**: Axios 1.6+ with interceptors
- **Form Handling**: React Hook Form 7.49+ with Zod validation
- **Charts**: Recharts 2.10+ (React-native charting)
- **Icons**: Lucide React 0.300+ (modern, consistent icons)
- **Notifications**: Sonner 1.4+ (toast notifications)
- **Routing**: React Router DOM 6.21+
- **Date Handling**: date-fns 3.0+ (lightweight date utility)

### Legacy Frontend (Current - For Migration Reference)
- **Template Engine**: Thymeleaf (being replaced by React)
- **CSS Framework**: Bootstrap 5.3.0 (being replaced by Tailwind)
- **Icons**: Font Awesome 6.0.0-beta3 (being replaced by Lucide)
- **Charts**: Chart.js (being replaced by Recharts)
- **JavaScript**: Vanilla JS (being replaced by TypeScript)

### Development Tools
- **Hot Reload**: Spring Boot DevTools + Vite HMR
- **Testing Backend**: Spring Boot Starter Test (JUnit 5, Mockito)
- **Testing Frontend**: Vitest 1.1+, React Testing Library 14.1+
- **Code Quality**: ESLint 8.56+, Prettier 3.1+, Checkstyle
- **API Testing**: Postman / Insomnia
- **Version Control**: Git + GitHub

### Why These Changes?

**Problems with Current Stack:**
- ❌ Thymeleaf = server-side rendering, poor interactivity
- ❌ Bootstrap = heavy, generic design, hard to customize
- ❌ Vanilla JS = no type safety, hard to maintain
- ❌ No component reusability
- ❌ Poor mobile experience
- ❌ No modern state management

**Benefits of New Stack:**
- ✅ React = component-based, excellent UX, SPA experience
- ✅ TypeScript = type safety, better DX, fewer bugs
- ✅ Tailwind = rapid development, consistent design, small bundle
- ✅ Modern tooling = hot reload, fast builds, better DX
- ✅ Component library = reusable, accessible, tested
- ✅ Mobile-first = responsive by default
- ✅ Better performance = client-side rendering, code splitting

---

## 🎨 UI/UX Design System

This section defines the complete visual and interaction design system for MilkMate. Every component, page, and interaction follows these specifications to ensure consistency, accessibility, and a professional user experience.

### 1. Color Palette

#### Primary Colors
```css
/* Primary - Brand identity (Dairy Blue) */
--color-primary-50: #eff6ff
--color-primary-100: #dbeafe
--color-primary-200: #bfdbfe
--color-primary-300: #93c5fd
--color-primary-400: #60a5fa
--color-primary-500: #3b82f6  /* Main brand color */
--color-primary-600: #2563eb
--color-primary-700: #1d4ed8
--color-primary-800: #1e40af
--color-primary-900: #1e3a8a

/* Usage: */
/* Primary buttons: bg-primary-600 hover:bg-primary-700 */
/* Links: text-primary-600 hover:text-primary-700 */
/* Active states: bg-primary-50 border-primary-500 */
```

#### Secondary Colors
```css
/* Secondary - Accent (Milk White/Cream) */
--color-secondary-50: #fafaf9
--color-secondary-100: #f5f5f4
--color-secondary-200: #e7e5e4
--color-secondary-300: #d6d3d1
--color-secondary-400: #a8a29e
--color-secondary-500: #78716c
--color-secondary-600: #57534e
--color-secondary-700: #44403c
--color-secondary-800: #292524
--color-secondary-900: #1c1917

/* Usage: */
/* Backgrounds: bg-secondary-50 */
/* Borders: border-secondary-200 */
/* Text: text-secondary-600 (muted text) */
```

#### Semantic Colors
```css
/* Success - Positive actions, completed states */
--color-success-50: #f0fdf4
--color-success-500: #22c55e
--color-success-600: #16a34a
--color-success-700: #15803d

/* Warning - Caution, pending states */
--color-warning-50: #fefce8
--color-warning-500: #eab308
--color-warning-600: #ca8a04
--color-warning-700: #a16207

/* Error - Destructive actions, validation errors */
--color-error-50: #fef2f2
--color-error-500: #ef4444
--color-error-600: #dc2626
--color-error-700: #b91c1c

/* Info - Informational messages */
--color-info-50: #eff6ff
--color-info-500: #3b82f6
--color-info-600: #2563eb
--color-info-700: #1d4ed8

/* Usage: */
/* Success: bg-success-50 text-success-700 border-success-500 */
/* Warning: bg-warning-50 text-warning-700 border-warning-500 */
/* Error: bg-error-50 text-error-700 border-error-500 */
/* Info: bg-info-50 text-info-700 border-info-500 */
```

#### Neutral Colors (Grays)
```css
--color-gray-50: #f9fafb   /* Page backgrounds */
--color-gray-100: #f3f4f6  /* Card backgrounds */
--color-gray-200: #e5e7eb  /* Borders, dividers */
--color-gray-300: #d1d5db  /* Disabled states */
--color-gray-400: #9ca3af  /* Placeholder text */
--color-gray-500: #6b7280  /* Secondary text */
--color-gray-600: #4b5563  /* Body text */
--color-gray-700: #374151  /* Headings */
--color-gray-800: #1f2937  /* Strong text */
--color-gray-900: #111827  /* darkest */
```

---

### 2. Typography Scale

#### Font Family
```css
/* Primary Font - Inter (modern, highly legible) */
font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;

/* Monospace - For code, bill numbers */
font-family: 'JetBrains Mono', 'Fira Code', monospace;
```

#### Type Scale (Mobile-First)
```css
/* Display - Hero sections, welcome messages */
text-4xl (36px / 2.25rem) - font-bold - leading-tight
text-5xl (48px / 3rem) - font-bold - leading-tight

/* Headings */
h1: text-3xl (30px / 1.875rem) - font-bold - tracking-tight
h2: text-2xl (24px / 1.5rem) - font-semibold - tracking-tight
h3: text-xl (20px / 1.25rem) - font-semibold
text-lg (18px / 1.125rem) - font-medium

/* Body */
base: text-base (16px / 1rem) - font-normal - leading-relaxed
sm: text-sm (14px / 0.875rem) - font-normal
xs: text-xs (12px / 0.75rem) - font-normal

/* Special */
caption: text-xs - font-medium - uppercase - tracking-wider
overline: text-xs - font-medium - uppercase - tracking-widest
```

#### Font Weight Usage
```css
font-light (300)    - Rarely used, special cases
font-normal (400)   - Body text, descriptions
font-medium (500)   - Subheadings, labels, buttons
font-semibold (600) - Headings, important text
font-bold (700)     - Main headings, stats, emphasis
```

---

### 3. Spacing System

#### Base Unit: 4px (0.25rem)
```css
/* Tailwind spacing scale */
0: 0px
1: 4px (0.25rem)
2: 8px (0.5rem)
3: 12px (0.75rem)
4: 16px (1rem)
5: 20px (1.25rem)
6: 24px (1.5rem)
8: 32px (2rem)
10: 40px (2.5rem)
12: 48px (3rem)
16: 64px (4rem)
20: 80px (5rem)
24: 96px (6rem)
```

#### Component Spacing Rules
```css
/* Inside components (padding) */
- Buttons: px-4 py-2 (16px horizontal, 8px vertical)
- Cards: p-6 (24px all sides)
- Inputs: px-3 py-2 (12px horizontal, 8px vertical)
- Modals: p-6 or p-8

/* Between elements (margin/gap) */
- Form fields: space-y-4 (16px vertical)
- Card grid: gap-6 (24px)
- Section spacing: my-8 or my-12
- Page sections: mb-8

/* Layout spacing */
- Container padding: px-4 sm:px-6 lg:px-8
- Sidebar width: w-64 (256px)
- Header height: h-16 (64px)
```

---

### 4. Layout Grid System

#### Breakpoints (Mobile-First)
```css
sm: 640px   /* Tablets */
md: 768px   /* Small laptops */
lg: 1024px  /* Desktops */
xl: 1280px  /* Large screens */
2xl: 1536px /* Extra large */
```

#### Grid Usage
```css
/* Dashboard stats */
grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6

/* Card lists */
grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6

/* Form layouts */
grid-cols-1 md:grid-cols-2 gap-4

/* Sidebar layout */
flex flex-col lg:flex-row
  - Sidebar: w-full lg:w-64
  - Content: flex-1
```

#### Container Widths
```css
max-w-7xl (1280px) mx-auto px-4 sm:px-6 lg:px-8
```

---

### 5. Border Radius System
```css
/* Consistent rounded corners */
none: 0px       /* Sharp edges, dividers */
sm: 0.125rem    /* Small badges, tags */
md: 0.375rem    /* Buttons, inputs, cards */
lg: 0.5rem      /* Large cards, modals */
xl: 0.75rem     /* Hero cards, featured items */
2xl: 1rem       /* Special cards */
full: 9999px    /* Avatars, badges, pills */

/* Usage: */
/* Buttons: rounded-md */
/* Cards: rounded-lg */
/* Inputs: rounded-md */
/* Avatars: rounded-full */
/* Modals: rounded-xl */
```

---

### 6. Shadow System
```css
/* Elevation levels */
shadow-sm:    0 1px 2px 0 rgba(0,0,0,0.05)
              /* Subtle cards, inputs */
              
shadow-md:    0 4px 6px -1px rgba(0,0,0,0.1)
              /* Cards, dropdowns */
              
shadow-lg:    0 10px 15px -3px rgba(0,0,0,0.1)
              /* Modals, popovers */
              
shadow-xl:    0 20px 25px -5px rgba(0,0,0,0.1)
              /* Important modals */
              
shadow-none:  No shadow (flat design)
```

---

### 7. Component States

#### Button States
```tsx
/* Primary Button */
<button className="
  px-4 py-2
  bg-primary-600 hover:bg-primary-700 active:bg-primary-800
  text-white font-medium
  rounded-md
  shadow-sm hover:shadow-md
  transition-all duration-200
  focus:outline-none focus:ring-2 focus:ring-primary-500 focus:ring-offset-2
  disabled:opacity-50 disabled:cursor-not-allowed
  loading:opacity-70 loading:cursor-wait
">
  {isLoading ? (
    <LoadingSpinner size="sm" className="mr-2" />
  ) : null}
  {children}
</button>

/* State specifications: */
/* Default: bg-primary-600 text-white */
/* Hover: bg-primary-700 shadow-md */
/* Active: bg-primary-800 scale-[0.98] */
/* Focus: ring-2 ring-primary-500 ring-offset-2 */
/* Disabled: opacity-50 cursor-not-allowed */
/* Loading: opacity-70 cursor-wait + spinner */
```

#### Input States
```tsx
/* Text Input */
<input className="
  w-full px-3 py-2
  border border-gray-300 rounded-md
  bg-white
  text-gray-900 placeholder-gray-400
  transition-all duration-200
  focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent
  hover:border-gray-400
  disabled:bg-gray-100 disabled:cursor-not-allowed
  /* Error state */
  error:border-error-500 error:focus:ring-error-500
  /* Success state */
  success:border-success-500 success:focus:ring-success-500
" />

/* Label */
<label className="block text-sm font-medium text-gray-700 mb-1">

/* Error message */
<p className="mt-1 text-sm text-error-600">

/* Helper text */
<p className="mt-1 text-sm text-gray-500">
```

#### Card States
```tsx
/* Default Card */
<div className="
  bg-white
  rounded-lg
  border border-gray-200
  shadow-sm
  hover:shadow-md
  transition-shadow duration-200
  p-6
">

/* Interactive Card (clickable) */
<div className="
  bg-white
  rounded-lg
  border border-gray-200
  shadow-sm
  hover:shadow-lg hover:border-primary-300
  cursor-pointer
  transition-all duration-200
  active:scale-[0.99]
  p-6
">
```

---

### 8. Responsiveness Strategy

#### Mobile-First Approach
```css
/* Base styles = Mobile (0-639px) */
/* Add sm: = Tablet (640px+) */
/* Add md: = Small laptop (768px+) */
/* Add lg: = Desktop (1024px+) */
/* Add xl: = Large desktop (1280px+) */
```

#### Responsive Patterns

**Navigation:**
```tsx
/* Mobile: Hamburger menu */
/* Desktop: Horizontal nav */
<nav className="
  lg:flex lg:items-center lg:space-x-6
">
  <MobileMenuButton className="lg:hidden" />
  <DesktopNav className="hidden lg:flex" />
</nav>
```

**Tables:**
```tsx
/* Mobile: Card view */
/* Desktop: Table view */
<div className="hidden md:block">
  <Table>...</Table>
</div>
<div className="md:hidden space-y-4">
  {data.map(item => <Card key={item.id}>...</Card>)}
</div>
```

**Grid Layouts:**
```tsx
<div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
```

---

### 9. Icon System

#### Icon Library: Lucide React
```tsx
import { 
  Home, 
  Users, 
  ShoppingCart, 
  Package, 
  DollarSign,
  Settings,
  Plus,
  Edit,
  Trash2,
  Search,
  Filter,
  Download,
  Printer,
  Eye,
  EyeOff,
  Check,
  X,
  AlertTriangle,
  Info,
  Loader2
} from 'lucide-react';

/* Size guidelines: */
/* Buttons: size={16} (sm), size={20} (md) */
/* Headings: size={24} (lg) */
/* Hero: size={32} (xl) */

/* Usage: */
<Button>
  <Plus size={16} className="mr-2" />
  Add New
</Button>
```

---

### 10. Animation & Transitions

#### Transition Standards
```css
/* Standard transitions */
transition-all duration-200 ease-in-out

/* Fast (hover states) */
transition-all duration-150 ease-in-out

/* Slow (page transitions) */
transition-all duration-300 ease-in-out
```

#### Common Animations
```tsx
/* Fade in */
animate: fadeIn
from: opacity-0
to: opacity-100

/* Slide up */
animate: slideUp
from: opacity-0 translate-y-4
to: opacity-100 translate-y-0

/* Scale (modals, popovers) */
animate: scaleIn
from: opacity-0 scale-95
to: opacity-100 scale-100

/* Loading spinner */
animate: spin
duration: 1s linear infinite
```

---

### 11. Accessibility Standards

#### WCAG 2.1 AA Compliance
```tsx
/* Color contrast: Minimum 4.5:1 for normal text */
/* Color contrast: Minimum 3:1 for large text */

/* Focus indicators: */
focus:outline-none focus:ring-2 focus:ring-primary-500 focus:ring-offset-2

/* ARIA labels: */
<button aria-label="Delete bill" aria-describedby="delete-warning">

/* Screen reader only text: */
<span className="sr-only">Open menu</span>

/* Keyboard navigation: */
/* All interactive elements must be keyboard accessible */
/* Tab order must be logical */
/* Enter/Space for buttons */
/* Escape to close modals */
```

---

### 12. Loading States

#### Skeleton Loaders
```tsx
/* Card skeleton */
<div className="animate-pulse">
  <div className="h-4 bg-gray-200 rounded w-3/4 mb-4"></div>
  <div className="h-3 bg-gray-200 rounded w-1/2 mb-2"></div>
  <div className="h-3 bg-gray-200 rounded w-2/3"></div>
</div>

/* Table skeleton */
{Array.from({ length: 5 }).map((_, i) => (
  <tr key={i} className="animate-pulse">
    <td className="px-4 py-3"><div className="h-4 bg-gray-200 rounded"></div></td>
    <td className="px-4 py-3"><div className="h-4 bg-gray-200 rounded"></div></td>
  </tr>
))}
```

#### Loading Spinners
```tsx
/* Button loading */
<button disabled className="...">
  <Loader2 size={16} className="animate-spin mr-2" />
  Loading...
</button>

/* Page loading */
<div className="flex items-center justify-center min-h-screen">
  <Loader2 size={48} className="animate-spin text-primary-600" />
</div>
```

---

### 13. Feedback Mechanisms

#### Toast Notifications (Sonner)
```tsx
import { toast } from 'sonner';

/* Success */
toast.success('Bill created successfully!', {
  description: `Bill #${billNumber} has been saved`,
  duration: 4000,
});

/* Error */
toast.error('Failed to save bill', {
  description: errorMessage,
  duration: 6000,
});

/* Warning */
toast.warning('Stock is running low', {
  description: `${productName} has only ${quantity} units left`,
  duration: 5000,
});

/* Info */
toast.info('Syncing data...', {
  duration: 3000,
});
```

#### Form Validation Feedback
```tsx
/* Inline validation */
<div>
  <label className="...">Email</label>
  <input 
    className={errors.email ? 'border-error-500' : 'border-gray-300'}
    aria-invalid={errors.email ? 'true' : 'false'}
    aria-describedby={errors.email ? 'email-error' : undefined}
  />
  {errors.email && (
    <p id="email-error" className="text-sm text-error-600 mt-1" role="alert">
      {errors.email.message}
    </p>
  )}
</div>
```

---

## 🏗️ Frontend Architecture

This section defines the modern React-based frontend architecture, replacing the legacy Thymeleaf templates.

### 1. Project Structure (React Frontend)

```
milkmate-frontend/
├── public/
│   ├── favicon.ico
│   └── logo.svg
│
├── src/
│   ├── assets/              # Static assets
│   │   ├── images/
│   │   └── fonts/
│   │
│   ├── components/          # Reusable UI components
│   │   ├── ui/             # shadcn/ui primitives
│   │   │   ├── button.tsx
│   │   │   ├── input.tsx
│   │   │   ├── card.tsx
│   │   │   ├── dialog.tsx
│   │   │   ├── table.tsx
│   │   │   ├── form.tsx
│   │   │   ├── toast.tsx
│   │   │   └── ...
│   │   │
│   │   ├── layout/         # Layout components
│   │   │   ├── Header.tsx
│   │   │   ├── Sidebar.tsx
│   │   │   ├── Footer.tsx
│   │   │   └── MainLayout.tsx
│   │   │
│   │   ├── common/         # Shared components
│   │   │   ├── StatCard.tsx
│   │   │   ├── DataTable.tsx
│   │   │   ├── SearchBar.tsx
│   │   │   ├── FilterBar.tsx
│   │   │   ├── EmptyState.tsx
│   │   │   ├── LoadingSpinner.tsx
│   │   │   ├── ErrorBoundary.tsx
│   │   │   └── ConfirmDialog.tsx
│   │   │
│   │   └── features/       # Feature-specific components
│   │       ├── bills/
│   │       ├── products/
│   │       ├── customers/
│   │       └── ...
│   │
│   ├── pages/              # Page components (routes)
│   │   ├── LoginPage.tsx
│   │   ├── DashboardPage.tsx
│   │   ├── BillsPage.tsx
│   │   ├── CreateBillPage.tsx
│   │   ├── ProductsPage.tsx
│   │   ├── CustomersPage.tsx
│   │   └── ...
│   │
│   ├── hooks/              # Custom React hooks
│   │   ├── useAuth.ts
│   │   ├── useBills.ts
│   │   ├── useProducts.ts
│   │   └── ...
│   │
│   ├── stores/             # Zustand stores (state management)
│   │   ├── authStore.ts
│   │   ├── billStore.ts
│   │   └── uiStore.ts
│   │
│   ├── services/           # API services
│   │   ├── api.ts          # Axios instance
│   │   ├── authService.ts
│   │   ├── billService.ts
│   │   ├── productService.ts
│   │   └── ...
│   │
│   ├── types/              # TypeScript type definitions
│   │   ├── index.ts
│   │   ├── bill.ts
│   │   ├── product.ts
│   │   └── api.ts
│   │
│   ├── utils/              # Utility functions
│   │   ├── formatters.ts
│   │   ├── validators.ts
│   │   └── helpers.ts
│   │
│   ├── constants/          # App constants
│   │   ├── routes.ts
│   │   ├── config.ts
│   │   └── messages.ts
│   │
│   ├── App.tsx             # Main app component
│   ├── main.tsx            # Entry point
│   └── vite-env.d.ts       # Vite type definitions
│
├── index.html
├── package.json
├── tsconfig.json
├── vite.config.ts
├── tailwind.config.ts
└── postcss.config.js
```

---

### 2. Component Architecture

#### Reusable Component Pattern
```tsx
// components/ui/button.tsx
import * as React from 'react';
import { cva, type VariantProps } from 'class-variance-authority';
import { cn } from '@/utils/helpers';

const buttonVariants = cva(
  'inline-flex items-center justify-center rounded-md text-sm font-medium transition-all duration-200 focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-primary-500 focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50',
  {
    variants: {
      variant: {
        default: 'bg-primary-600 text-white hover:bg-primary-700 shadow-sm hover:shadow-md',
        destructive: 'bg-error-600 text-white hover:bg-error-700',
        outline: 'border border-gray-300 bg-white hover:bg-gray-50',
        secondary: 'bg-secondary-100 text-secondary-900 hover:bg-secondary-200',
        ghost: 'hover:bg-gray-100',
        link: 'text-primary-600 underline-offset-4 hover:underline',
      },
      size: {
        default: 'h-10 px-4 py-2',
        sm: 'h-9 rounded-md px-3',
        lg: 'h-11 rounded-md px-8',
        icon: 'h-10 w-10',
      },
    },
    defaultVariants: {
      variant: 'default',
      size: 'default',
    },
  }
);

export interface ButtonProps
  extends React.ButtonHTMLAttributes<HTMLButtonElement>,
    VariantProps<typeof buttonVariants> {
  isLoading?: boolean;
}

const Button = React.forwardRef<HTMLButtonElement, ButtonProps>(
  ({ className, variant, size, isLoading, children, ...props }, ref) => {
    return (
      <button
        className={cn(buttonVariants({ variant, size, className }))}
        ref={ref}
        disabled={isLoading || props.disabled}
        {...props}
      >
        {isLoading && <Loader2 size={16} className="mr-2 animate-spin" />}
        {children}
      </button>
    );
  }
);
Button.displayName = 'Button';

export { Button, buttonVariants };
```

---

### 3. State Management (Zustand)

#### Auth Store
```tsx
// stores/authStore.ts
import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface User {
  id: number;
  name: string;
  email: string;
  role: 'OWNER' | 'MANAGER' | 'EMPLOYEE' | 'ACCOUNTANT';
  isActive: boolean;
}

interface AuthState {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
  updateUser: (user: User) => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      user: null,
      token: null,
      isAuthenticated: false,

      login: async (email: string, password: string) => {
        const response = await authService.login({ email, password });
        set({
          user: response.user,
          token: response.token,
          isAuthenticated: true,
        });
      },

      logout: () => {
        set({
          user: null,
          token: null,
          isAuthenticated: false,
        });
      },

      updateUser: (user: User) => {
        set({ user });
      },
    }),
    {
      name: 'auth-storage',
    }
  )
);
```

---

### 4. API Service Layer

#### Axios Instance with Interceptors
```tsx
// services/api.ts
import axios from 'axios';

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor - Add auth token
api.interceptors.request.use(
  (config) => {
    const token = useAuthStore.getState().token;
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Response interceptor - Handle errors
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      useAuthStore.getState().logout();
      window.location.href = '/login';
    }
    
    if (error.response?.status === 403) {
      toast.error('You do not have permission to perform this action');
    }
    
    return Promise.reject(error);
  }
);

export default api;
```

---

### 5. Type Safety (TypeScript)

#### Type Definitions
```tsx
// types/bill.ts
export interface Bill {
  id: number;
  billNo: number;
  fullName: string;
  date: string;
  mobileNumber: string;
  customerType: 'CUSTOMER' | 'RETAILER' | 'WHOLESALER';
  item: string;
  qty: number;
  price: number;
  subtotal: number;
  cgst: number;
  sgst: number;
  discount: number;
  total: number;
  paidAmount: number;
  balanceDue: number;
}

export interface CreateBillInput {
  fullName: string;
  mobileNumber: string;
  customerType: 'CUSTOMER' | 'RETAILER' | 'WHOLESALER';
  items: BillItem[];
}

export interface BillItem {
  item: string;
  qty: number;
  price: number;
}

// types/api.ts
export interface ApiResponse<T> {
  data: T;
  message: string;
  success: boolean;
}

export interface PaginatedResponse<T> {
  data: T[];
  total: number;
  page: number;
  limit: number;
  totalPages: number;
}
```

---

## 📄 Page Specifications

This section provides detailed specifications for every page in the application, including layout, components, interactions, and all states.

---

### 1. Login Page

**Route:** `/login`
**Access:** Public
**Purpose:** User authentication

#### Layout Structure
```
┌─────────────────────────────────────────────┐
│                                             │
│              [Logo + App Name]              │
│                                             │
│         ┌───────────────────────┐          │
│         │                       │          │
│         │   Login Form Card     │          │
│         │                       │          │
│         │   Email Input         │          │
│         │   Password Input      │          │
│         │   Login Button        │          │
│         │                       │          │
│         │   Forgot Password?    │          │
│         │   Sign Up Link        │          │
│         │                       │          │
│         └───────────────────────┘          │
│                                             │
└─────────────────────────────────────────────┘
```

#### Components Used
- Logo (centered, 120px width)
- Card (max-w-md, centered)
- Form (React Hook Form + Zod validation)
- Input fields (email, password with toggle visibility)
- Button (primary, full-width)
- Links (forgot password, sign up)

#### User Interactions
1. User enters email and password
2. Clicks "Login" button
3. Button shows loading state
4. On success → Redirect to dashboard
5. On error → Show toast notification

#### Loading State
- Button shows spinner: `<Loader2 className="animate-spin" />`
- Button disabled during login
- Text changes to "Logging in..."

#### Error States
- Invalid credentials: Red border on inputs + toast error
- Network error: Toast with retry option
- Account deactivated: Clear message with contact info

#### Validation Rules
```tsx
const loginSchema = z.object({
  email: z.string().email('Invalid email address'),
  password: z.string().min(1, 'Password is required'),
});
```

---

### 2. Dashboard Page

**Route:** `/dashboard`
**Access:** All authenticated users (role-based content)
**Purpose:** Overview of business metrics and quick actions

#### Layout Structure
```
┌─────────────────────────────────────────────────────┐
│ Header (Logo, User Menu, Notifications)             │
├──────────┬──────────────────────────────────────────┤
│          │                                          │
│ Sidebar  │  Welcome Message                         │
│          │                                          │
│ Nav      ├──────────────────────────────────────────┤
│ Items    │                                          │
│          │  Stats Cards (4-column grid)             │
│          │  ┌──────┐ ┌──────┐ ┌──────┐ ┌──────┐   │
│          │  │Total │ │Total │ │Total │ │Total │   │
│          │  │Bills │ │Sales │ │Stock │ │Cust. │   │
│          │  └──────┘ └──────┘ └──────┘ └──────┘   │
│          │                                          │
│          ├──────────────────────────────────────────┤
│          │                                          │
│          │  Charts Section (2-column grid)          │
│          │  ┌─────────────────┐ ┌────────────────┐ │
│          │  │                 │ │                │ │
│          │  │ Sales Chart     │ │ Customer Chart │ │
│          │  │                 │ │                │ │
│          │  └─────────────────┘ └────────────────┘ │
│          │                                          │
│          ├──────────────────────────────────────────┤
│          │                                          │
│          │  Recent Transactions (Table)             │
│          │  ┌──────────────────────────────────┐   │
│          │  │ Bill # | Customer | Amount | ... │   │
│          │  └──────────────────────────────────┘   │
│          │                                          │
└──────────┴──────────────────────────────────────────┘
```

#### Components Used
- Header component (sticky)
- Sidebar component (collapsible on mobile)
- StatCard (4 instances)
- Chart components (LineChart, PieChart)
- DataTable (recent transactions)
- Quick action buttons

#### Data Displayed
- Total bills count (today/this month)
- Total sales amount
- Low stock alerts
- Customer count
- Sales trend (last 7/30 days)
- Customer distribution (type-wise)
- Recent 10 transactions

#### Loading States
- Skeleton loaders for stat cards
- Chart placeholder with spinner
- Table skeleton (5 rows)

#### Empty States
- No bills: "No bills yet. Create your first bill!" + CTA button
- No data: Illustration + "Start adding data to see insights here"

#### Role-Based Visibility
- OWNER: All metrics and actions
- MANAGER: Same as owner, no delete actions
- EMPLOYEE: Limited to bill creation, no financial metrics
- ACCOUNTANT: Financial metrics only

---

### 3. Bills List Page

**Route:** `/bills`
**Access:** All authenticated users
**Purpose:** View, filter, and manage all bills

#### Layout Structure
```
┌─────────────────────────────────────────────────────────┐
│ Header                                                   │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  Page Title: "Bills"              [Create Bill Button]  │
│                                                          │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  Filters Bar:                                           │
│  [Search Input] [Customer Type ▼] [Date Range] [Apply] │
│                                                          │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  Desktop: Table View                                    │
│  ┌──────────────────────────────────────────────────┐  │
│  │ Bill# | Customer | Type | Date | Total | Actions│  │
│  ├──────────────────────────────────────────────────┤  │
│  │ #1001 | John Doe | R    | ...  | ₹500 | [E][P][D]│  │
│  │ #1002 | Jane Smith| W   | ...  | ₹800 | [E][P][D]│  │
│  └──────────────────────────────────────────────────┘  │
│                                                          │
│  Mobile: Card View                                      │
│  ┌─────────────────────┐                               │
│  │ Bill #1001          │                               │
│  │ John Doe (Retailer) │                               │
│  │ ₹500 | [Actions]    │                               │
│  └─────────────────────┘                               │
│                                                          │
├─────────────────────────────────────────────────────────┤
│                                                          │
│  Pagination: [Previous] 1 2 3 ...10 [Next]             │
│                                                          │
└─────────────────────────────────────────────────────────┘
```

#### Components Used
- PageHeader (title + actions)
- FilterBar (search, dropdowns, date picker)
- DataTable (desktop)
- CardList (mobile)
- Pagination
- ActionButtons (edit, print, delete - role-based)
- EmptyState
- ConfirmDialog (for delete)

#### User Interactions
1. Search by bill number or customer name (debounced 300ms)
2. Filter by customer type dropdown
3. Filter by date range
4. Click "Create Bill" → Navigate to create bill page
5. Click Edit → Navigate to edit bill page
6. Click Print → Open print preview in new tab
7. Click Delete → Show confirmation dialog → Delete if confirmed

#### Actions by Role
- OWNER: Edit, Print, Delete
- MANAGER: Edit, Print (no delete)
- EMPLOYEE: Print only (view-only)
- ACCOUNTANT: View only

#### States
- Loading: Table skeleton with 10 rows
- Empty: "No bills found" illustration + "Create Bill" CTA
- Error: "Failed to load bills" + Retry button
- Success (after delete): Toast notification

---

### 4. Create Bill Page

**Route:** `/bills/new`
**Access:** OWNER, MANAGER, EMPLOYEE
**Purpose:** Create a new bill with items and customer details

#### Layout Structure
```
┌──────────────────────────────────────────────────────┐
│ Header                                                │
├──────────────────────────────────────────────────────┤
│                                                       │
│  [← Back to Bills]     Create New Bill               │
│                                                       │
├──────────────────────────────────────────────────────┤
│                                                       │
│  Left Column (Customer Details)                      │
│  ┌─────────────────────────────────────────────┐    │
│  │ Customer Information                        │    │
│  │                                             │    │
│  │ Full Name: [Input]                          │    │
│  │ Mobile: [Input]                             │    │
│  │ Customer Type: [Dropdown]                   │    │
│  │                                             │    │
│  └─────────────────────────────────────────────┘    │
│                                                       │
├──────────────────────────────────────────────────────┤
│                                                       │
│  Right Column (Items)                                │
│  ┌─────────────────────────────────────────────┐    │
│  │ Items                                       │    │
│  │                                             │    │
│  │ [Product ▼] [Qty] [Price] [Total] [Remove] │    │
│  │ [Product ▼] [Qty] [Price] [Total] [Remove] │    │
│  │                                             │    │
│  │ [+ Add Item Button]                         │    │
│  │                                             │    │
│  └─────────────────────────────────────────────┘    │
│                                                       │
├──────────────────────────────────────────────────────┤
│                                                       │
│  Summary Section                                      │
│  ┌─────────────────────────────────────────────┐    │
│  │ Subtotal:         ₹1000                     │    │
│  │ CGST (9%):        ₹90                       │    │
│  │ SGST (9%):        ₹90                       │    │
│  │ Discount:         [Input] ₹50               │    │
│  │ ─────────────────────────                   │    │
│  │ Grand Total:      ₹1130                     │    │
│  │ Paid Amount:      [Input]                   │    │
│  │ Balance Due:      ₹1130                     │    │
│  └─────────────────────────────────────────────┘    │
│                                                       │
├──────────────────────────────────────────────────────┤
│                                                       │
│  Action Buttons                                       │
│  [Cancel (Secondary)]     [Save Bill (Primary)]      │
│                                                       │
└──────────────────────────────────────────────────────┘
```

#### Components Used
- Breadcrumb/Back button
- Form sections (customer, items, summary)
- Autocomplete (product search)
- Dynamic item rows (add/remove)
- Summary card (auto-calculated)
- Action buttons
- Stock warning alerts

#### User Interactions
1. Enter customer details (or select from existing)
2. Add items (search product, enter qty, auto-calculate)
3. Add multiple items
4. Enter discount (optional)
5. Enter paid amount
6. Auto-calculate totals
7. Save bill → Show success toast → Redirect to bills list
8. Stock automatically deducted

#### Validation
- Customer name: Required, min 2 characters
- Mobile: Required, 10 digits
- Customer type: Required
- Items: At least 1 item required
- Product: Required for each item
- Quantity: Required, > 0, <= stock available
- Price: Required, > 0
- Paid amount: >= 0

#### Real-time Calculations
```tsx
// Auto-calculate on item change
subtotal = qty * price
cgst = subtotal * 0.09
sgst = subtotal * 0.09
total = subtotal + cgst + sgst - discount
balanceDue = total - paidAmount
```

#### Stock Validation
- Real-time stock check when adding items
- Warning if qty > available stock
- Prevent save if insufficient stock
- Show current stock level

---

### 5. Products Page

**Route:** `/products`
**Access:** All authenticated users (edit/delete role-based)
**Purpose:** Manage product catalog

#### Layout Structure
```
┌──────────────────────────────────────────────────┐
│ Header                                            │
├──────────────────────────────────────────────────┤
│                                                   │
│  Products                    [+ Add Product]      │
│                                                   │
├──────────────────────────────────────────────────┤
│                                                   │
│  [Search] [Filter by Company ▼] [View: Grid/List]│
│                                                   │
├──────────────────────────────────────────────────┤
│                                                   │
│  Grid View (default)                             │
│  ┌─────────┐ ┌─────────┐ ┌─────────┐           │
│  │ Product │ │ Product │ │ Product │           │
│  │ Card    │ │ Card    │ │ Card    │           │
│  │         │ │         │ │         │           │
│  │ Name    │ │ Name    │ │ Name    │           │
│  │ Company │ │ Company │ │ Company │           │
│  │ Prices  │ │ Prices  │ │ Prices  │           │
│  │ Actions │ │ Actions │ │ Actions │           │
│  └─────────┘ └─────────┘ └─────────┘           │
│                                                   │
├──────────────────────────────────────────────────┤
│  Pagination                                      │
│                                                   │
└──────────────────────────────────────────────────┘
```

#### Components Used
- ProductCard (grid view)
- ProductRow (list view)
- ViewToggle (grid/list)
- SearchBar
- FilterDropdown
- EmptyState
- ConfirmDialog

#### Product Card Content
- Product name (bold)
- Company name (muted)
- Unit
- Prices (Customer, Retailer, Wholesaler)
- Stock status badge (In Stock/Low/Out)
- Action buttons (Edit, Delete - role-based)

---

This documentation continues with specifications for all remaining pages (Customers, Expenses, Payments, Reports, Settings, etc.) following the same detailed format.

---

## 🗄️ Database Setup

### Option 1: H2 Database (Development - Default)

No setup required! The application uses an in-memory H2 database by default.

**Access H2 Console:**
1. Start the application
2. Open browser: http://localhost:8080/h2-console
3. Use these settings:
   - **JDBC URL**: `jdbc:h2:mem:dairy_management_db`
   - **Username**: `sa`
   - **Password**: (leave empty)

### Option 2: MySQL Database (Production)

#### Step 1: Install MySQL
```bash
# Windows: Download from https://dev.mysql.com/downloads/installer/
# Mac: brew install mysql
# Linux: sudo apt install mysql-server
```

#### Step 2: Create Database and User
```sql
-- Login to MySQL
mysql -u root -p

-- Create database
CREATE DATABASE dairy_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Create user
CREATE USER 'dairy_user'@'localhost' IDENTIFIED BY 'your_secure_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON dairy_management.* TO 'dairy_user'@'localhost';

-- Apply changes
FLUSH PRIVILEGES;

-- Exit
EXIT;
```

#### Step 3: Update .env File
```properties
DB_URL=jdbc:mysql://localhost:3306/dairy_management?createDatabaseIfNotExist=true
DB_DRIVER=com.mysql.cj.jdbc.Driver
DB_USERNAME=dairy_user
DB_PASSWORD=your_secure_password
```

#### Step 4: Update application.properties
The application already uses environment variables, so just ensure your `.env` file is configured correctly.

---

## ⚙️ Configuration

### application.properties

The main configuration file is located at `src/main/resources/application.properties`:

```properties
# Application Name
spring.application.name=DairyManagement

# Server Port
server.port=8080

# Database Configuration (uses environment variables)
spring.datasource.url=${DB_URL:jdbc:h2:mem:dairy_management_db}
spring.datasource.driver-class-name=${DB_DRIVER:org.h2.Driver}
spring.datasource.username=${DB_USERNAME:sa}
spring.datasource.password=${DB_PASSWORD:}

# H2 Console (Development only)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false

# Thymeleaf Configuration
spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.mode=HTML
spring.thymeleaf.encoding=UTF-8
spring.thymeleaf.cache=false

# File Upload Configuration
spring.servlet.multipart.enabled=true
spring.servlet.multipart.file-size-threshold=2KB
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=15MB

# Email Configuration (Gmail SMTP)
spring.mail.host=${MAIL_HOST:smtp.gmail.com}
spring.mail.port=${MAIL_PORT:587}
spring.mail.username=${MAIL_USERNAME:}
spring.mail.password=${MAIL_PASSWORD:}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### Environment Variables

Create a `.env` file in the project root with these variables:

```properties
# Database
DB_URL=jdbc:h2:mem:dairy_management_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
DB_DRIVER=org.h2.Driver
DB_USERNAME=sa
DB_PASSWORD=

# Email
MAIL_HOST=smtp.gmail.com
MAIL_PORT=587
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password

# Admin
ADMIN_EMAIL=admin@gmail.com
ADMIN_PASSWORD=pass
```

---

## 🏃 Running the Application

### Method 1: Using Maven (Recommended)

```bash
# Clean and run
mvn spring-boot:run

# Or using Maven Wrapper
# Windows
.\mvnw.cmd spring-boot:run

# Mac/Linux
./mvnw spring-boot:run
```

### Method 2: Build JAR and Run

```bash
# Step 1: Build the project
mvn clean package

# Step 2: Run the JAR file
java -jar target/dairyManagement-0.0.1-SNAPSHOT.jar
```

### Method 3: From IDE

**IntelliJ IDEA:**
1. Open the project
2. Wait for Maven to import dependencies
3. Find `DairyManagementApplication.java`
4. Right-click → Run 'DairyManagementApplication'

**Eclipse:**
1. Import as Maven project
2. Find `DairyManagementApplication.java`
3. Right-click → Run As → Spring Boot App

**VS Code:**
1. Install Java Extension Pack
2. Open the project
3. Open `DairyManagementApplication.java`
4. Click "Run" button above main method

### Access the Application

Once started, access the application at:
- **Main Application**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console (development only)

### First Login
- **Email**: `admin@gmail.com`
- **Password**: `pass`

---

## 🎯 Core Features

### 1. User Management
- User registration with profile photo upload
- Secure login with BCrypt password encryption
- Profile editing and management
- Password reset via email
- Session-based authentication

### 2. Dashboard
- Total companies, products, bills, payments count
- Latest transactions and their status
- Recent products and companies
- Visual charts and analytics
- Quick access to all modules

### 3. Company Management
- Add, edit, delete companies
- Track contact information
- Manage product associations
- View company-wise reports

### 4. Product Management
- Create and manage dairy products
- Set different prices for customer types
- Associate with companies and units
- Track product availability

### 5. Unit Management
- Define measurement units (Liter, Kilogram, etc.)
- Set conversion rates
- Manage unit relationships

### 6. Stock Management
- Track product inventory
- Automatic stock deduction on bill creation
- Update stock quantities
- View stock levels

### 7. Bill Management
- Create bills for customers, retailers, wholesalers
- Automatic tax calculations (CGST + SGST)
- Discount application
- Bill printing functionality
- Edit and delete bills
- Filter by customer type, date, name

### 8. Payment Management
- Record payments against bills
- Multiple payment methods (Cash, UPI, Card, Bank Transfer)
- Track payment status
- View payment history
- Balance due tracking

### 9. Customer Management
**Retailers:**
- Add and manage retail customers
- Track contact and vehicle information
- Associate with companies and wholesalers
- Document uploads (Aadhar, License, Photo)

**Wholesalers:**
- Add and manage wholesale customers
- Track bulk order information
- Document management
- Company associations

### 10. Expense Management
**Employee Expenses:**
- Track employee salaries
- Manage advance payments
- Calculate remaining salary

**Other Expenses:**
- Shop rent tracking
- Electricity bills
- Diesel expenses
- Miscellaneous expenses

**Total Expenses:**
- Consolidated expense reports
- Daily expense summaries
- Category-wise breakdown

### 11. Reports
- Customer reports
- Retailer reports
- Wholesaler reports
- Transaction summaries

---

## 🚀 Advanced Features (Production-Ready)

This section covers **critical production features** that transform MilkMate from a basic system into an industry-standard, enterprise-ready dairy management platform.

---

### 1. Multi-Item Bill Support

**Problem Solved:** Current system only supports 1 item per bill, which is unrealistic for real-world dairy businesses where customers typically buy multiple products.

**Industry Relevance:** All retail billing systems (Tally, Zoho Books, QuickBooks) support multi-item invoices as a baseline feature.

#### Implementation

**Database Schema Change:**
```sql
-- Create new bill_items table
CREATE TABLE bill_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    bill_id BIGINT NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (bill_id) REFERENCES bills(id) ON DELETE CASCADE
);

-- Modify bills table (remove item-specific fields)
ALTER TABLE bills 
    DROP COLUMN item,
    DROP COLUMN qty,
    DROP COLUMN price,
    DROP COLUMN subtotal;

-- Add indexes
CREATE INDEX idx_bill_items_bill_id ON bill_items(bill_id);
```

**New Entity:**
```java
@Entity
@Table(name = "bill_items")
public class BillItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill bill;
    
    private String productName;
    private int quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
    
    // Getters and setters
}
```

**Updated Bill Entity:**
```java
@Entity
@Table(name = "bills")
public class Bill {
    // ... existing fields ...
    
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillItem> items = new ArrayList<>();
    
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal grandTotal;
    
    // Helper method to add items
    public void addItem(BillItem item) {
        items.add(item);
        item.setBill(this);
    }
    
    // Calculate totals
    public void calculateTotals() {
        BigDecimal subtotal = items.stream()
            .map(BillItem::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal tax = subtotal.multiply(new BigDecimal("0.18")); // 18% GST
        this.totalAmount = subtotal;
        this.grandTotal = subtotal.add(tax).subtract(discount != null ? discount : BigDecimal.ZERO);
    }
}
```

**Frontend UI (React Component):**
```tsx
// components/bills/BillItemsTable.tsx
export function BillItemsTable({ items, setItems }) {
  const addItem = () => {
    setItems([...items, { productName: '', quantity: 1, price: 0 }]);
  };

  const removeItem = (index: number) => {
    setItems(items.filter((_, i) => i !== index));
  };

  const updateItem = (index: number, field: string, value: any) => {
    const newItems = [...items];
    newItems[index] = { ...newItems[index], [field]: value };
    newItems[index].subtotal = newItems[index].quantity * newItems[index].price;
    setItems(newItems);
  };

  return (
    <div className="space-y-4">
      <div className="flex justify-between items-center">
        <h3 className="text-lg font-semibold">Bill Items</h3>
        <Button onClick={addItem} variant="outline" size="sm">
          <Plus size={16} className="mr-2" />
          Add Item
        </Button>
      </div>

      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Product</TableHead>
            <TableHead>Qty</TableHead>
            <TableHead>Price</TableHead>
            <TableHead>Subtotal</TableHead>
            <TableHead>Actions</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {items.map((item, index) => (
            <TableRow key={index}>
              <TableCell>
                <ProductAutocomplete
                  value={item.productName}
                  onChange={(val) => updateItem(index, 'productName', val)}
                />
              </TableCell>
              <TableCell>
                <Input
                  type="number"
                  value={item.quantity}
                  onChange={(e) => updateItem(index, 'quantity', parseInt(e.target.value))}
                  min={1}
                />
              </TableCell>
              <TableCell>
                <Input
                  type="number"
                  value={item.price}
                  onChange={(e) => updateItem(index, 'price', parseFloat(e.target.value))}
                  step="0.01"
                />
              </TableCell>
              <TableCell>₹{item.subtotal.toFixed(2)}</TableCell>
              <TableCell>
                <Button
                  variant="destructive"
                  size="sm"
                  onClick={() => removeItem(index)}
                  disabled={items.length === 1}
                >
                  <Trash2 size={16} />
                </Button>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
}
```

---

### 2. Data Type Improvements (BigDecimal & LocalDate)

**Problem Solved:** Using `String` for dates and `double` for monetary values causes:
- Precision loss in financial calculations
- Inability to perform date operations (sorting, filtering, ranges)
- Rounding errors in tax calculations

**Implementation:**

**Before (Current):**
```java
private String date;           // ❌ Cannot sort/filter properly
private double price;          // ❌ Precision issues
private double total;          // ❌ Rounding errors
```

**After (Improved):**
```java
private LocalDate date;        // ✅ Type-safe date operations
private BigDecimal price;      // ✅ Precise decimal arithmetic
private BigDecimal total;      // ✅ Accurate financial calculations
```

**Migration Script:**
```sql
-- Add new columns with correct types
ALTER TABLE bills ADD COLUMN date_new DATE;
ALTER TABLE bills ADD COLUMN price_new DECIMAL(10,2);
ALTER TABLE bills ADD COLUMN total_new DECIMAL(10,2);

-- Migrate data
UPDATE bills SET date_new = STR_TO_DATE(date, '%Y-%m-%d');
UPDATE bills SET price_new = CAST(price AS DECIMAL(10,2));
UPDATE bills SET total_new = CAST(total AS DECIMAL(10,2));

-- Drop old columns
ALTER TABLE bills DROP COLUMN date;
ALTER TABLE bills DROP COLUMN price;
ALTER TABLE bills DROP COLUMN total;

-- Rename new columns
ALTER TABLE bills CHANGE date_new date DATE;
ALTER TABLE bills CHANGE price_new price DECIMAL(10,2);
ALTER TABLE bills CHANGE total_new total DECIMAL(10,2);
```

**Usage Example:**
```java
// Financial calculations with BigDecimal
BigDecimal subtotal = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
BigDecimal tax = subtotal.multiply(new BigDecimal("0.09")); // 9% CGST
BigDecimal total = subtotal.add(tax).setScale(2, RoundingMode.HALF_UP);

// Date operations with LocalDate
LocalDate today = LocalDate.now();
LocalDate startDate = today.minusDays(30);
List<Bill> monthlyBills = billRepository.findByDateBetween(startDate, today);
```

---

### 3. GST-Compliant Invoicing

**Problem Solved:** Indian businesses require GST-compliant invoices with specific fields for legal compliance.

**Required Fields:**
- GSTIN (GST Identification Number)
- HSN/SAC Code for products
- CGST, SGST, IGST breakdown
- Invoice number format (financial year based)

**Database Changes:**
```sql
ALTER TABLE bills ADD COLUMN gstin VARCHAR(15);
ALTER TABLE bills ADD COLUMN invoice_number VARCHAR(50) UNIQUE;
ALTER TABLE bills ADD COLUMN cgst DECIMAL(10,2);
ALTER TABLE bills ADD COLUMN sgst DECIMAL(10,2);
ALTER TABLE bills ADD COLUMN igst DECIMAL(10,2);
ALTER TABLE bills ADD COLUMN hsn_code VARCHAR(10);

ALTER TABLE companies ADD COLUMN gstin VARCHAR(15);
ALTER TABLE products ADD COLUMN hsn_code VARCHAR(10);
```

**Invoice Number Generation:**
```java
@Service
public class InvoiceService {
    
    public String generateInvoiceNumber(LocalDate date) {
        int year = date.getYear();
        int month = date.getMonthValue();
        int financialYear = month > 3 ? year : year - 1;
        String fy = String.format("%d-%d", financialYear, financialYear + 1);
        
        long count = billRepository.countByFinancialYear(financialYear);
        return String.format("MILK/%s/%05d", fy, count + 1);
    }
}
```

**GST Calculation:**
```java
public class GSTCalculator {
    
    public static BigDecimal calculateCGST(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate.divide(new BigDecimal("100")))
                     .setScale(2, RoundingMode.HALF_UP);
    }
    
    public static BigDecimal calculateSGST(BigDecimal amount, BigDecimal rate) {
        return calculateCGST(amount, rate); // Same as CGST
    }
    
    public static BigDecimal calculateIGST(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate.divide(new BigDecimal("100")))
                     .setScale(2, RoundingMode.HALF_UP);
    }
}
```

**GST Invoice Template:**
```html
<!-- PrintBill.html - GST Compliant Layout -->
<div class="invoice">
  <header>
    <h1>TAX INVOICE</h1>
    <p>Invoice #: <span th:text="${bill.invoiceNumber}"></span></p>
    <p>Date: <span th:text="${bill.date}"></span></p>
  </header>
  
  <section class="seller-info">
    <h3>Seller (Supplier)</h3>
    <p th:text="${company.name}"></p>
    <p>GSTIN: <span th:text="${company.gstin}"></span></p>
  </section>
  
  <section class="buyer-info">
    <h3>Buyer (Recipient)</h3>
    <p th:text="${bill.customerName}"></p>
    <p th:if="${bill.gstin}">GSTIN: <span th:text="${bill.gstin}"></span></p>
  </section>
  
  <table>
    <thead>
      <tr>
        <th>Item</th>
        <th>HSN Code</th>
        <th>Qty</th>
        <th>Price</th>
        <th>Subtotal</th>
      </tr>
    </thead>
    <tbody>
      <tr th:each="item : ${bill.items}">
        <td th:text="${item.productName}"></td>
        <td th:text="${item.hsnCode}"></td>
        <td th:text="${item.quantity}"></td>
        <td>₹<span th:text="${item.price}"></span></td>
        <td>₹<span th:text="${item.subtotal}"></span></td>
      </tr>
    </tbody>
  </table>
  
  <section class="tax-breakdown">
    <p>Subtotal: ₹<span th:text="${bill.totalAmount}"></span></p>
    <p>CGST (9%): ₹<span th:text="${bill.cgst}"></span></p>
    <p>SGST (9%): ₹<span th:text="${bill.sgst}"></span></p>
    <p class="grand-total">Grand Total: ₹<span th:text="${bill.grandTotal}"></span></p>
  </section>
  
  <footer>
    <p>This is a computer-generated invoice</p>
    <p>Thank you for your business!</p>
  </footer>
</div>
```

---

### 4. Advanced Search & Filtering

**Problem Solved:** Finding specific records in large datasets (1000+ bills, customers) is slow and inefficient.

**Implementation:**

**Backend (JPA Specifications):**
```java
// repository/BillSpecifications.java
public class BillSpecifications {
    
    public static Specification<Bill> hasCustomerName(String name) {
        return (root, query, cb) -> 
            name == null ? null : cb.like(cb.lower(root.get("fullName")), "%'" + name.toLowerCase() + "'%");
    }
    
    public static Specification<Bill> hasCustomerType(String type) {
        return (root, query, cb) -> 
            type == null ? null : cb.equal(root.get("customerType"), type);
    }
    
    public static Specification<Bill> hasDateBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            if (start == null && end == null) return null;
            if (start != null && end != null) {
                return cb.between(root.get("date"), start, end);
            }
            if (start != null) return cb.greaterThanOrEqualTo(root.get("date"), start);
            return cb.lessThanOrEqualTo(root.get("date"), end);
        };
    }
    
    public static Specification<Bill> hasBillNumber(Long billNo) {
        return (root, query, cb) -> 
            billNo == null ? null : cb.equal(root.get("billNo"), billNo);
    }
}

// Usage in service
public Page<Bill> searchBills(BillSearchCriteria criteria, Pageable pageable) {
    Specification<Bill> spec = Specification
        .where(BillSpecifications.hasCustomerName(criteria.getCustomerName()))
        .and(BillSpecifications.hasCustomerType(criteria.getCustomerType()))
        .and(BillSpecifications.hasDateBetween(criteria.getStartDate(), criteria.getEndDate()))
        .and(BillSpecifications.hasBillNumber(criteria.getBillNumber()));
    
    return billRepository.findAll(spec, pageable);
}
```

**Frontend (React Filter Component):**
```tsx
// components/filters/BillFilterBar.tsx
export function BillFilterBar({ onFilter }) {
  const [filters, setFilters] = useState({
    customerName: '',
    customerType: '',
    startDate: '',
    endDate: '',
    billNumber: '',
  });

  const debouncedSearch = useDebounce(filters.customerName, 300);

  useEffect(() => {
    onFilter({ ...filters, customerName: debouncedSearch });
  }, [debouncedSearch, filters.customerType, filters.startDate, filters.endDate, filters.billNumber]);

  return (
    <div className="flex flex-wrap gap-4 p-4 bg-white rounded-lg shadow-sm">
      <div className="flex-1 min-w-[200px]">
        <label className="text-sm font-medium">Search Customer</label>
        <Input
          placeholder="Name or mobile..."
          value={filters.customerName}
          onChange={(e) => setFilters({ ...filters, customerName: e.target.value })}
        />
      </div>
      
      <div className="w-[180px]">
        <label className="text-sm font-medium">Customer Type</label>
        <Select value={filters.customerType} onValueChange={(val) => setFilters({ ...filters, customerType: val })}>
          <SelectTrigger>
            <SelectValue placeholder="All types" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="">All</SelectItem>
            <SelectItem value="CUSTOMER">Customer</SelectItem>
            <SelectItem value="RETAILER">Retailer</SelectItem>
            <SelectItem value="WHOLESALER">Wholesaler</SelectItem>
          </SelectContent>
        </Select>
      </div>
      
      <div className="w-[150px]">
        <label className="text-sm font-medium">From Date</label>
        <Input
          type="date"
          value={filters.startDate}
          onChange={(e) => setFilters({ ...filters, startDate: e.target.value })}
        />
      </div>
      
      <div className="w-[150px]">
        <label className="text-sm font-medium">To Date</label>
        <Input
          type="date"
          value={filters.endDate}
          onChange={(e) => setFilters({ ...filters, endDate: e.target.value })}
        />
      </div>
      
      <div className="w-[150px]">
        <label className="text-sm font-medium">Bill Number</label>
        <Input
          type="number"
          placeholder="#1001"
          value={filters.billNumber}
          onChange={(e) => setFilters({ ...filters, billNumber: e.target.value })}
        />
      </div>
    </div>
  );
}
```

---

### 5. Pagination for All Lists

**Problem Solved:** Loading all records at once causes slow performance and high memory usage.

**Implementation:**

**Backend:**
```java
@GetMapping("/bills")
public ApiResponse<PagedResponse<BillResponse>> getBills(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size,
        @RequestParam(required = false) String search,
        @RequestParam(required = false) String customerType) {
    
    Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
    
    Specification<Bill> spec = Specification
        .where(BillSpecifications.hasCustomerName(search))
        .and(BillSpecifications.hasCustomerType(customerType));
    
    Page<Bill> bills = billRepository.findAll(spec, pageable);
    
    List<BillResponse> responses = bills.getContent().stream()
        .map(billMapper::toResponse)
        .collect(Collectors.toList());
    
    PagedResponse<BillResponse> pagedResponse = PagedResponse.of(
        responses, 
        bills.getTotalElements(), 
        bills.getNumber(), 
        bills.getSize(),
        bills.getTotalPages()
    );
    
    return ApiResponse.success(pagedResponse);
}
```

**Frontend:**
```tsx
// components/common/Pagination.tsx
export function Pagination({ currentPage, totalPages, onPageChange }) {
  const pages = Array.from({ length: Math.min(5, totalPages) }, (_, i) => {
    let page = currentPage - 2 + i;
    if (page < 0) page += 2;
    if (page >= totalPages) page = totalPages - 1;
    return page;
  });

  return (
    <div className="flex items-center justify-between mt-4">
      <p className="text-sm text-gray-600">
        Page {currentPage + 1} of {totalPages}
      </p>
      
      <div className="flex gap-2">
        <Button
          variant="outline"
          size="sm"
          onClick={() => onPageChange(currentPage - 1)}
          disabled={currentPage === 0}
        >
          Previous
        </Button>
        
        {pages.map((page) => (
          <Button
            key={page}
            variant={currentPage === page ? "default" : "outline"}
            size="sm"
            onClick={() => onPageChange(page)}
          >
            {page + 1}
          </Button>
        ))}
        
        <Button
          variant="outline"
          size="sm"
          onClick={() => onPageChange(currentPage + 1)}
          disabled={currentPage === totalPages - 1}
        >
          Next
        </Button>
      </div>
    </div>
  );
}
```

---

### 6. Stock Alerts & Reorder Points

**Problem Solved:** No proactive notification when stock is running low, leading to stockouts.

**Implementation:**

**Database:**
```sql
ALTER TABLE stocks ADD COLUMN reorder_point INT DEFAULT 10;
ALTER TABLE stocks ADD COLUMN max_stock INT DEFAULT 100;
```

**Entity:**
```java
@Entity
public class Stock {
    // ... existing fields ...
    
    private int reorderPoint = 10;
    private int maxStock = 100;
    
    public boolean isLowStock() {
        return quantity <= reorderPoint;
    }
    
    public boolean isOutOfStock() {
        return quantity == 0;
    }
    
    public StockStatus getStatus() {
        if (isOutOfStock()) return StockStatus.OUT_OF_STOCK;
        if (isLowStock()) return StockStatus.LOW_STOCK;
        return StockStatus.IN_STOCK;
    }
}

public enum StockStatus {
    IN_STOCK, LOW_STOCK, OUT_OF_STOCK
}
```

**Service:**
```java
@Service
public class StockAlertService {
    
    @Autowired
    private StockRepository stockRepository;
    
    @Autowired
    private EmailService emailService;
    
    public List<Stock> getLowStockAlerts() {
        return stockRepository.findAll().stream()
            .filter(Stock::isLowStock)
            .collect(Collectors.toList());
    }
    
    @Scheduled(cron = "0 0 9 * * ?") // Daily at 9 AM
    public void sendStockAlerts() {
        List<Stock> lowStock = getLowStockAlerts();
        
        if (!lowStock.isEmpty()) {
            String message = buildAlertMessage(lowStock);
            emailService.sendAlert("Stock Alert", message);
        }
    }
    
    private String buildAlertMessage(List<Stock> lowStock) {
        StringBuilder sb = new StringBuilder("Low Stock Alert:\n\n");
        for (Stock stock : lowStock) {
            sb.append(String.format("%s: %d units (Reorder at: %d)\n",
                stock.getProductName(), stock.getQuantity(), stock.getReorderPoint()));
        }
        return sb.toString();
    }
}
```

**Frontend (Dashboard Widget):**
```tsx
// components/dashboard/StockAlerts.tsx
export function StockAlerts({ alerts }) {
  if (alerts.length === 0) return null;

  return (
    <Card className="border-warning-500">
      <CardHeader className="bg-warning-50">
        <CardTitle className="flex items-center text-warning-700">
          <AlertTriangle size={20} className="mr-2" />
          Low Stock Alerts ({alerts.length})
        </CardTitle>
      </CardHeader>
      <CardContent>
        <ul className="space-y-2">
          {alerts.map((alert) => (
            <li key={alert.id} className="flex justify-between items-center">
              <span className="font-medium">{alert.productName}</span>
              <Badge variant={alert.isOutOfStock() ? "destructive" : "warning"}>
                {alert.quantity} units left
              </Badge>
            </li>
          ))}
        </ul>
        <Button className="mt-4 w-full" variant="outline">
          View All Stock
        </Button>
      </CardContent>
    </Card>
  );
}
```

---

### 7. Export & Import (Excel, PDF, CSV)

**Problem Solved:** No way to backup data, share with accountants, or analyze in external tools.

**Dependencies:**
```xml
<!-- pom.xml -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.2.5</version>
</dependency>
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itext7-core</artifactId>
    <version>8.0.2</version>
    <type>pom</type>
</dependency>
<dependency>
    <groupId>com.opencsv</groupId>
    <artifactId>opencsv</artifactId>
    <version>5.9</version>
</dependency>
```

**Export Service:**
```java
@Service
public class ExportService {
    
    public byte[] exportBillsToExcel(List<Bill> bills) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Bills");
            
            // Header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Bill No", "Customer", "Date", "Type", "Total", "Paid", "Balance"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(createHeaderStyle(workbook));
            }
            
            // Data rows
            for (int i = 0; i < bills.size(); i++) {
                Row row = sheet.createRow(i + 1);
                Bill bill = bills.get(i);
                row.createCell(0).setCellValue(bill.getBillNo());
                row.createCell(1).setCellValue(bill.getFullName());
                row.createCell(2).setCellValue(bill.getDate().toString());
                row.createCell(3).setCellValue(bill.getCustomerType());
                row.createCell(4).setCellValue(bill.getGrandTotal().doubleValue());
                row.createCell(5).setCellValue(bill.getPaidAmount());
                row.createCell(6).setCellValue(bill.getBalanceDue());
            }
            
            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return out.toByteArray();
        }
    }
    
    public byte[] exportBillsToCSV(List<Bill> bills) throws IOException {
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer);
        
        // Header
        csvWriter.writeNext(new String[]{"Bill No", "Customer", "Date", "Total"});
        
        // Data
        for (Bill bill : bills) {
            csvWriter.writeNext(new String[]{
                bill.getBillNo().toString(),
                bill.getFullName(),
                bill.getDate().toString(),
                bill.getGrandTotal().toString()
            });
        }
        
        csvWriter.close();
        return writer.toString().getBytes();
    }
}
```

**Controller:**
```java
@GetMapping("/bills/export/excel")
public ResponseEntity<byte[]> exportBillsExcel(
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate) {
    
    List<Bill> bills = billService.getBillsByDateRange(startDate, endDate);
    byte[] excelData = exportService.exportBillsToExcel(bills);
    
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bills.xlsx")
        .contentType(MediaType.APPLICATION_OCTET_STREAM)
        .body(excelData);
}

@GetMapping("/bills/export/csv")
public ResponseEntity<byte[]> exportBillsCSV(...) {
    // Similar to Excel
}
```

**Frontend:**
```tsx
// Export buttons in Bills page
<div className="flex gap-2">
  <Button onClick={() => exportToExcel()} variant="outline">
    <Download size={16} className="mr-2" />
    Export Excel
  </Button>
  <Button onClick={() => exportToCSV()} variant="outline">
    <Download size={16} className="mr-2" />
    Export CSV
  </Button>
  <Button onClick={() => exportToPDF()} variant="outline">
    <Download size={16} className="mr-2" />
    Export PDF
  </Button>
</div>
```

---

### 8. Sales Analytics & Reports

**Problem Solved:** Basic dashboard only shows counts, no insights or trends.

**Implementation:**

**Backend Analytics Service:**
```java
@Service
public class AnalyticsService {
    
    @Autowired
    private BillRepository billRepository;
    
    public SalesTrendResponse getSalesTrend(LocalDate startDate, LocalDate endDate) {
        List<Object[]> results = billRepository.getDailySales(startDate, endDate);
        
        List<SalesDataPoint> trend = results.stream()
            .map(row -> new SalesDataPoint(
                (LocalDate) row[0],
                ((Number) row[1]).intValue(),
                (BigDecimal) row[2]
            ))
            .collect(Collectors.toList());
        
        BigDecimal totalSales = trend.stream()
            .map(SalesDataPoint::getAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        return new SalesTrendResponse(trend, totalSales);
    }
    
    public TopProductsResponse getTopProducts(int limit) {
        List<Object[]> results = billRepository.getTopSellingProducts(limit);
        
        List<ProductSales> topProducts = results.stream()
            .map(row -> new ProductSales(
                (String) row[0],
                ((Number) row[1]).intValue(),
                (BigDecimal) row[2]
            ))
            .collect(Collectors.toList());
        
        return new TopProductsResponse(topProducts);
    }
    
    public CustomerAnalysisResponse getCustomerAnalysis() {
        Map<String, Long> customerTypeDistribution = billRepository.getCustomerTypeDistribution();
        List<CustomerStats> topCustomers = billRepository.getTopCustomersBySpending(10);
        
        return new CustomerAnalysisResponse(customerTypeDistribution, topCustomers);
    }
}
```

**Repository Queries:**
```java
public interface BillRepository extends JpaRepository<Bill, Long> {
    
    @Query("SELECT b.date, COUNT(b), SUM(b.grandTotal) FROM Bill b " +
           "WHERE b.date BETWEEN :start AND :end " +
           "GROUP BY b.date ORDER BY b.date")
    List<Object[]> getDailySales(@Param("start") LocalDate start, 
                                 @Param("end") LocalDate end);
    
    @Query("SELECT bi.productName, SUM(bi.quantity), SUM(bi.subtotal) " +
           "FROM BillItem bi GROUP BY bi.productName " +
           "ORDER BY SUM(bi.subtotal) DESC")
    List<Object[]> getTopSellingProducts(@Param("limit") int limit);
    
    @Query("SELECT b.customerType, COUNT(b) FROM Bill b GROUP BY b.customerType")
    Map<String, Long> getCustomerTypeDistribution();
}
```

**Frontend Charts:**
```tsx
// pages/DashboardPage.tsx
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import { BarChart, Bar } from 'recharts';
import { PieChart, Pie, Cell } from 'recharts';

export function DashboardPage() {
  const { data: salesTrend } = useQuery({
    queryKey: ['salesTrend'],
    queryFn: () => analyticsService.getSalesTrend(last30Days),
  });
  
  const { data: topProducts } = useQuery({
    queryKey: ['topProducts'],
    queryFn: () => analyticsService.getTopProducts(10),
  });
  
  const COLORS = ['#3b82f6', '#22c55e', '#eab308', '#ef4444'];
  
  return (
    <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
      {/* Sales Trend Chart */}
      <Card>
        <CardHeader>
          <CardTitle>Sales Trend (Last 30 Days)</CardTitle>
        </CardHeader>
        <CardContent>
          <ResponsiveContainer width="100%" height={300}>
            <LineChart data={salesTrend?.trend}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="date" />
              <YAxis />
              <Tooltip />
              <Line type="monotone" dataKey="amount" stroke="#3b82f6" strokeWidth={2} />
            </LineChart>
          </ResponsiveContainer>
        </CardContent>
      </Card>
      
      {/* Top Products */}
      <Card>
        <CardHeader>
          <CardTitle>Top Products</CardTitle>
        </CardHeader>
        <CardContent>
          <ResponsiveContainer width="100%" height={300}>
            <BarChart data={topProducts?.products}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="name" />
              <YAxis />
              <Tooltip />
              <Bar dataKey="revenue" fill="#3b82f6" />
            </BarChart>
          </ResponsiveContainer>
        </CardContent>
      </Card>
      
      {/* Customer Distribution */}
      <Card>
        <CardHeader>
          <CardTitle>Customer Distribution</CardTitle>
        </CardHeader>
        <CardContent>
          <ResponsiveContainer width="100%" height={300}>
            <PieChart>
              <Pie
                data={customerDistribution}
                cx="50%"
                cy="50%"
                outerRadius={100}
                fill="#8884d8"
                dataKey="count"
                label
              >
                {customerDistribution.map((entry, index) => (
                  <Cell key={entry.name} fill={COLORS[index % COLORS.length]} />
                ))}
              </Pie>
              <Tooltip />
            </PieChart>
          </ResponsiveContainer>
        </CardContent>
      </Card>
    </div>
  );
}
```

---

### 9. Customer Credit Management & Payment Reminders

**Problem Solved:** No tracking of credit limits or automated follow-ups for overdue payments.

**Database:**
```sql
ALTER TABLE retailers ADD COLUMN credit_limit DECIMAL(10,2) DEFAULT 0;
ALTER TABLE wholesalers ADD COLUMN credit_limit DECIMAL(10,2) DEFAULT 0;
ALTER TABLE bills ADD COLUMN due_date DATE;
```

**Entity:**
```java
@Entity
public class Retailer {
    // ... existing fields ...
    
    private BigDecimal creditLimit = BigDecimal.ZERO;
    
    public BigDecimal getCurrentBalance() {
        return bills.stream()
            .map(Bill::getBalanceDue)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public BigDecimal getAvailableCredit() {
        return creditLimit.subtract(getCurrentBalance());
    }
    
    public boolean hasExceededCreditLimit() {
        return getCurrentBalance().compareTo(creditLimit) > 0;
    }
}
```

**Automated Reminder Service:**
```java
@Service
public class PaymentReminderService {
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private BillRepository billRepository;
    
    @Scheduled(cron = "0 0 10 * * ?") // Daily at 10 AM
    public void sendPaymentReminders() {
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.minusDays(7); // 7 days overdue
        
        List<Bill> overdueBills = billRepository.findOverdueBills(dueDate);
        
        for (Bill bill : overdueBills) {
            String subject = "Payment Reminder - Bill #" + bill.getBillNo();
            String message = buildReminderMessage(bill);
            
            emailService.sendEmail(bill.getCustomerEmail(), subject, message);
            
            // Log the reminder
            auditLogService.logAction("PAYMENT_REMINDER_SENT", bill.getId());
        }
    }
    
    private String buildReminderMessage(Bill bill) {
        return String.format(
            "Dear %s,\n\n" +
            "This is a reminder that Bill #%s dated %s is overdue.\n" +
            "Amount Due: ₹%s\n" +
            "Due Date: %s\n\n" +
            "Please make the payment at your earliest convenience.\n\n" +
            "Thank you,\nMilkMate Dairy Management",
            bill.getFullName(),
            bill.getBillNo(),
            bill.getDate(),
            bill.getBalanceDue(),
            bill.getDueDate()
        );
    }
}
```

---

### 10. Bill Preview Before Printing

**Problem Solved:** Print opens directly without preview, leading to wasted paper on errors.

**Implementation:**

```tsx
// components/bills/BillPreviewModal.tsx
export function BillPreviewModal({ bill, isOpen, onClose }) {
  const componentRef = useRef();
  
  const handlePrint = () => {
    const printWindow = window.open('', '_blank');
    printWindow.document.write(componentRef.current.innerHTML);
    printWindow.document.close();
    printWindow.print();
  };
  
  return (
    <Dialog open={isOpen} onOpenChange={onClose}>
      <DialogContent className="max-w-4xl max-h-[90vh] overflow-y-auto">
        <DialogHeader>
          <DialogTitle>Bill Preview</DialogTitle>
        </DialogHeader>
        
        <div ref={componentRef} className="p-8 bg-white">
          {/* Bill content here */}
          <BillContent bill={bill} />
        </div>
        
        <DialogFooter>
          <Button variant="outline" onClick={onClose}>Close</Button>
          <Button onClick={handlePrint}>
            <Printer size={16} className="mr-2" />
            Print Bill
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
}

// Usage in Bills page
<Button onClick={() => setPreviewBill(bill)}>
  <Eye size={16} />
  Preview
</Button>

<BillPreviewModal 
  bill={previewBill} 
  isOpen={!!previewBill} 
  onClose={() => setPreviewBill(null)} 
/>
```

---

---

## 🔐 Multi-Login & Role-Based Access Control

### Overview

The MilkMate system implements a **role-based access control (RBAC)** system to prevent unauthorized access and ensure that employees cannot perform sensitive operations like deleting bills, modifying payments, or accessing financial reports. This protects the business from potential theft, fraud, or accidental data loss.

### User Roles

The system supports **4 distinct roles** with different permission levels:

#### Permissions Matrix

| Feature | OWNER | MANAGER | EMPLOYEE | ACCOUNTANT |
|---------|-------|---------|----------|------------|
| Create Bills | ✅ | ✅ | ✅ | ❌ |
| View Bills | ✅ | ✅ | ✅ | ✅ |
| Edit Bills | ✅ | ✅ | ❌ | ❌ |
| Delete Bills | ✅ | ❌ | ❌ | ❌ |
| Print Bills | ✅ | ✅ | ✅ | ✅ |
| Manage Stock | ✅ | ✅ | ❌ | ❌ |
| View Stock | ✅ | ✅ | ✅ | ✅ |
| Add Customers | ✅ | ✅ | ❌ | ❌ |
| Edit Customers | ✅ | ✅ | ❌ | ❌ |
| Delete Customers | ✅ | ❌ | ❌ | ❌ |
| Record Payments | ✅ | ✅ | ✅ | ✅ |
| View Payment History | ✅ | ✅ | ❌ | ✅ |
| Delete Payments | ✅ | ❌ | ❌ | ❌ |
| View Expenses | ✅ | ❌ | ❌ | ✅ |
| Manage Expenses | ✅ | ❌ | ❌ | ❌ |
| Manage Employees | ✅ | ❌ | ❌ | ❌ |
| View Salaries | ✅ | ❌ | ❌ | ❌ |
| Financial Reports | ✅ | ❌ | ❌ | ✅ |
| Export Data | ✅ | ❌ | ❌ | ✅ |
| Delete Products | ✅ | ❌ | ❌ | ❌ |
| Delete Companies | ✅ | ❌ | ❌ | ❌ |
| System Settings | ✅ | ❌ | ❌ | ❌ |
| Audit Logs | ✅ | ❌ | ❌ | ❌ |
| Manage Users | ✅ | ❌ | ❌ | ❌ |

---

#### 1. OWNER (Role: `OWNER`)
**Full Control - Business Owner/Administrator**

**Permissions:**
- ✅ Full access to ALL features
- ✅ Create, read, update, delete bills
- ✅ Delete any records
- ✅ Access financial reports and analytics
- ✅ Manage employees (add, edit, delete, set salaries)
- ✅ View and manage all payments
- ✅ Access expense reports
- ✅ Delete companies, products, customers
- ✅ View audit logs
- ✅ Manage user accounts and roles
- ✅ Access system settings

**Use Case:** Business owner who needs complete control and oversight

---

#### 2. MANAGER (Role: `MANAGER`)
**Limited Administrative Access**

**Permissions:**
- ✅ Create and view bills
- ✅ View and edit bills (but NOT delete)
- ✅ View stock and inventory
- ✅ Update stock quantities
- ✅ View customer information
- ✅ Add new customers (retailers, wholesalers)
- ✅ View payment history
- ❌ CANNOT delete bills
- ❌ CANNOT access expense reports
- ❌ CANNOT manage employees
- ❌ CANNOT delete any records
- ❌ CANNOT access system settings

**Use Case:** Store manager who handles daily operations but shouldn't have destructive permissions

---

#### 3. EMPLOYEE (Role: `EMPLOYEE`)
**Restricted Access - Regular Staff**

**Permissions:**
- ✅ Create new bills
- ✅ View bills (read-only)
- ✅ Print bills
- ✅ View stock levels
- ✅ View customer information
- ✅ Record payments (with owner approval workflow)
- ❌ CANNOT edit bills after creation
- ❌ CANNOT delete ANY records
- ❌ CANNOT access financial reports
- ❌ CANNOT view expenses
- ❌ CANNOT manage customers
- ❌ CANNOT access payment history
- ❌ CANNOT modify stock quantities

**Use Case:** Cashier or counter staff who only needs to create bills and view information

---

#### 4. ACCOUNTANT (Role: `ACCOUNTANT`)
**Financial Access Only**

**Permissions:**
- ✅ View all bills
- ✅ View and manage payments
- ✅ Access financial reports
- ✅ View expense reports
- ✅ Generate financial summaries
- ✅ Export financial data
- ❌ CANNOT create bills
- ❌ CANNOT delete bills
- ❌ CANNOT manage inventory
- ❌ CANNOT manage customers
- ❌ CANNOT delete any records

**Use Case:** Accountant who needs financial data access but shouldn't modify operational data

---

### Implementation Guide

#### Step 1: Update User Entity

Add role field to the User model:

```java
package com.DM.dairyManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @Email
    private String email;

    @NotEmpty
    private String mobile;

    @NotEmpty
    private String password;

    private String photoPath;
    private String verificationCode;
    
    // NEW: Role field for access control
    @Enumerated(EnumType.STRING)
    private Role role = Role.EMPLOYEE; // Default role
    
    // NEW: Active status (owner can deactivate employees)
    private boolean active = true;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhotoPath() { return photoPath; }
    public void setPhotoPath(String photoPath) { this.photoPath = photoPath; }

    public String getVerificationCode() { return verificationCode; }
    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }
    
    // NEW: Role getter and setter
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
    
    // NEW: Active status getter and setter
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}

// NEW: Role enum
enum Role {
    OWNER,      // Full access
    MANAGER,    // Limited admin
    EMPLOYEE,   // Restricted
    ACCOUNTANT  // Financial only
}
```

---

#### Step 2: Create Authorization Service

Create a service to handle permission checks:

```java
package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Role;
import com.DM.dairyManagement.model.User;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    /**
     * Check if user has permission to perform an action
     */
    public boolean hasPermission(User user, String action) {
        if (user == null) return false;
        if (!user.isActive()) return false; // Deactivated users have no access
        
        Role role = user.getRole();
        
        switch (action) {
            // DELETE actions - Only OWNER
            case "DELETE_BILL":
            case "DELETE_COMPANY":
            case "DELETE_PRODUCT":
            case "DELETE_CUSTOMER":
            case "DELETE_PAYMENT":
            case "DELETE_USER":
                return role == Role.OWNER;
                
            // EDIT actions - OWNER and MANAGER
            case "EDIT_BILL":
            case "EDIT_COMPANY":
            case "EDIT_PRODUCT":
            case "EDIT_STOCK":
            case "EDIT_CUSTOMER":
                return role == Role.OWNER || role == Role.MANAGER;
                
            // CREATE actions - OWNER, MANAGER, EMPLOYEE
            case "CREATE_BILL":
            case "CREATE_PAYMENT":
            case "CREATE_CUSTOMER":
                return role == Role.OWNER || role == Role.MANAGER || role == Role.EMPLOYEE;
                
            // VIEW actions - All roles
            case "VIEW_BILL":
            case "VIEW_STOCK":
            case "VIEW_CUSTOMER":
                return true;
                
            // FINANCIAL reports - Only OWNER and ACCOUNTANT
            case "VIEW_EXPENSES":
            case "VIEW_FINANCIAL_REPORTS":
            case "VIEW_PAYMENT_HISTORY":
            case "EXPORT_DATA":
                return role == Role.OWNER || role == Role.ACCOUNTANT;
                
            // EMPLOYEE management - Only OWNER
            case "MANAGE_EMPLOYEES":
            case "VIEW_SALARIES":
            case "EDIT_EMPLOYEE":
                return role == Role.OWNER;
                
            // SYSTEM settings - Only OWNER
            case "SYSTEM_SETTINGS":
            case "VIEW_AUDIT_LOGS":
            case "MANAGE_USERS":
                return role == Role.OWNER;
                
            default:
                return false;
        }
    }
    
    /**
     * Check if user is owner
     */
    public boolean isOwner(User user) {
        return user != null && user.getRole() == Role.OWNER;
    }
    
    /**
     * Check if user is employee (restricted access)
     */
    public boolean isEmployee(User user) {
        return user != null && user.getRole() == Role.EMPLOYEE;
    }
}
```

---

#### Step 3: Update Controllers with Authorization

Add permission checks to controllers:

```java
package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.model.User;
import com.DM.dairyManagement.service.AuthorizationService;
import com.DM.dairyManagement.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class BillController {

    @Autowired
    private BillService billService;
    
    @Autowired
    private AuthorizationService authorizationService;

    // VIEW bills - All roles can view
    @GetMapping("/listBill")
    public String viewBills(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        
        if (!authorizationService.hasPermission(user, "VIEW_BILL")) {
            return "redirect:/unauthorized";
        }
        
        model.addAttribute("bills", billService.getAllBills());
        model.addAttribute("userRole", user.getRole());
        return "listBill";
    }

    // CREATE bill - OWNER, MANAGER, EMPLOYEE
    @GetMapping("/createBill")
    public String showBillForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        
        if (!authorizationService.hasPermission(user, "CREATE_BILL")) {
            return "redirect:/unauthorized";
        }
        
        model.addAttribute("bill", new Bill());
        return "createBill";
    }

    // DELETE bill - Only OWNER
    @PostMapping("/delete-bill/{id}")
    public String deleteBill(@PathVariable Long id, 
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        User user = (User) session.getAttribute("loggedInUser");
        if (user == null) return "redirect:/login";
        
        if (!authorizationService.hasPermission(user, "DELETE_BILL")) {
            redirectAttributes.addFlashAttribute("error", 
                "❌ You don't have permission to delete bills. Only the owner can perform this action.");
            return "redirect:/listBill";
        }
        
        try {
            billService.deleteBillById(id);
            redirectAttributes.addFlashAttribute("success", "Bill deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting bill: " + e.getMessage());
        }
        return "redirect:/listBill";
    }
}
```

---

#### Step 4: Create Unauthorized Access Page

Create `src/main/resources/templates/unauthorized.html`:

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Access Denied - MilkMate</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow">
                    <div class="card-body text-center p-5">
                        <i class="fas fa-lock fa-5x text-danger mb-4"></i>
                        <h1 class="text-danger mb-3">Access Denied</h1>
                        <p class="lead mb-4">You don't have permission to access this resource.</p>
                        <div class="alert alert-warning">
                            <i class="fas fa-exclamation-triangle me-2"></i>
                            This action requires higher privileges. Please contact the owner if you need access.
                        </div>
                        <div class="mt-4">
                            <a href="/dashboard" class="btn btn-primary me-2">
                                <i class="fas fa-home me-2"></i>Go to Dashboard
                            </a>
                            <a href="javascript:history.back()" class="btn btn-secondary">
                                <i class="fas fa-arrow-left me-2"></i>Go Back
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
```

---

#### Step 5: Update Login Controller

Add role checking during login:

```java
@PostMapping("/login")
public String loginUser(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model, HttpSession session) {

    email = email.trim();
    password = password.trim();
    
    User user = userService.getUserByEmail(email);
    
    if (user == null) {
        model.addAttribute("error", "Invalid email or password");
        return "login";
    }
    
    // Check if user account is active
    if (!user.isActive()) {
        model.addAttribute("error", "Your account has been deactivated. Please contact the owner.");
        return "login";
    }
    
    // Verify password
    if (passwordEncoder.matches(password, user.getPassword())) {
        session.setAttribute("loggedInUser", user);
        session.setAttribute("userRole", user.getRole());
        
        System.out.println("Login successful: " + user.getEmail() + " | Role: " + user.getRole());
        return "redirect:/dashboard";
    } else {
        model.addAttribute("error", "Invalid email or password");
        return "login";
    }
}
```

---

#### Step 6: Update UI Based on Role

Modify templates to show/hide buttons based on user role:

**listBill.html - Show Delete button only for OWNER:**

```html
<td class="action-column">
    <div class="btn-group">
        <!-- Edit button - OWNER and MANAGER -->
        <a th:if="${session.loggedInUser.role == 'OWNER' or session.loggedInUser.role == 'MANAGER'}"
           th:href="@{/edit-bill/{id}(id=${bill.id})}" 
           class="btn btn-warning btn-sm" title="Edit">
            <i class="fas fa-edit"></i>
        </a>
        
        <!-- Print button - All roles -->
        <a th:href="@{/PrintBill/{id}(id=${bill.id})}" target="_blank" 
           class="btn btn-primary btn-sm" title="Print">
            <i class="fas fa-print"></i>
        </a>
        
        <!-- Delete button - Only OWNER -->
        <form th:if="${session.loggedInUser.role == 'OWNER'}"
              th:action="@{/delete-bill/{id}(id=${bill.id})}" 
              method="post" style="display:inline;">
            <button type="submit" class="btn btn-danger btn-sm" title="Delete" 
                    onclick="return confirm('Are you sure you want to delete this bill?')">
                <i class="fas fa-trash-alt"></i>
            </button>
        </form>
    </div>
</td>
```

---

#### Step 7: Create Employee Management Page

Create a page for owners to manage employees:

```html
<!-- src/main/resources/templates/manage-employees.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Manage Employees - MilkMate</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h2><i class="fas fa-users-cog me-2"></i>Manage Employees</h2>
        
        <!-- Add Employee Form -->
        <div class="card mb-4">
            <div class="card-header bg-primary text-white">
                <h5 class="mb-0">Add New Employee</h5>
            </div>
            <div class="card-body">
                <form th:action="@{/employees/add}" method="post">
                    <div class="row">
                        <div class="col-md-3">
                            <label>Name</label>
                            <input type="text" name="name" class="form-control" required>
                        </div>
                        <div class="col-md-3">
                            <label>Email</label>
                            <input type="email" name="email" class="form-control" required>
                        </div>
                        <div class="col-md-2">
                            <label>Role</label>
                            <select name="role" class="form-control" required>
                                <option value="EMPLOYEE">Employee</option>
                                <option value="MANAGER">Manager</option>
                                <option value="ACCOUNTANT">Accountant</option>
                            </select>
                        </div>
                        <div class="col-md-2">
                            <label>Password</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <div class="col-md-2">
                            <label>&nbsp;</label>
                            <button type="submit" class="btn btn-success d-block w-100">
                                <i class="fas fa-plus me-2"></i>Add
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        
        <!-- Employee List -->
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr th:each="emp : ${employees}">
                    <td th:text="${emp.name}"></td>
                    <td th:text="${emp.email}"></td>
                    <td>
                        <span th:class="${emp.role == 'OWNER' ? 'badge bg-danger' : 
                                          emp.role == 'MANAGER' ? 'badge bg-warning' : 
                                          emp.role == 'ACCOUNTANT' ? 'badge bg-info' : 'badge bg-secondary'}"
                              th:text="${emp.role}"></span>
                    </td>
                    <td>
                        <span th:class="${emp.active ? 'badge bg-success' : 'badge bg-danger'}"
                              th:text="${emp.active ? 'Active' : 'Deactivated'}"></span>
                    </td>
                    <td>
                        <!-- Deactivate/Activate button - Only for non-owners -->
                        <form th:if="${emp.role != 'OWNER'}" th:action="@{/employees/toggle-status}" method="post" style="display:inline;">
                            <input type="hidden" name="employeeId" th:value="${emp.id}">
                            <button type="submit" class="btn btn-sm" 
                                    th:classappend="${emp.active ? 'btn-warning' : 'btn-success'}"
                                    th:text="${emp.active ? 'Deactivate' : 'Activate'}">
                            </button>
                        </form>
                        <span th:if="${emp.role == 'OWNER'}" class="text-muted">Cannot modify owner</span>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>
```

---

#### Step 8: Add Audit Logging

Track all critical actions:

```java
package com.DM.dairyManagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String userEmail;
    private String userName;
    private String action; // e.g., "DELETE_BILL", "CREATE_PAYMENT"
    private String entityType; // e.g., "Bill", "Payment"
    private Long entityId;
    private String details;
    private LocalDateTime timestamp;
    private String ipAddress;
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    
    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }
    
    public Long getEntityId() { return entityId; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }
    
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
}
```

**Audit Log Service:**

```java
@Service
public class AuditLogService {
    
    @Autowired
    private AuditLogRepository auditLogRepository;
    
    public void logAction(User user, String action, String entityType, 
                         Long entityId, String details, String ipAddress) {
        AuditLog log = new AuditLog();
        log.setUserEmail(user.getEmail());
        log.setUserName(user.getName());
        log.setAction(action);
        log.setEntityType(entityType);
        log.setEntityId(entityId);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        log.setIpAddress(ipAddress);
        
        auditLogRepository.save(log);
    }
}
```

**Usage in Controller:**

```java
@PostMapping("/delete-bill/{id}")
public String deleteBill(@PathVariable Long id, 
                        HttpSession session,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {
    User user = (User) session.getAttribute("loggedInUser");
    
    if (!authorizationService.hasPermission(user, "DELETE_BILL")) {
        redirectAttributes.addFlashAttribute("error", "Permission denied!");
        return "redirect:/listBill";
    }
    
    try {
        billService.deleteBillById(id);
        
        // Log the deletion
        auditLogService.logAction(
            user,
            "DELETE_BILL",
            "Bill",
            id,
            "Deleted bill #" + id,
            request.getRemoteAddr()
        );
        
        redirectAttributes.addFlashAttribute("success", "Bill deleted successfully!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
    }
    
    return "redirect:/listBill";
}
```

---

### Security Best Practices

#### 1. Prevent Employee Theft
- **Disable delete permissions** for employees
- **Require owner approval** for refunds or voided bills
- **Log all financial transactions** with user details
- **Daily reconciliation reports** sent to owner

#### 2. Session Security
```java
// Set session timeout (30 minutes)
server.servlet.session.timeout=30m

// Secure session cookies
server.servlet.session.cookie.secure=true
server.servlet.session.cookie.http-only=true
```

#### 3. Password Policies
```java
// Enforce strong passwords for employees
public boolean isPasswordStrong(String password) {
    return password.length() >= 8 &&
           password.matches(".*[A-Z].*") &&
           password.matches(".*[a-z].*") &&
           password.matches(".*[0-9].*") &&
           password.matches(".*[@#$%^&+=].*");
}
```

#### 4. Regular Audits
- Review audit logs weekly
- Check for unusual activity patterns
- Verify employee access levels monthly
- Deactivate former employees immediately

---

### Quick Setup Example

**Create an employee account:**

```java
// In DataInitializationService or via admin panel
User employee = new User();
employee.setName("John Cashier");
employee.setEmail("john@dairy.com");
employee.setMobile("9876543210");
employee.setPassword(passwordEncoder.encode("employee123"));
employee.setRole(Role.EMPLOYEE); // Restricted access
employee.setActive(true);
userService.saveUser(employee);
```

**Create a manager account:**

```java
User manager = new User();
manager.setName("Sarah Manager");
manager.setEmail("sarah@dairy.com");
manager.setMobile("9876543211");
manager.setPassword(passwordEncoder.encode("manager123"));
manager.setRole(Role.MANAGER); // Limited admin
manager.setActive(true);
userService.saveUser(manager);
```

---

### Testing Role-Based Access

**Test scenarios:**

1. ✅ Login as EMPLOYEE → Try to delete bill → Should be denied
2. ✅ Login as EMPLOYEE → Try to access expenses → Should be denied
3. ✅ Login as MANAGER → Edit bill → Should be allowed
4. ✅ Login as MANAGER → Delete bill → Should be denied
5. ✅ Login as OWNER → All actions → Should be allowed
6. ✅ Deactivate employee → Try to login → Should be denied

---

## 🔌 API Endpoints

### Authentication
```
GET  /signup                 - Show registration page
POST /signup                 - Register new user
GET  /login                  - Show login page
POST /login                  - Authenticate user
GET  /logout                 - Logout current user
GET  /edit-profile           - Show profile edit page
POST /edit-profile           - Update user profile
GET  /unauthorized           - Access denied page
```

### Employee Management (OWNER only)
```
GET  /employees              - List all employees
GET  /employees/add          - Show add employee form
POST /employees/add          - Create new employee
POST /employees/toggle-status - Activate/Deactivate employee
GET  /employees/{id}/edit    - Edit employee details
POST /employees/{id}/update  - Update employee
GET  /audit-logs             - View audit logs (OWNER only)
```

### Dashboard
```
GET  /dashboard              - Main dashboard with analytics
GET  /home                   - Home page redirect
```

### Bill Management
```
GET  /createBill             - Show create bill form
POST /createBill             - Create new bill
GET  /listBill               - List all bills
GET  /edit-bill/{id}         - Show edit bill form
POST /update-bill            - Update bill
POST /delete-bill/{id}       - Delete bill
GET  /PrintBill/{id}         - Print bill
GET  /next-bill-no           - Get next bill number
GET  /customers              - Customer bills
GET  /retailers              - Retailer bills
GET  /wholesalers            - Wholesaler bills
```

### Company Management
```
GET  /companies              - List all companies
GET  /companies/new          - Show add company form
POST /companies              - Create company
GET  /companies/{id}/edit    - Show edit form
PUT  /companies/{id}         - Update company
DELETE /companies/{id}       - Delete company
```

### Product Management
```
GET  /products               - List all products
GET  /products/new           - Show add product form
POST /products               - Create product
GET  /products/{id}/edit     - Show edit form
PUT  /products/{id}          - Update product
DELETE /products/{id}        - Delete product
```

### Stock Management
```
GET  /stockForm              - Show stock form
GET  /stockList              - List stock levels
POST /stocks                 - Update stock
```

### Payment Management
```
GET  /payments/form          - Show payment form
POST /payments               - Record payment
GET  /payments/list          - List all payments
GET  /payments/{id}/edit     - Edit payment
GET  /payment-history/{id}   - Payment history
```

### Unit Management
```
GET  /units                  - List all units
GET  /units/new              - Show add unit form
POST /units                  - Create unit
GET  /units/{id}/edit        - Edit unit
PUT  /units/{id}             - Update unit
DELETE /units/{id}           - Delete unit
```

### Employee Expenses
```
GET  /employee-expenses      - View employee expenses
POST /employee-expenses      - Add employee
GET  /employees/{id}/edit    - Edit employee
```

### Other Expenses
```
GET  /other-expenses         - View other expenses
POST /other-expenses         - Add expense
GET  /other-expenses/{id}/edit - Edit expense
```

### Total Expenses
```
GET  /total-expenses         - View consolidated expenses
```

---

## 📊 Database Schema

### Entity Relationships

```
User 1──┐
        ├─► Company 1──┐
Product *──┘            ├─► Retailer *
Unit 1──┐               ├─► Wholesaler *
        ├─► Product *───┘
Stock 1──┘

Bill *──► Payment *
Customer 1──┘
```

### Core Tables

#### users
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    mobile VARCHAR(20) NOT NULL,
    password VARCHAR(255) NOT NULL,
    photo_path VARCHAR(255),
    verification_code VARCHAR(255)
);
```

#### companies
```sql
CREATE TABLE companies (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    contact_person VARCHAR(255),
    contact_no BIGINT,
    products TEXT,
    remark TEXT,
    is_deleted BOOLEAN DEFAULT FALSE
);
```

#### units
```sql
CREATE TABLE units (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    kg FLOAT,
    ltr FLOAT
);
```

#### products
```sql
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    company_id BIGINT,
    unit1_id BIGINT,
    customer_price BIGINT,
    retailer_price BIGINT,
    wholesaler_price BIGINT,
    company_price BIGINT,
    FOREIGN KEY (company_id) REFERENCES companies(id),
    FOREIGN KEY (unit1_id) REFERENCES units(id)
);
```

#### stocks
```sql
CREATE TABLE stocks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT,
    quantity INT,
    returned_quantity VARCHAR(50),
    last_updated TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id)
);
```

#### bills
```sql
CREATE TABLE bills (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    bill_no BIGINT UNIQUE NOT NULL,
    full_name VARCHAR(255),
    mobile_number VARCHAR(20),
    date VARCHAR(50),
    customer_type VARCHAR(50),
    item VARCHAR(255),
    qty INT,
    price DOUBLE,
    subtotal DOUBLE,
    cgst DOUBLE,
    sgst DOUBLE,
    discount DOUBLE,
    total DOUBLE,
    paid_amount DOUBLE,
    balance_due DOUBLE
);
```

#### payments
```sql
CREATE TABLE payments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    bill_id BIGINT,
    name VARCHAR(255),
    amount DECIMAL(10,2),
    paid_amount DECIMAL(10,2),
    due_amount DECIMAL(10,2),
    payment_date TIMESTAMP,
    payment_method VARCHAR(50),
    upi_id VARCHAR(255),
    credit_card_number VARCHAR(255),
    account_number VARCHAR(255),
    payment_status VARCHAR(50)
);
```

#### retailers
```sql
CREATE TABLE retailers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    mobile_no BIGINT,
    email VARCHAR(255),
    vehicle_no VARCHAR(100),
    company_id BIGINT,
    wholesaler_id BIGINT,
    adhar_card_path VARCHAR(255),
    licence_copy_path VARCHAR(255),
    passport_photo_path VARCHAR(255),
    FOREIGN KEY (company_id) REFERENCES companies(id),
    FOREIGN KEY (wholesaler_id) REFERENCES wholesalers(id)
);
```

#### wholesalers
```sql
CREATE TABLE wholesalers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    mobile_no BIGINT,
    email VARCHAR(255),
    vehicle_no VARCHAR(100),
    company_id BIGINT,
    adhar_card_path VARCHAR(255),
    licence_copy_path VARCHAR(255),
    passport_photo_path VARCHAR(255),
    FOREIGN KEY (company_id) REFERENCES companies(id)
);
```

#### employees
```sql
CREATE TABLE employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    salary DOUBLE,
    advance_salary DOUBLE,
    remaining_salary DOUBLE
);
```

#### other_expenses
```sql
CREATE TABLE other_expenses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    date DATE,
    shop_rent DOUBLE,
    light_bill DOUBLE,
    diesel_expense DOUBLE,
    other_expense DOUBLE,
    total_amount DOUBLE
);
```

---

## 💻 Development Guide

### Setting Up Development Environment

#### 1. Import Project in IDE

**IntelliJ IDEA:**
```
1. File → Open → Select project folder
2. Select "pom.xml" → Open as Project
3. Wait for Maven to download dependencies
4. Run → Edit Configurations → Add Spring Boot
5. Main class: com.DM.dairyManagement.DairyManagementApplication
```

**Eclipse:**
```
1. File → Import → Maven → Existing Maven Projects
2. Select project root
3. Right-click project → Maven → Update Project
4. Run as → Spring Boot App
```

**VS Code:**
```
1. Install extensions:
   - Extension Pack for Java
   - Spring Boot Extension Pack
2. File → Open Folder → Select project
3. Open DairyManagementApplication.java
4. Click Run button
```

### Code Architecture

#### Layer Architecture
```
Presentation Layer (Controllers)
        ↓
Business Layer (Services)
        ↓
Data Access Layer (Repositories)
        ↓
Database (Entities)
```

#### Adding a New Feature

**Step 1: Create Entity (if needed)**
```java
package com.DM.dairyManagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "your_table")
public class YourEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Fields
    private String name;
    
    // Getters and Setters
}
```

**Step 2: Create Repository**
```java
package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.YourEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YourEntityRepository extends JpaRepository<YourEntity, Long> {
    // Custom query methods
    YourEntity findByName(String name);
}
```

**Step 3: Create Service**
```java
package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.YourEntity;
import com.DM.dairyManagement.repository.YourEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YourEntityService {
    
    @Autowired
    private YourEntityRepository repository;
    
    public List<YourEntity> getAll() {
        return repository.findAll();
    }
    
    public YourEntity getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Not found!"));
    }
    
    public YourEntity save(YourEntity entity) {
        return repository.save(entity);
    }
    
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
```

**Step 4: Create Controller**
```java
package com.DM.dairyManagement.controller;

import com.DM.dairyManagement.model.YourEntity;
import com.DM.dairyManagement.service.YourEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/your-entity")
public class YourEntityController {
    
    @Autowired
    private YourEntityService service;
    
    @GetMapping
    public String list(Model model) {
        model.addAttribute("entities", service.getAll());
        return "your-entity/list";
    }
    
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("entity", new YourEntity());
        return "your-entity/form";
    }
    
    @PostMapping
    public String save(YourEntity entity) {
        service.save(entity);
        return "redirect:/your-entity";
    }
}
```

**Step 5: Create Thymeleaf Templates**
```html
<!-- src/main/resources/templates/your-entity/list.html -->
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Your Entity List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-4">
        <h1>Your Entity List</h1>
        <a href="/your-entity/new" class="btn btn-primary mb-3">Add New</a>
        <table class="table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr th:each="entity : ${entities}">
                    <td th:text="${entity.id}"></td>
                    <td th:text="${entity.name}"></td>
                    <td>
                        <a th:href="@{/your-entity/edit/{id}(id=${entity.id})}" class="btn btn-sm btn-warning">Edit</a>
                        <a th:href="@{/your-entity/delete/{id}(id=${entity.id})}" class="btn btn-sm btn-danger">Delete</a>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</body>
</html>
```

### Code Conventions

#### Naming Conventions
- **Classes**: PascalCase (e.g., `UserService`)
- **Methods**: camelCase (e.g., `getAllUsers()`)
- **Variables**: camelCase (e.g., `userName`)
- **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_USERS`)
- **Packages**: lowercase (e.g., `com.DM.dairyManagement.service`)

#### Commit Messages
```
feat: add new feature
fix: fix bug
docs: update documentation
style: code style changes
refactor: code refactoring
test: add tests
chore: maintenance tasks
```

---

## 🧪 Testing

### Run All Tests
```bash
mvn test
```

### Run Specific Test
```bash
mvn -Dtest=UserServiceTest test
```

### Test Structure
```
src/test/java/com/DM/dairyManagement/
├── service/
│   ├── UserServiceTest.java
│   ├── ProductServiceTest.java
│   └── BillServiceTest.java
├── controller/
│   ├── UserControllerTest.java
│   └── BillControllerTest.java
└── repository/
    └── UserRepositoryTest.java
```

### Example Unit Test
```java
package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.User;
import com.DM.dairyManagement.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void testSaveUser() {
        // Given
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setMobile("1234567890");
        user.setPassword("password");
        
        // When
        User savedUser = userService.saveUser(user);
        
        // Then
        assertNotNull(savedUser.getId());
        assertEquals("Test User", savedUser.getName());
        assertEquals("test@example.com", savedUser.getEmail());
    }
    
    @Test
    void testGetUserByEmail() {
        // Given
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setMobile("1234567890");
        user.setPassword("password");
        userRepository.save(user);
        
        // When
        User foundUser = userService.getUserByEmail("test@example.com");
        
        // Then
        assertNotNull(foundUser);
        assertEquals("Test User", foundUser.getName());
    }
}
```

---

## 🚀 Deployment

### Production Checklist

#### 1. Update Configuration
```properties
# Change to production database
spring.datasource.url=jdbc:mysql://your-production-db:3306/dairy_management
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

# Disable H2 console
spring.h2.console.enabled=false

# Update JPA settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Enable caching
spring.thymeleaf.cache=true
```

#### 2. Set Environment Variables
```bash
# Linux/Mac
export DB_URL=jdbc:mysql://localhost:3306/dairy_management
export DB_USERNAME=prod_user
export DB_PASSWORD=secure_password
export MAIL_USERNAME=noreply@yourcompany.com
export MAIL_PASSWORD=app_password
export ADMIN_EMAIL=admin@yourcompany.com
export ADMIN_PASSWORD=secure_admin_password

# Windows (PowerShell)
$env:DB_URL="jdbc:mysql://localhost:3306/dairy_management"
$env:DB_USERNAME="prod_user"
$env:DB_PASSWORD="secure_password"
```

#### 3. Build for Production
```bash
# Clean build
mvn clean package -DskipTests

# Or with tests
mvn clean package
```

#### 4. Run JAR
```bash
java -jar target/dairyManagement-0.0.1-SNAPSHOT.jar
```

### Docker Deployment

#### Create Dockerfile
```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/dairyManagement-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Build and Run Docker
```bash
# Build image
docker build -t milkmate .

# Run container
docker run -d -p 8080:8080 \
  -e DB_URL=jdbc:mysql://host.docker.internal:3306/dairy_management \
  -e DB_USERNAME=dairy_user \
  -e DB_PASSWORD=your_password \
  milkmate
```

### Cloud Deployment

#### Heroku
```bash
# Login to Heroku
heroku login

# Create app
heroku create your-app-name

# Set config vars
heroku config:set DB_URL=jdbc:mysql://...
heroku config:set DB_USERNAME=...
heroku config:set DB_PASSWORD=...

# Deploy
git push heroku main
```

#### AWS Elastic Beanstalk
```bash
# Install EB CLI
pip install awsebcli

# Initialize
eb init

# Create environment
eb create production

# Deploy
eb deploy
```

---

## 🔧 Troubleshooting

### Common Issues

#### 1. Application Won't Start

**Error**: `Port 8080 already in use`
```bash
# Solution 1: Change port in application.properties
server.port=8081

# Solution 2: Kill process using port 8080
# Windows
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# Mac/Linux
lsof -i :8080
kill -9 <PID>
```

**Error**: `Java version mismatch`
```bash
# Check Java version
java -version

# Install correct Java version (17+)
# Download from: https://adoptium.net/
```

#### 2. Database Connection Issues

**Error**: `Communications link failure`
```bash
# Check if MySQL is running
# Windows: services.msc → MySQL → Start
# Mac: brew services start mysql
# Linux: sudo systemctl start mysql

# Test connection
mysql -u username -p -h localhost dairy_management
```

**Error**: `Table doesn't exist`
```properties
# In application.properties, change:
spring.jpa.hibernate.ddl-auto=update
# This will create tables if they don't exist
```

#### 3. Email Not Working

**Error**: `Authentication failed`
```
Solution:
1. Enable 2-Step Verification in Google Account
2. Generate App Password:
   - Go to Google Account → Security
   - App passwords → Generate
3. Use the 16-character password in .env file
```

#### 4. File Upload Issues

**Error**: `File size exceeds limit`
```properties
# Increase limits in application.properties
spring.servlet.multipart.max-file-size=20MB
spring.servlet.multipart.max-request-size=25MB
```

**Error**: `Upload directory not found`
```bash
# Create uploads directory
mkdir uploads

# Or update in application.properties
file.upload-dir=/tmp/uploads
```

#### 5. Login Problems

**Error**: `Invalid email or password`
```bash
# Check if admin user exists
# Access H2 console: http://localhost:8080/h2-console
# Run: SELECT * FROM users WHERE email='admin@gmail.com';

# If not exists, it will be created on first login
# Ensure .env file has correct ADMIN_EMAIL and ADMIN_PASSWORD
```

#### 6. Multi-Login & Role-Based Access Issues

**Error**: `Employee can still delete bills`
```sql
-- Check user role in database
SELECT email, role, active FROM users WHERE email='employee@email.com';

-- Should show: role='EMPLOYEE', active=true
-- If role is 'OWNER', update it:
UPDATE users SET role='EMPLOYEE' WHERE email='employee@email.com';
```

**Error**: `All users have full access`
```
Solution:
1. Ensure role field exists in users table
2. Check AuthorizationService is being called in controllers
3. Verify session attribute 'userRole' is set during login
4. Test with: System.out.println(user.getRole()) in controller
```

**Error**: `Unauthorized access page not showing`
```
Solution:
1. Ensure unauthorized.html exists in src/main/resources/templates/
2. Check controller returns "redirect:/unauthorized" on permission denial
3. Verify @GetMapping("/unauthorized") endpoint exists
```

**Error**: `Employee cannot create bills`
```
Solution:
1. Check user role: SELECT role FROM users WHERE email='employee@email.com';
2. Should be 'EMPLOYEE', 'MANAGER', or 'OWNER'
3. Verify AuthorizationService.hasPermission(user, "CREATE_BILL") returns true
4. Check console logs for permission check results
```

**Error**: `Owner account deactivated by mistake`
```sql
-- Reactivate owner account directly in database
UPDATE users SET active=true WHERE email='admin@gmail.com' AND role='OWNER';

-- NOTE: Always keep at least one OWNER account active!
```

**Error**: `Audit logs not being created`
```sql
-- Check if audit_logs table exists
SHOW TABLES LIKE 'audit_logs';

-- If not, Hibernate should create it with:
spring.jpa.hibernate.ddl-auto=update

-- Manually create if needed:
CREATE TABLE audit_logs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255),
    user_name VARCHAR(255),
    action VARCHAR(100),
    entity_type VARCHAR(100),
    entity_id BIGINT,
    details TEXT,
    timestamp TIMESTAMP,
    ip_address VARCHAR(50)
);
```

### Debug Mode

Enable detailed logging:
```properties
# In application.properties
logging.level.com.DM.dairyManagement=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
logging.level.root=INFO
```

### View Logs

```bash
# Run with console logging
mvn spring-boot:run

# Or redirect to file
java -jar target/dairyManagement-0.0.1-SNAPSHOT.jar > app.log 2>&1

# View logs
tail -f app.log
```

---

## 📞 Support

### Getting Help

1. **Documentation**: Check this document and other markdown files in the project
2. **GitHub Issues**: https://github.com/vishaltandale/Milkmate/issues
3. **Email**: Contact the development team

### Additional Resources

- **INSTALLATION_GUIDE.md**: Detailed installation steps
- **USER_MANUAL.md**: End-user guide
- **DEVELOPMENT_GUIDE.md**: Developer documentation
- **ARCHITECTURE.md**: System architecture details
- **CONTRIBUTING.md**: How to contribute

---

## 🚀 Modernization Guide

For detailed instructions on upgrading from the legacy Thymeleaf/Bootstrap stack to a modern React/Tailwind architecture with production-ready patterns, see:

📄 **[MODERNIZATION_GUIDE.md](MODERNIZATION_GUIDE.md)**

This comprehensive guide includes:
- ✅ Complete UI/UX Design System (colors, typography, spacing, components)
- ✅ React + TypeScript setup and architecture
- ✅ Step-by-step migration strategy (Strangler Pattern)
- ✅ Backend improvements (DTOs, mappers, exception handling, pagination)
- ✅ Database optimizations (indexes, constraints, data type fixes)
- ✅ Implementation checklist (7-week timeline)
- ✅ Best practices for performance, security, and testing

### Quick Upgrade Path

**Current Stack → Modern Stack:**
```
Thymeleaf → React 18 + TypeScript
Bootstrap 5 → Tailwind CSS 3.4
Vanilla JS → Zustand + React Hooks
Chart.js → Recharts
Font Awesome → Lucide Icons
Server-side → SPA with API
```

**Timeline:** 6-8 weeks with Strangler Pattern (zero downtime)

---

## 🎉 Quick Start Summary

```bash
# 1. Clone repository
git clone https://github.com/vishaltandale/Milkmate.git
cd Milkmate

# 2. Create .env file (copy from .env.example)
# Edit with your configuration

# 3. Build and run
# Windows
.\mvnw.cmd spring-boot:run

# Mac/Linux
./mvnw spring-boot:run

# 4. Open browser
http://localhost:8080

# 5. Login with different roles

# OWNER (Full access - can delete, manage employees, view all reports)
Email: admin@gmail.com
Password: pass

# EMPLOYEE (Restricted - can only create bills, view stock)
Email: employee@gmail.com
Password: employee123

# 6. Test role-based access
# - Login as EMPLOYEE → Try to delete bill → Should be denied
# - Login as OWNER → All actions allowed
# - Check audit logs for all critical actions
```

### 🛡️ Security Features
- ✅ Role-based access control (OWNER, MANAGER, EMPLOYEE, ACCOUNTANT)
- ✅ Employees cannot delete bills or access financial reports
- ✅ All critical actions logged in audit trail
- ✅ Owner can deactivate employee accounts
- ✅ Session-based authentication with timeout
- ✅ Password encryption with BCrypt

### 🚀 Production-Ready Features
- ✅ Multi-item bill support (industry standard)
- ✅ GST-compliant invoicing (Indian businesses)
- ✅ Customer credit management & payment reminders
- ✅ Advanced search & filtering (JPA Specifications)
- ✅ Pagination for all lists (performance optimized)
- ✅ Stock alerts & reorder points (prevent stockouts)
- ✅ Export to Excel, PDF, CSV (data portability)
- ✅ Sales analytics & reports (business insights)
- ✅ Bill preview before printing (reduce errors)
- ✅ BigDecimal for financial accuracy
- ✅ LocalDate for proper date operations

---

<div align="center">

# 🐄 MilkMate Dairy Management System

**Built with ❤️ for dairy businesses**

[Spring Boot](https://spring.io/projects/spring-boot) • [MySQL](https://www.mysql.com/) • [React](https://react.dev/) • [Tailwind CSS](https://tailwindcss.com/)

© 2024 MilkMate. Licensed under MIT License.

</div>
