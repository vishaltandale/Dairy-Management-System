# 👤 MilkMate User Manual

Complete end-user guide for the MilkMate Dairy Management System. This manual covers all features, workflows, and best practices for daily operations.

---

## 📋 Table of Contents
1. [Getting Started](#-getting-started)
2. [Login & Authentication](#-login--authentication)
3. [Dashboard Overview](#-dashboard-overview)
4. [Bill Management](#-bill-management)
5. [Product Management](#-product-management)
6. [Stock Management](#-stock-management)
7. [Customer Management](#-customer-management)
8. [Payment Management](#-payment-management)
9. [Expense Tracking](#-expense-tracking)
10. [Reports & Analytics](#-reports--analytics)
11. [User Profile](#-user-profile)
12. [Keyboard Shortcuts](#-keyboard-shortcuts)
13. [Troubleshooting](#-troubleshooting)
14. [Best Practices](#-best-practices)

---

## 🚀 Getting Started

### What is MilkMate?

MilkMate is a comprehensive **Dairy Business Management System** designed to help you manage all aspects of your dairy business, including:

- 📊 **Sales & Billing** - Create professional invoices
- 📦 **Inventory** - Track stock levels
- 👥 **Customers** - Manage retailers and wholesalers
- 💰 **Payments** - Record and track payments
- 📈 **Analytics** - Business insights and reports
- 👨‍💼 **Employees** - Staff management with role-based access

### System Access

**Web Address**: `http://localhost:8080` (local installation)  
**Supported Browsers**: Chrome, Firefox, Safari, Edge (latest versions)

### User Roles

| Role | Access Level | Best For |
|------|-------------|----------|
| **OWNER** | Full access to all features | Business owner/administrator |
| **MANAGER** | Can create/edit bills, manage stock | Store manager |
| **EMPLOYEE** | Can only create bills and view stock | Cashier/counter staff |
| **ACCOUNTANT** | Financial reports and payments only | Accountant/finance team |

**Note**: Your role determines what features you can access. Contact your administrator if you need different permissions.

---

## 🔐 Login & Authentication

### How to Login

1. **Open the application** in your browser
2. **Enter your credentials**:
   - **Email**: Your registered email address
   - **Password**: Your password (case-sensitive)
3. **Click "Login"** button

**Example:**
```
Email: admin@gmail.com
Password: pass
```

### First-Time Login

If this is your first time:
1. Use the default admin credentials provided by your administrator
2. **Change your password immediately** after first login
3. Update your profile information

### Forgot Password

1. Click **"Forgot Password?"** link on login page
2. Enter your registered email address
3. Check your email for password reset link
4. Click the link and create a new password
5. Login with new password

### Security Best Practices

✅ **DO:**
- Use strong passwords (minimum 8 characters)
- Keep your password confidential
- Logout when finished
- Change password regularly

❌ **DON'T:**
- Share your credentials
- Use simple passwords like "123456"
- Leave your session open on shared computers
- Write passwords on sticky notes

### Session Timeout

- Sessions automatically expire after **30 minutes** of inactivity
- You'll be redirected to login page
- Any unsaved work will be lost
- **Save your work frequently!**

---

## 📊 Dashboard Overview

The dashboard is your **command center** - it provides a quick snapshot of your business performance.

### Dashboard Components

```
┌─────────────────────────────────────────────────────┐
│  Welcome Message                                    │
│  "Good Morning, John! Here's your business overview"│
├─────────────────────────────────────────────────────┤
│                                                     │
│  Quick Stats (4 Cards)                             │
│  ┌──────────┐ ┌──────────┐ ┌──────────┐ ┌────────┐│
│  │Today's   │ │Total     │ │Low Stock │ │Total   ││
│  │Bills     │ │Sales     │ │Items     │ │Customers││
│  │  25      │ │₹45,230   │ │   8      │ │  156   ││
│  └──────────┘ └──────────┘ └──────────┘ └────────┘│
│                                                     │
├─────────────────────────────────────────────────────┤
│                                                     │
│  Charts Section                                     │
│  ┌─────────────────────┐ ┌─────────────────────┐  │
│  │ Sales Trend         │ │ Customer            │  │
│  │ (Last 30 days)      │ │ Distribution        │  │
│  │ [Line Chart]        │ │ [Pie Chart]         │  │
│  └─────────────────────┘ └─────────────────────┘  │
│                                                     │
├─────────────────────────────────────────────────────┤
│                                                     │
│  Recent Transactions                                │
│  ┌───────────────────────────────────────────┐    │
│  │ Bill#  │ Customer  │ Amount  │ Status    │    │
│  │ #1250  | John Doe  | ₹1,200  | Paid      │    │
│  │ #1249  | Jane Smith| ₹850    | Partial   │    │
│  └───────────────────────────────────────────┘    │
│                                                     │
└─────────────────────────────────────────────────────┘
```

### Understanding the Stats

**Today's Bills**: Number of bills created today  
**Total Sales**: Revenue generated (current month)  
**Low Stock Items**: Products running low (need reorder)  
**Total Customers**: Active customer count

### Using the Dashboard

**Quick Actions:**
- Click on any **stat card** to view detailed list
- Click **"Create Bill"** button for fast billing
- Click **"View All"** on recent transactions for full history

**Charts:**
- **Sales Trend**: Shows daily sales over last 30 days
- **Customer Distribution**: Shows customer types (Retailer/Wholesaler/Customer)
- **Hover** over charts for detailed information

**Filters:**
- Use **date range picker** to change time period
- Click **refresh** button to update data

---

## 🧾 Bill Management

Bills are the **core of your business**. This section covers creating, viewing, editing, and printing bills.

### Creating a New Bill

**Step-by-Step Guide:**

1. **Navigate to Bills**
   - Click **"Bills"** in sidebar
   - Click **"Create Bill"** button

2. **Enter Customer Information**
   ```
   Full Name: John Dairy Farm
   Mobile Number: 9876543210
   Customer Type: Retailer ▼
   ```

3. **Add Items**
   - Click **"Add Item"** button
   - Select product from dropdown
   - Enter quantity
   - Price auto-fills based on customer type
   - Subtotal calculates automatically

   **Example:**
   ```
   Item 1:
   - Product: Full Cream Milk
   - Quantity: 50
   - Price: ₹40.00
   - Subtotal: ₹2,000.00

   Item 2:
   - Product: Curd
   - Quantity: 20
   - Price: ₹60.00
   - Subtotal: ₹1,200.00
   ```

4. **Review Summary**
   ```
   Subtotal: ₹3,200.00
   CGST (9%): ₹288.00
   SGST (9%): ₹288.00
   Discount: ₹100.00 (optional)
   ─────────────────────
   Grand Total: ₹3,676.00
   ```

5. **Enter Payment**
   ```
   Paid Amount: ₹3,676.00 (full payment)
   OR
   Paid Amount: ₹2,000.00 (partial payment)
   Balance Due: ₹1,676.00 (auto-calculated)
   ```

6. **Save Bill**
   - Click **"Save Bill"** button
   - Success message appears
   - Redirected to bill list

### Viewing Bills

**Bill List Features:**

- **Search**: Find bills by customer name or bill number
- **Filter**: By customer type, date range, payment status
- **Sort**: Click column headers to sort
- **Pagination**: Navigate through pages (20 bills per page)

**Bill Actions:**

| Action | Icon | Description | Permission |
|--------|------|-------------|------------|
| **View** | 👁️ | View bill details | All roles |
| **Edit** | ✏️ | Modify bill | Owner, Manager |
| **Print** | 🖨️ | Print bill | All roles |
| **Delete** | 🗑️ | Remove bill | Owner only |

### Editing a Bill

**When to Edit:**
- Correct customer information
- Adjust quantities or prices
- Add/remove items
- Update payment amount

**Steps:**
1. Find the bill in list
2. Click **Edit** (pencil icon)
3. Make changes
4. Click **"Update Bill"**

**Note**: Stock levels will be automatically adjusted when you save changes.

### Printing a Bill

**Print Preview:**
1. Click **Print** button
2. Review bill in preview window
3. Check all details are correct
4. Click **"Print"** in browser dialog

**Print Settings:**
- Use **A4 size** paper
- **Portrait** orientation
- Enable **background graphics** for best results

### Bill Status

| Status | Meaning | Color |
|--------|---------|-------|
| **Paid** | Full payment received | 🟢 Green |
| **Partial** | Some payment received | 🟡 Yellow |
| **Unpaid** | No payment received | 🔴 Red |

---

## 📦 Product Management

Products are the items you sell. Manage your product catalog here.

### Adding a Product

**Required Information:**
```
Product Name: Full Cream Milk
Description: Fresh full cream milk (6% fat)
Company: Amul
Unit: Liter

Pricing:
- Customer Price: ₹45.00
- Retailer Price: ₹40.00
- Wholesaler Price: ₹38.00
- Company Price: ₹35.00
```

**Steps:**
1. Navigate to **Products** in sidebar
2. Click **"Add Product"**
3. Fill in all fields
4. Click **"Save"**

### Editing a Product

1. Find product in list
2. Click **Edit** button
3. Update information
4. Click **"Update"**

**Important**: Changing prices affects **future bills only**. Existing bills remain unchanged.

### Deleting a Product

**⚠️ Warning**: 
- Only **Owner** can delete products
- Cannot delete if product has **existing bills**
- Consider **deactivating** instead of deleting

### Product Categories

Organize products by:
- **Milk Products**: Full Cream, Toned, Skimmed Milk
- **Dairy Products**: Curd, Butter, Ghee, Paneer
- **Sweet Products**: Lassi, Buttermilk, Flavored Milk

---

## 📊 Stock Management

Track your inventory levels and prevent stockouts.

### Viewing Stock Levels

**Stock List Shows:**
```
┌──────────────────────────────────────────────┐
│ Product        │ Current Stock │ Status     │
├──────────────────────────────────────────────┤
│ Full Cream Milk│ 250 Ltr       | ✅ In Stock│
│ Toned Milk     | 45 Ltr        | ⚠️ Low     │
│ Curd           | 0 Kg          | ❌ Out      │
└──────────────────────────────────────────────┘
```

### Stock Status Indicators

| Status | Meaning | Action Required |
|--------|---------|-----------------|
| ✅ **In Stock** | Quantity > 50 | None |
| ⚠️ **Low Stock** | Quantity 10-50 | Plan reorder |
| ❌ **Out of Stock** | Quantity < 10 | Urgent reorder |

### Updating Stock

**Manual Stock Update:**
1. Go to **Stock Management**
2. Find the product
3. Click **"Update Stock"**
4. Enter new quantity
5. Add remark (optional): "Received from supplier"
6. Click **"Save"**

**Automatic Stock Deduction:**
- Stock automatically decreases when you create a bill
- No manual action needed
- Example: Create bill with 50L milk → Stock reduces by 50L

### Stock Alerts

**Dashboard Alert Box:**
```
⚠️ Low Stock Alerts (5)
• Toned Milk: 45 Ltr (Reorder at: 50)
• Curd: 8 Kg (Reorder at: 20)
• Butter: 12 Kg (Reorder at: 25)
```

**Email Notifications:**
- Daily stock alert emails (if configured)
- Sent at 9:00 AM
- Lists all low stock items

### Stock Returns

**Recording Returns:**
1. Navigate to **Stock**
2. Click **"Record Return"**
3. Select product
4. Enter return quantity
5. Add reason: "Customer return", "Damaged", etc.
6. Click **"Save"**

**Note**: Returns **increase** stock quantity.

---

## 👥 Customer Management

Manage your retailers and wholesalers in one place.

### Adding a Retailer

**Required Information:**
```
Name: Sharma Dairy Store
Mobile: 9876543210
Email: sharma@email.com (optional)
Vehicle No: MH12AB1234
Company: Amul
Wholesaler: Mumbai Distributors (optional)

Documents (optional):
- Aadhar Card: [Upload]
- License Copy: [Upload]
- Passport Photo: [Upload]

Credit Limit: ₹50,000 (optional)
```

**Steps:**
1. Go to **Retailers** in sidebar
2. Click **"Add Retailer"**
3. Fill in details
4. Upload documents (optional)
5. Click **"Save"**

### Adding a Wholesaler

Similar to retailers, but typically:
- Higher credit limits
- Bulk order quantities
- Different pricing tiers

### Viewing Customer Details

**Customer Profile Shows:**
- Contact information
- Transaction history
- Current balance (if any credit)
- Credit limit and available credit
- Document copies

### Editing Customers

1. Find customer in list
2. Click **Edit**
3. Update information
4. Click **"Update"**

### Customer Credit Management

**Credit Limit:**
- Maximum amount customer can owe
- Set when adding customer
- Can be updated anytime

**Available Credit:**
```
Available Credit = Credit Limit - Current Balance

Example:
Credit Limit: ₹50,000
Current Balance: ₹35,000
Available Credit: ₹15,000
```

**Credit Alerts:**
- Warning when customer exceeds 80% of credit limit
- Block new bills if credit limit exceeded
- Owner can override if needed

---

## 💰 Payment Management

Record and track all payments received.

### Recording a Payment

**When to Record:**
- Customer pays cash
- UPI/Bank transfer received
- Check deposited
- Any payment method

**Steps:**
1. Go to **Payments** in sidebar
2. Click **"Record Payment"**
3. Select bill (search by bill number or customer name)
4. Enter payment details:
   ```
   Bill: #1250 - John Dairy Farm
   Amount: ₹5,000
   Payment Method: Cash ▼
   Date: 2024-01-15
   ```
5. For digital payments, add reference:
   ```
   UPI ID: john@paytm
   OR
   Transaction ID: T20240115123456
   ```
6. Click **"Save Payment"**

### Payment Methods

| Method | When to Use | Additional Info |
|--------|-------------|-----------------|
| **Cash** | Direct cash payment | None required |
| **UPI** | PhonePe, GPay, Paytm | Enter UPI ID or reference |
| **Bank Transfer** | NEFT/RTGS/IMPS | Enter transaction ID |
| **Credit Card** | Card swipe machine | Last 4 digits only |
| **Debit Card** | Card swipe machine | Last 4 digits only |
| **Cheque** | Post-dated cheques | Cheque number |

### Viewing Payment History

**For a Specific Bill:**
1. Open bill details
2. Click **"Payment History"** tab
3. See all payments made against this bill

**All Payments:**
1. Go to **Payments**
2. Filter by date range
3. Filter by payment method
4. Export if needed

### Payment Status

**Bill Payment Status Updates Automatically:**
```
Paid Amount = Bill Total → Status: ✅ PAID
Paid Amount < Bill Total → Status: ⚠️ PARTIAL
Paid Amount = 0 → Status: ❌ UNPAID
```

---

## 📈 Expense Tracking

Track all business expenses to understand your true profitability.

### Employee Expenses

**Managing Employee Salaries:**

**Add Employee:**
```
Name: Rahul Sharma
Monthly Salary: ₹15,000
```

**Record Advance:**
1. Select employee
2. Click **"Give Advance"**
3. Enter amount: ₹5,000
4. Date: 2024-01-15
5. Click **"Save"**

**View Remaining Salary:**
```
Monthly Salary: ₹15,000
Advance Given: ₹5,000
─────────────────────
Remaining: ₹10,000
```

### Other Expenses

**Track Daily Expenses:**
```
Date: 2024-01-15

Shop Rent: ₹10,000 (monthly)
Electricity Bill: ₹3,500
Diesel Expense: ₹2,000 (generator/delivery)
Other Expense: ₹1,500 (miscellaneous)
─────────────────────────────
Total: ₹17,000
```

**Common Expense Categories:**
- Rent
- Electricity
- Water
- Diesel/Fuel
- Maintenance
- Transportation
- Office Supplies
- Miscellaneous

### Viewing Total Expenses

**Expense Dashboard:**
```
This Month's Expenses:

Employee Salaries: ₹45,000
Shop Rent: ₹10,000
Electricity: ₹3,500
Diesel: ₹6,000
Other: ₹4,500
─────────────────────
Total: ₹69,000
```

**Daily Expense Summary:**
- Navigate to **Total Expenses**
- Select date
- View breakdown
- Export to Excel if needed

---

## 📊 Reports & Analytics

Make data-driven decisions with comprehensive reports.

### Available Reports

| Report | Description | Access |
|--------|-------------|--------|
| **Sales Report** | Daily/monthly sales | All roles |
| **Customer Report** | Customer-wise sales | All roles |
| **Product Report** | Top-selling products | All roles |
| **Payment Report** | Payment collection | Owner, Accountant |
| **Expense Report** | Expense breakdown | Owner, Accountant |
| **Profit/Loss** | Revenue vs expenses | Owner only |

### Generating Reports

**Sales Report Example:**
1. Go to **Reports** → **Sales Report**
2. Select date range: "Last 30 Days"
3. Click **"Generate"**
4. View results:
   ```
   Total Sales: ₹4,50,000
   Total Bills: 450
   Average Bill: ₹1,000
   Best Day: Monday (₹85,000)
   ```

### Exporting Reports

**Export Formats:**
- **Excel** (.xlsx) - For further analysis
- **PDF** (.pdf) - For sharing/printing
- **CSV** (.csv) - For importing to other systems

**Steps:**
1. Generate report
2. Click **"Export"** button
3. Select format
4. Download file

### Understanding Analytics

**Sales Trends:**
- **Upward trend** 📈: Business growing
- **Downward trend** 📉: Investigate issues
- **Seasonal patterns**: Plan inventory accordingly

**Customer Analysis:**
- **Top customers**: Focus on retention
- **Inactive customers**: Re-engagement campaigns
- **New customers**: Acquisition tracking

**Product Performance:**
- **High sellers**: Keep in stock
- **Low sellers**: Consider discontinuing
- **Seasonal items**: Plan accordingly

---

## 👤 User Profile

Manage your account settings and preferences.

### Viewing Profile

1. Click your **name** in top-right corner
2. Select **"My Profile"**
3. View your information:
   ```
   Name: John Doe
   Email: john@dairy.com
   Mobile: 9876543210
   Role: Manager
   Member Since: January 2024
   ```

### Editing Profile

**Update Information:**
1. Click **"Edit Profile"**
2. Change details:
   - Name
   - Mobile number
   - Profile photo
3. Click **"Save Changes"**

**Note**: Email and role can only be changed by Owner.

### Changing Password

**Steps:**
1. Go to **Profile**
2. Click **"Change Password"**
3. Enter:
   ```
   Current Password: ********
   New Password: ********
   Confirm New Password: ********
   ```
4. Click **"Update Password"**

**Password Requirements:**
- Minimum 8 characters
- At least 1 uppercase letter
- At least 1 number
- At least 1 special character (@#$%&*)

### Profile Photo

**Upload Photo:**
1. Click **"Upload Photo"**
2. Select image file
3. Image should be:
   - Square aspect ratio (1:1)
   - Minimum 200x200 pixels
   - JPG or PNG format
   - Max size: 2MB
4. Click **"Save"**

---

## ⌨️ Keyboard Shortcuts

Speed up your work with keyboard shortcuts.

### Global Shortcuts

| Shortcut | Action |
|----------|--------|
| `Ctrl + N` | Create new bill |
| `Ctrl + S` | Save current form |
| `Ctrl + P` | Print current bill |
| `Ctrl + F` | Search/filter |
| `Ctrl + /` | Show keyboard shortcuts |
| `Esc` | Close modal/cancel |
| `Alt + D` | Go to Dashboard |
| `Alt + B` | Go to Bills |
| `Alt + P` | Go to Products |

### Bill Creation Shortcuts

| Shortcut | Action |
|----------|--------|
| `Tab` | Move to next field |
| `Shift + Tab` | Move to previous field |
| `Enter` | Add item / Save form |
| `Ctrl + Enter` | Save and print |
| `Delete` | Remove item row |

### Navigation Shortcuts

| Shortcut | Action |
|----------|--------|
| `←` | Previous page |
| `→` | Next page |
| `Home` | Go to first page |
| `End` | Go to last page |

---

## 🔧 Troubleshooting

Common issues and their solutions.

### Login Issues

**Problem**: "Invalid email or password"

**Solutions:**
1. Check caps lock is off
2. Verify email address spelling
3. Try resetting password
4. Contact administrator if account is deactivated

---

### Bill Creation Issues

**Problem**: "Insufficient stock" error

**Solution:**
1. Check current stock level
2. Update stock if needed
3. Reduce quantity in bill
4. Contact manager if stock discrepancy

---

**Problem**: Cannot save bill

**Solutions:**
1. Check all required fields are filled
2. Verify mobile number is 10 digits
3. Ensure at least 1 item is added
4. Check quantities are greater than 0

---

### Printing Issues

**Problem**: Bill doesn't print correctly

**Solutions:**
1. Use **Chrome** browser (best compatibility)
2. Enable **"Background graphics"** in print settings
3. Set paper size to **A4**
4. Use **Portrait** orientation
5. Try **"Save as PDF"** first, then print

---

### Stock Issues

**Problem**: Stock not updating

**Solutions:**
1. Refresh the page
2. Clear browser cache
3. Check if another user is editing same product
4. Contact administrator

---

### Performance Issues

**Problem**: Application is slow

**Solutions:**
1. Check internet connection
2. Close unnecessary browser tabs
3. Clear browser cache
4. Try refreshing page
5. Contact IT if issue persists

---

## 📝 Best Practices

Follow these tips for efficient daily operations.

### Daily Routine

**Morning:**
1. ✅ Check dashboard for low stock alerts
2. ✅ Review pending payments
3. ✅ Verify yesterday's bills are complete

**During Day:**
1. ✅ Create bills promptly
2. ✅ Record payments immediately
3. ✅ Update stock when receiving goods
4. ✅ Log expenses as they occur

**End of Day:**
1. ✅ Reconcile cash in hand
2. ✅ Verify all bills are saved
3. ✅ Check for any unsaved work
4. ✅ Logout properly

### Data Entry Tips

✅ **DO:**
- Enter customer mobile numbers correctly (needed for reminders)
- Use consistent naming for customers
- Add remarks for special transactions
- Save work frequently
- Double-check amounts before saving

❌ **DON'T:**
- Leave fields blank if information is available
- Use abbreviations inconsistently
- Delete records without confirmation
- Share your login credentials
- Keep session open when away

### Backup Recommendations

**Daily:**
- System automatically backs up to database
- No action needed

**Weekly:**
- Export important reports to Excel
- Save to external drive or cloud

**Monthly:**
- Full database backup (administrator task)
- Archive old bills and payments

### Security Reminders

🔒 **Always:**
- Use strong passwords
- Lock computer when away
- Report suspicious activity
- Logout at end of day

🚫 **Never:**
- Share passwords
- Leave bills unattended
- Delete records without authorization
- Access other users' accounts

---

## 📞 Getting Help

### Internal Support

1. **Check this manual** first
2. **Ask your supervisor** or manager
3. **Contact system administrator**

### External Support

- **Email**: support@milkmate.com
- **Documentation**: [Complete Documentation](COMPLETE_DOCUMENTATION.md)
- **Installation Guide**: [Installation Guide](INSTALLATION_GUIDE.md)

### Reporting Bugs

When reporting issues, include:
1. What you were trying to do
2. What happened instead
3. Error message (if any)
4. Screenshots (if possible)
5. Your browser and operating system

---

<div align="center">

**Thank you for using MilkMate! 🐄✨**

[Installation Guide](INSTALLATION_GUIDE.md) • [Architecture](ARCHITECTURE.md) • [Development Guide](DEVELOPMENT_GUIDE.md) • [Complete Documentation](COMPLETE_DOCUMENTATION.md)

</div>
