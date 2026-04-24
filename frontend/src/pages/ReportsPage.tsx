import { useState } from 'react'
import { BarChart, Bar, LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, Legend, ResponsiveContainer, PieChart, Pie, Cell } from 'recharts'
import { TrendingUp, DollarSign, Package, Users, Download, Calendar } from 'lucide-react'
import { toast } from 'sonner'

export function ReportsPage() {
  const [dateRange, setDateRange] = useState<'7d' | '30d' | '90d' | '1y' | 'custom'>('30d')

  // Mock data - In production, fetch from backend based on dateRange
  const revenueData = [
    { month: 'Jan', revenue: 45000, expenses: 28000, profit: 17000 },
    { month: 'Feb', revenue: 52000, expenses: 31000, profit: 21000 },
    { month: 'Mar', revenue: 48000, expenses: 29000, profit: 19000 },
    { month: 'Apr', revenue: 61000, expenses: 35000, profit: 26000 },
    { month: 'May', revenue: 58000, expenses: 33000, profit: 25000 },
    { month: 'Jun', revenue: 67000, expenses: 38000, profit: 29000 }
  ]

  const productSalesData = [
    { name: 'Milk', sales: 35000 },
    { name: 'Cream', sales: 18000 },
    { name: 'Ghee', sales: 22000 },
    { name: 'Paneer', sales: 15000 },
    { name: 'Curd', sales: 12000 }
  ]

  const customerTypeData = [
    { name: 'Customers', value: 45, color: '#16a34a' },
    { name: 'Retailers', value: 30, color: '#3b82f6' },
    { name: 'Wholesalers', value: 25, color: '#8b5cf6' }
  ]

  const totalRevenue = revenueData.reduce((sum, d) => sum + d.revenue, 0)
  const totalExpenses = revenueData.reduce((sum, d) => sum + d.expenses, 0)
  const netProfit = totalRevenue - totalExpenses
  const profitMargin = ((netProfit / totalRevenue) * 100).toFixed(1)

  const exportToCSV = () => {
    try {
      // Revenue data CSV
      const revenueCSV = [
        ['Month', 'Revenue', 'Expenses', 'Profit'],
        ...revenueData.map(d => [d.month, d.revenue, d.expenses, d.profit])
      ].map(row => row.join(',')).join('\n')

      // Product sales CSV
      const productCSV = [
        ['Product', 'Sales'],
        ...productSalesData.map(d => [d.name, d.sales])
      ].map(row => row.join(',')).join('\n')

      // Combined CSV
      const fullCSV = `Revenue vs Expenses\n${revenueCSV}\n\nProduct Sales\n${productCSV}`
      
      const blob = new Blob([fullCSV], { type: 'text/csv' })
      const url = window.URL.createObjectURL(blob)
      const a = document.createElement('a')
      a.href = url
      a.download = `milkmate-report-${new Date().toISOString().split('T')[0]}.csv`
      document.body.appendChild(a)
      a.click()
      document.body.removeChild(a)
      window.URL.revokeObjectURL(url)
      
      toast.success('Report exported successfully')
    } catch (error) {
      toast.error('Failed to export report')
    }
  }

  const handleDateRangeChange = (range: typeof dateRange) => {
    setDateRange(range)
    // In production, reload data based on selected range
    toast.info(`Loading data for ${range === '7d' ? 'last 7 days' : range === '30d' ? 'last 30 days' : range === '90d' ? 'last 90 days' : 'last year'}`)
  }

  return (
    <div className="space-y-6">
      {/* Date Range Filter */}
      <div className="bg-white p-4 rounded-xl border border-gray-200 shadow-sm">
        <div className="flex flex-wrap items-center gap-4">
          <div className="flex items-center gap-2">
            <Calendar className="w-5 h-5 text-gray-600" />
            <span className="font-semibold text-gray-700">Date Range:</span>
          </div>
          
          <div className="flex gap-2">
            <button
              onClick={() => handleDateRangeChange('7d')}
              className={`px-3 py-1.5 rounded-lg text-sm font-medium transition ${
                dateRange === '7d' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              Last 7 Days
            </button>
            <button
              onClick={() => handleDateRangeChange('30d')}
              className={`px-3 py-1.5 rounded-lg text-sm font-medium transition ${
                dateRange === '30d' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              Last 30 Days
            </button>
            <button
              onClick={() => handleDateRangeChange('90d')}
              className={`px-3 py-1.5 rounded-lg text-sm font-medium transition ${
                dateRange === '90d' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              Last 90 Days
            </button>
            <button
              onClick={() => handleDateRangeChange('1y')}
              className={`px-3 py-1.5 rounded-lg text-sm font-medium transition ${
                dateRange === '1y' ? 'bg-blue-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
              }`}
            >
              Last Year
            </button>
          </div>

          <div className="flex-1" />

          <button
            onClick={exportToCSV}
            className="flex items-center gap-2 px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition"
          >
            <Download className="w-4 h-4" />
            Export CSV
          </button>
        </div>
      </div>

      {/* Summary Cards */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div className="bg-gradient-to-br from-green-500 to-green-600 p-6 rounded-xl text-white">
          <DollarSign className="w-8 h-8 mb-2" />
          <p className="text-3xl font-bold">₹{totalRevenue.toLocaleString()}</p>
          <p className="text-sm opacity-90">Total Revenue</p>
        </div>

        <div className="bg-gradient-to-br from-red-500 to-red-600 p-6 rounded-xl text-white">
          <TrendingUp className="w-8 h-8 mb-2" />
          <p className="text-3xl font-bold">₹{totalExpenses.toLocaleString()}</p>
          <p className="text-sm opacity-90">Total Expenses</p>
        </div>

        <div className="bg-gradient-to-br from-purple-500 to-purple-600 p-6 rounded-xl text-white">
          <Package className="w-8 h-8 mb-2" />
          <p className="text-3xl font-bold">₹{netProfit.toLocaleString()}</p>
          <p className="text-sm opacity-90">Net Profit</p>
        </div>

        <div className="bg-gradient-to-br from-orange-500 to-orange-600 p-6 rounded-xl text-white">
          <Users className="w-8 h-8 mb-2" />
          <p className="text-3xl font-bold">{profitMargin}%</p>
          <p className="text-sm opacity-90">Profit Margin</p>
        </div>
      </div>

      {/* Charts Grid */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
          <h3 className="text-lg font-semibold mb-4">Revenue vs Expenses Trend</h3>
          <ResponsiveContainer width="100%" height={300}>
            <LineChart data={revenueData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="month" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Line type="monotone" dataKey="revenue" stroke="#16a34a" strokeWidth={2} />
              <Line type="monotone" dataKey="expenses" stroke="#ef4444" strokeWidth={2} />
            </LineChart>
          </ResponsiveContainer>
        </div>

        <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
          <h3 className="text-lg font-semibold mb-4">Monthly Comparison</h3>
          <ResponsiveContainer width="100%" height={300}>
            <BarChart data={revenueData}>
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis dataKey="month" />
              <YAxis />
              <Tooltip />
              <Legend />
              <Bar dataKey="revenue" fill="#16a34a" />
              <Bar dataKey="expenses" fill="#ef4444" />
            </BarChart>
          </ResponsiveContainer>
        </div>

        <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
          <h3 className="text-lg font-semibold mb-4">Product Sales Distribution</h3>
          <ResponsiveContainer width="100%" height={300}>
            <BarChart data={productSalesData} layout="vertical">
              <CartesianGrid strokeDasharray="3 3" />
              <XAxis type="number" />
              <YAxis dataKey="name" type="category" />
              <Tooltip />
              <Bar dataKey="sales" fill="#3b82f6" />
            </BarChart>
          </ResponsiveContainer>
        </div>

        <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
          <h3 className="text-lg font-semibold mb-4">Customer Type Breakdown</h3>
          <ResponsiveContainer width="100%" height={300}>
            <PieChart>
              <Pie
                data={customerTypeData}
                cx="50%"
                cy="50%"
                labelLine={false}
                label={({ name, value }) => `${name}: ${value}%`}
                outerRadius={100}
                fill="#8884d8"
                dataKey="value"
              >
                {customerTypeData.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={entry.color} />
                ))}
              </Pie>
              <Tooltip />
            </PieChart>
          </ResponsiveContainer>
        </div>
      </div>

      {/* Key Performance Indicators */}
      <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
        <h3 className="text-lg font-semibold mb-4">Key Performance Indicators</h3>
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
          <div className="p-4 bg-gray-50 rounded-lg">
            <p className="text-sm text-gray-600">Average Daily Sales</p>
            <p className="text-2xl font-bold text-green-600">₹1,840</p>
          </div>
          <div className="p-4 bg-gray-50 rounded-lg">
            <p className="text-sm text-gray-600">Average Bill Value</p>
            <p className="text-2xl font-bold text-blue-600">₹850</p>
          </div>
          <div className="p-4 bg-gray-50 rounded-lg">
            <p className="text-sm text-gray-600">Customer Growth</p>
            <p className="text-2xl font-bold text-purple-600">+12%</p>
          </div>
          <div className="p-4 bg-gray-50 rounded-lg">
            <p className="text-sm text-gray-600">Collection Rate</p>
            <p className="text-2xl font-bold text-orange-600">87%</p>
          </div>
        </div>
      </div>
    </div>
  )
}
