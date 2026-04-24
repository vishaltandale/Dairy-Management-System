import api from './api'

export interface LoginRequest {
  email: string
  password: string
}

export interface AuthResponse {
  success: boolean
  message: string
  data: {
    id: number
    name: string
    email: string
    role: string
  }
}

export const authService = {
  login: async (data: LoginRequest): Promise<{ user: AuthResponse['data'] }> => {
    const response = await api.post('/auth/login', data)
    return { user: response.data.data }
  },

  logout: async (): Promise<void> => {
    await api.post('/auth/logout')
  },

  getCurrentUser: async (): Promise<{ user: AuthResponse['data'] }> => {
    const response = await api.get('/auth/me')
    return { user: response.data.data }
  },
}

export interface BillItem {
  productName: string
  quantity: number
  price: number
  subtotal?: number
}

export interface Bill {
  id: number
  billNo: number
  fullName: string
  mobileNumber: string
  date: string
  customerType: string
  total: number
  paidAmount: number
  balanceDue: number
  items?: BillItem[]
  subtotal?: number
  cgst?: number
  sgst?: number
  discount?: number
}

export const billService = {
  getAll: async (search?: string, customerType?: string, status?: string): Promise<Bill[]> => {
    const response = await api.get('/bills', {
      params: { search, customerType, status }
    })
    return response.data.data || []
  },

  getById: async (id: number): Promise<Bill> => {
    const response = await api.get(`/bills/${id}`)
    return response.data.data
  },

  create: async (data: any): Promise<Bill> => {
    const response = await api.post('/bills', data)
    return response.data.data
  },

  update: async (id: number, data: any): Promise<Bill> => {
    const response = await api.put(`/bills/${id}`, data)
    return response.data.data
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/bills/${id}`)
  },
}

export interface Product {
  id: number
  name: string
  description: string
  companyName: string
  unitName: string
  customerPrice: number
  retailerPrice: number
  wholesalerPrice: number
  companyPrice: number
  hsnCode: string
  stockQuantity: number
}

export const productService = {
  getAll: async (): Promise<Product[]> => {
    const response = await api.get('/products')
    return response.data.data || []
  },
  
  getById: async (id: number): Promise<Product> => {
    const response = await api.get(`/products/${id}`)
    return response.data.data
  },

  create: async (data: Partial<Product>): Promise<Product> => {
    const response = await api.post('/products', data)
    return response.data.data
  },

  update: async (id: number, data: Partial<Product>): Promise<Product> => {
    const response = await api.put(`/products/${id}`, data)
    return response.data.data
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/products/${id}`)
  },
}

export interface Payment {
  id: number
  billId: number
  billNo: number
  customerName: string
  amount: number
  paymentMethod: string
  paymentDate: string
  referenceNumber: string
  remarks: string
}

export const paymentService = {
  getAll: async (): Promise<Payment[]> => {
    const response = await api.get('/payments')
    return response.data.data || []
  },

  getById: async (id: number): Promise<Payment> => {
    const response = await api.get(`/payments/${id}`)
    return response.data.data
  },

  create: async (data: Partial<Payment>): Promise<Payment> => {
    const response = await api.post('/payments', data)
    return response.data.data
  },

  getByBillId: async (billId: number): Promise<Payment[]> => {
    const response = await api.get(`/payments/bill/${billId}`)
    return response.data.data || []
  },
}

export interface Customer {
  id: number
  name: string
  mobileNumber: string
  email?: string
  vehicleNo?: string
  companyName?: string
  wholesalerName?: string
  creditLimit: number
  currentBalance: number
  customerType: 'RETAILER' | 'WHOLESALER'
}

export const customerService = {
  getAllRetailers: async (): Promise<Customer[]> => {
    const response = await api.get('/retailers')
    return response.data.data || []
  },

  getAllWholesalers: async (): Promise<Customer[]> => {
    const response = await api.get('/wholesalers')
    return response.data.data || []
  },

  createRetailer: async (data: Partial<Customer>): Promise<Customer> => {
    const response = await api.post('/retailers', data)
    return response.data.data
  },

  updateRetailer: async (id: number, data: Partial<Customer>): Promise<Customer> => {
    const response = await api.put(`/retailers/${id}`, data)
    return response.data.data
  },

  deleteRetailer: async (id: number): Promise<void> => {
    await api.delete(`/retailers/${id}`)
  },

  createWholesaler: async (data: Partial<Customer>): Promise<Customer> => {
    const response = await api.post('/wholesalers', data)
    return response.data.data
  },

  updateWholesaler: async (id: number, data: Partial<Customer>): Promise<Customer> => {
    const response = await api.put(`/wholesalers/${id}`, data)
    return response.data.data
  },

  deleteWholesaler: async (id: number): Promise<void> => {
    await api.delete(`/wholesalers/${id}`)
  },
}

export interface Employee {
  id: number
  name: string
  mobileNumber: string
  role: string
  monthlySalary: number
  advanceAmount: number
  remainingSalary: number
  joiningDate: string
}

