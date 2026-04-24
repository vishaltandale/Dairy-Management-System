import { Routes, Route, Navigate, Outlet } from 'react-router-dom'
import { useAuthStore } from './stores/authStore'
import { LoginPage } from './pages/auth/LoginPage'
import { DashboardPage } from './pages/DashboardPage'
import { BillsPage } from './pages/BillsPage'
import { CreateBillPage } from './pages/CreateBillPage'
import { ProductsPage } from './pages/ProductsPage'
import { PaymentsPage } from './pages/PaymentsPage'
import { CustomersPage } from './pages/CustomersPage'
import { CompaniesPage } from './pages/CompaniesPage'
import { ExpensesPage } from './pages/ExpensesPage'
import { ReportsPage } from './pages/ReportsPage'
import { EmployeesPage } from './pages/EmployeesPage'
import { StockAlertsPage } from './pages/StockAlertsPage'
import { MainLayout } from './components/layout/MainLayout'

function ProtectedRoute() {
  const { user } = useAuthStore()
  return user ? <MainLayout><Outlet /></MainLayout> : <Navigate to="/login" />
}

function AppRoutes() {
  const { user } = useAuthStore()

  return (
    <Routes>
      <Route path="/login" element={!user ? <LoginPage /> : <Navigate to="/dashboard" />} />
      
      <Route element={<ProtectedRoute />}>
        <Route path="/" element={<Navigate to="/dashboard" />} />
        <Route path="dashboard" element={<DashboardPage />} />
        <Route path="bills" element={<BillsPage />} />
        <Route path="bills/new" element={<CreateBillPage />} />
        <Route path="products" element={<ProductsPage />} />
        <Route path="payments" element={<PaymentsPage />} />
        <Route path="customers" element={<CustomersPage />} />
        <Route path="companies" element={<CompaniesPage />} />
        <Route path="expenses" element={<ExpensesPage />} />
        <Route path="employees" element={<EmployeesPage />} />
        <Route path="stock-alerts" element={<StockAlertsPage />} />
        <Route path="reports" element={<ReportsPage />} />
      </Route>
      
      <Route path="*" element={<Navigate to="/dashboard" />} />
    </Routes>
  )
}

export { AppRoutes }
