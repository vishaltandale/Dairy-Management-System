-- ============================================
-- MilkMate Database Indexes Migration Script
-- Purpose: Improve query performance for large datasets
-- Database: MySQL / H2
-- Date: 2026-04-24
-- ============================================

-- Company Table Indexes
CREATE INDEX IF NOT EXISTS idx_company_name ON company(name);
CREATE INDEX IF NOT EXISTS idx_company_gstin ON company(gstin);

-- Product Table Indexes
CREATE INDEX IF NOT EXISTS idx_product_name ON product(name);
CREATE INDEX IF NOT EXISTS idx_product_company ON product(company_id);
CREATE INDEX IF NOT EXISTS idx_product_category ON product(category);

-- Employee Table Indexes
CREATE INDEX IF NOT EXISTS idx_employee_name ON employee(name);
CREATE INDEX IF NOT EXISTS idx_employee_email ON employee(email);
CREATE INDEX IF NOT EXISTS idx_employee_phone ON employee(phone);

-- Retailer Table Indexes
CREATE INDEX IF NOT EXISTS idx_retailer_name ON retailer(name);
CREATE INDEX IF NOT EXISTS idx_retailer_company ON retailer(company_id);
CREATE INDEX IF NOT EXISTS idx_retailer_phone ON retailer(phone);

-- Wholesaler Table Indexes
CREATE INDEX IF NOT EXISTS idx_wholesaler_name ON wholesaler(name);
CREATE INDEX IF NOT EXISTS idx_wholesaler_company ON wholesaler(company_id);
CREATE INDEX IF NOT EXISTS idx_wholesaler_phone ON wholesaler(phone);

-- Bill Table Indexes
CREATE INDEX IF NOT EXISTS idx_bill_number ON bill(bill_no);
CREATE INDEX IF NOT EXISTS idx_bill_date ON bill(bill_date);
CREATE INDEX IF NOT EXISTS idx_bill_customer ON bill(full_name);
CREATE INDEX IF NOT EXISTS idx_bill_customer_type ON bill(customer_type);
CREATE INDEX IF NOT EXISTS idx_bill_created_by ON bill(created_by);

-- Item Table Indexes (Bill Items)
CREATE INDEX IF NOT EXISTS idx_item_bill ON item(bill_id);
CREATE INDEX IF NOT EXISTS idx_item_product ON item(product_name);

-- Payment Table Indexes
CREATE INDEX IF NOT EXISTS idx_payment_bill ON payment(bill_id);
CREATE INDEX IF NOT EXISTS idx_payment_date ON payment(payment_date);
CREATE INDEX IF NOT EXISTS idx_payment_method ON payment(payment_method);

-- Payment History Table Indexes
CREATE INDEX IF NOT EXISTS idx_payment_history_bill ON payment_history(bill_id);
CREATE INDEX IF NOT EXISTS idx_payment_history_date ON payment_history(payment_date);

-- Stock Table Indexes
CREATE INDEX IF NOT EXISTS idx_stock_product ON stock(product_id);
CREATE INDEX IF NOT EXISTS idx_stock_quantity ON stock(quantity);
CREATE INDEX IF NOT EXISTS idx_stock_low_stock ON stock(quantity, reorder_level);

-- Other Expense Table Indexes
CREATE INDEX IF NOT EXISTS idx_expense_date ON other_expense(date);
CREATE INDEX IF NOT EXISTS idx_expense_category ON other_expense(category);
CREATE INDEX IF NOT EXISTS idx_expense_amount ON other_expense(amount);

-- User Table Indexes
CREATE INDEX IF NOT EXISTS idx_user_username ON user(username);
CREATE INDEX IF NOT EXISTS idx_user_email ON user(email);
CREATE INDEX IF NOT EXISTS idx_user_role ON user(role);

-- Sell Table Indexes
CREATE INDEX IF NOT EXISTS idx_sell_date ON sell(sell_date);
CREATE INDEX IF NOT EXISTS idx_sell_customer ON sell(customer_name);

-- Master Table Indexes
CREATE INDEX IF NOT EXISTS idx_master_type ON master(type);
CREATE INDEX IF NOT EXISTS idx_master_name ON master(name);

-- ============================================
-- Composite Indexes for Common Query Patterns
-- ============================================

-- Bill queries with date range and status
CREATE INDEX IF NOT EXISTS idx_bill_date_status ON bill(bill_date, payment_status);

-- Product search by company and name
CREATE INDEX IF NOT EXISTS idx_product_company_name ON product(company_id, name);

-- Payment queries by bill and date
CREATE INDEX IF NOT EXISTS idx_payment_bill_date ON payment(bill_id, payment_date);

-- Stock alerts (low stock items)
CREATE INDEX IF NOT EXISTS idx_stock_reorder ON stock(reorder_level, quantity);

-- ============================================
-- Verification Queries (Optional - Run to verify)
-- ============================================

-- SHOW INDEX FROM company;
-- SHOW INDEX FROM product;
-- SHOW INDEX FROM bill;
-- SHOW INDEX FROM payment;

-- ============================================
-- Notes:
-- ============================================
-- 1. Run this script after all tables are created
-- 2. Indexes improve read performance but may slow down writes slightly
-- 3. Monitor index usage and remove unused indexes
-- 4. Consider partitioning for very large tables (>1M rows)
-- 5. Update statistics after creating indexes: ANALYZE TABLE <table_name>;
-- ============================================