export const employeeService = {
  getAll: async (): Promise<Employee[]> => {
    const response = await api.get('/employees')
    return response.data.data || []
  },

  getById: async (id: number): Promise<Employee> => {
    const response = await api.get(`/employees/${id}`)
    return response.data.data
  },

  create: async (data: Partial<Employee>): Promise<Employee> => {
    const response = await api.post('/employees', data)
    return response.data.data
  },

  update: async (id: number, data: Partial<Employee>): Promise<Employee> => {
    const response = await api.put(`/employees/${id}`, data)
    return response.data.data
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/employees/${id}`)
  },

  giveAdvance: async (id: number, amount: number): Promise<Employee> => {
    const response = await api.post(`/employees/${id}/advance`, { amount })
    return response.data.data
  },
}

export interface Expense {
  id: number
  date: string
  shopRent: number
  electricityBill: number
  dieselExpense: number
  otherExpense: number
  totalExpense: number
  remarks: string
  category?: string
  description?: string
  amount?: number
  paymentMethod?: string
}

export const expenseService = {
  getAll: async (): Promise<Expense[]> => {
    const response = await api.get('/expenses')
    return response.data.data || []
  },

  getById: async (id: number): Promise<Expense> => {
    const response = await api.get(`/expenses/${id}`)
    return response.data.data
  },

  create: async (data: Partial<Expense>): Promise<Expense> => {
    const response = await api.post('/expenses', data)
    return response.data.data
  },

  update: async (id: number, data: Partial<Expense>): Promise<Expense> => {
    const response = await api.put(`/expenses/${id}`, data)
    return response.data.data
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/expenses/${id}`)
  },

  getTotalExpenses: async (): Promise<Expense[]> => {
    const response = await api.get('/expenses/total')
    return response.data.data || []
  },
}

export interface Stock {
  id: number
  productName: string
  quantity: number
  unit: string
  reorderLevel: number
  status: 'IN_STOCK' | 'LOW_STOCK' | 'OUT_OF_STOCK'
}

export const stockService = {
  getAll: async (): Promise<Stock[]> => {
    const response = await api.get('/stock')
    return response.data.data || []
  },

  getLowStock: async (): Promise<Stock[]> => {
    const response = await api.get('/stock/low')
    return response.data.data || []
  },

  updateStock: async (productId: number, quantity: number, remarks?: string): Promise<Stock> => {
    const response = await api.put(`/stock/${productId}`, { quantity, remarks })
    return response.data.data
  },

  recordReturn: async (productId: number, quantity: number, reason: string): Promise<Stock> => {
    const response = await api.post(`/stock/${productId}/return`, { quantity, reason })
    return response.data.data
  },
}

export interface DashboardStats {
  totalRevenue: number
  totalBills: number
  todayBills: number
  pendingPayments: number
  lowStockCount: number
  totalCustomers: number
}

export interface Company {
  id: number
  name: string
  contactPerson: string
  contactNo: string
  email: string
  address: string
  gstin: string
}

export interface PaginatedResponse<T> {
  content: T[]
  totalElements: number
  totalPages: number
  number: number
  size: number
}

export const companyService = {
  getAll: async (page: number = 0, size: number = 20, search?: string): Promise<PaginatedResponse<Company>> => {
    const response = await api.get('/companies', {
      params: { page, size, search }
    })
    return {
      content: response.data.data || [],
      totalElements: response.data.totalElements || 0,
      totalPages: response.data.totalPages || 0,
      number: response.data.number || 0,
      size: response.data.size || 20
    }
  },

  getById: async (id: number): Promise<Company> => {
    const response = await api.get(`/companies/${id}`)
    return response.data.data
  },

  create: async (data: Partial<Company>): Promise<Company> => {
    const response = await api.post('/companies', data)
    return response.data.data
  },

  update: async (id: number, data: Partial<Company>): Promise<Company> => {
    const response = await api.put(`/companies/${id}`, data)
    return response.data.data
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/companies/${id}`)
  },
}

export interface DashboardSummary {
  totalRevenue: number
  totalBills: number
  totalCustomers: number
  totalProducts: number
  lowStockCount: number
  paymentAnalytics: {
    totalCollected: number
    pendingAmount: number
  }
  customerAnalytics: {
    totalCustomers: number
    customerTypeDistribution: any[]
  }
  productAnalytics: {
    totalProducts: number
    lowStockProducts: number
  }
}

export const dashboardService = {
  getSummary: async (): Promise<DashboardSummary> => {
    const response = await api.get('/dashboard/summary')
    return response.data.data
  },

  getRevenueTrend: async (startDate?: string, endDate?: string): Promise<any[]> => {
    const response = await api.get('/dashboard/revenue-trend', {
      params: { startDate, endDate }
    })
    return response.data.data.trend || []
  },

  getTopProducts: async (limit: number = 10): Promise<any[]> => {
    const response = await api.get('/dashboard/top-products', {
      params: { limit }
    })
    return response.data.data.products || []
  },

  getCustomerAnalytics: async (): Promise<any> => {
    const response = await api.get('/dashboard/customers')
    return response.data.data
  },

  getPaymentAnalytics: async (): Promise<any> => {
    const response = await api.get('/dashboard/payments')
    return response.data.data
  },

  getProductAnalytics: async (): Promise<any> => {
    const response = await api.get('/dashboard/products')
    return response.data.data
  },
}
