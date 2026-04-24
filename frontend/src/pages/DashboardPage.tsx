import { useEffect, useState } from 'react'
import { DollarSign, TrendingUp, TrendingDown, Calendar, AlertTriangle } from 'lucide-react'
import { billService, stockService, type Bill } from '@/services'
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer, PieChart, Pie, Cell } from 'recharts'
import { format } from 'date-fns'
import { toast } from 'sonner'

const COLORS = ['#16a34a', '#2563eb', '#9333ea', '#dc2626']

export function DashboardPage() {
  const [stats, setStats] = useState({
    totalRevenue: 0,
    totalBills: 0,
    todayBills: 0,
    pendingPayments: 0,
    lowStockCount: 0,
    totalCustomers: 0
  })
  const [recentBills, setRecentBills] = useState<Bill[]>([])
  const [lowStockItems, setLowStockItems] = useState<any[]>([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    loadDashboardData()
  }, [])

  const loadDashboardData = async () => {
    try {
      setLoading(true)
      setError(null)
      
      // Load bills
      const bills = await billService.getAll()
      setRecentBills(bills.slice(0, 5))
      
      // Calculate stats
      const totalRevenue = bills.reduce((sum, bill) => sum + (bill.total || 0), 0)
      const pendingPayments = bills.reduce((sum, bill) => sum + (bill.balanceDue || 0), 0)
      const today = format(new Date(), 'yyyy-MM-dd')
      const todayBills = bills.filter(b => b.date === today).length
      
      // Load low stock items
      const lowStock = await stockService.getLowStock()
      setLowStockItems(lowStock)

      setStats({
        totalRevenue,
        totalBills: bills.length,
        todayBills,
        pendingPayments,
        lowStockCount: lowStock.length,
        totalCustomers: 0 // TODO: Add customer count endpoint
      })
    } catch (err: unknown) {
      const message = err instanceof Error ? err.message : 'Failed to load data'
      console.error('Failed to load dashboard data:', message)
      setError(message)
      toast.error('Failed to load dashboard data')
    } finally {
      setLoading(false)
    }
  }

  const chartData = recentBills.map(bill => ({
    name: `#${bill.billNo}`,
    total: bill.total || 0
  }))

  const customerTypeData = [
    { name: 'Retailers', value: 60 },
    { name: 'Wholesalers', value: 25 },
    { name: 'Customers', value: 15 }
  ]

  const statCards = [
    { label: 'Total Revenue', value: `₹${stats.totalRevenue.toLocaleString()}`, icon: DollarSign, color: 'green' },
    { label: 'Total Bills', value: stats.totalBills.toString(), icon: TrendingUp, color: 'blue' },
    { label: "Today's Bills", value: stats.todayBills.toString(), icon: Calendar, color: 'purple' },
    { label: 'Pending Payments', value: `₹${stats.pendingPayments.toLocaleString()}`, icon: TrendingDown, color: 'red' }
  ]

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-[400px]">
        <div className="text-center">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-green-600 mx-auto"></div>
          <p className="mt-4 text-gray-600">Loading dashboard...</p>
        </div>
      </div>
    )
  }

  if (error) {
    return (
      <div className="bg-red-50 border border-red-200 rounded-xl p-6 text-center">
        <AlertTriangle className="w-12 h-12 text-red-600 mx-auto mb-4" />
        <p className="text-red-700">{error}</p>
        <button 
          onClick={loadDashboardData}
          className="mt-4 px-4 py-2 bg-red-600 text-white rounded-lg hover:bg-red-700"
        >
          Retry
        </button>
      </div>
    )
  }

  return (
    <div className="space-y-6">
      {/* Welcome Message */}
      <div>
        <h2 className="text-2xl font-bold text-gray-800">
          Good {new Date().getHours() < 12 ? 'Morning' : new Date().getHours() < 17 ? 'Afternoon' : 'Evening'}! 👋
        </h2>
        <p className="text-gray-600 mt-1">Here's your business overview for today</p>
      </div>

      {/* Stats Grid */}
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
        {statCards.map((stat, index) => (
          <div key={index} className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm hover:shadow-md transition">
            <div className="flex items-center justify-between">
              <div className={`p-3 bg-${stat.color}-50 rounded-lg`}>
                <stat.icon className={`w-6 h-6 text-${stat.color}-600`} />
              </div>
            </div>
            <h3 className="text-2xl font-bold mt-4">{stat.value}</h3>
            <p className="text-sm text-gray-600 mt-1">{stat.label}</p>
          </div>
        ))}
      </div>

      {/* Charts Section */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Revenue Chart */}
        <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
          <h3 className="text-lg font-semibold mb-4">Revenue Chart</h3>
          {chartData.length === 0 ? (
            <div className="text-center py-12 text-gray-500">
              <TrendingUp className="w-12 h-12 mx-auto mb-2 opacity-50" />
              <p>No bills yet. Create your first bill to see revenue data.</p>
            </div>
          ) : (
            <ResponsiveContainer width="100%" height={300}>
              <BarChart data={chartData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Bar dataKey="total" fill="#16a34a" />
              </BarChart>
            </ResponsiveContainer>
          )}
        </div>

        {/* Customer Distribution */}
        <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
          <h3 className="text-lg font-semibold mb-4">Customer Distribution</h3>
          <ResponsiveContainer width="100%" height={300}>
            <PieChart>
              <Pie
                data={customerTypeData}
                cx="50%"
                cy="50%"
                labelLine={false}
                label={({ name, percent }) => `${name}: ${(percent * 100).toFixed(0)}%`}
                outerRadius={100}
                fill="#8884d8"
                dataKey="value"
              >
                {customerTypeData.map((_entry, index) => (
                  <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                ))}
              </Pie>
              <Tooltip />
            </PieChart>
          </ResponsiveContainer>
        </div>
      </div>

      {/* Recent Bills & Low Stock Alerts */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        {/* Recent Bills */}
        <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-lg font-semibold">Recent Bills</h3>
            <a href="/bills" className="text-sm text-green-600 hover:text-green-700">View All →</a>
          </div>
          <div className="space-y-3">
            {recentBills.length === 0 ? (
              <p className="text-gray-500 text-center py-8">No bills yet</p>
            ) : (
              recentBills.map((bill) => (
                <div key={bill.id} className="flex items-center justify-between p-3 bg-gray-50 rounded-lg hover:bg-gray-100 transition">
                  <div>
                    <p className="font-medium">#{bill.billNo} - {bill.fullName}</p>
                    <p className="text-sm text-gray-600">{bill.date}</p>
                  </div>
                  <div className="text-right">
                    <p className="font-semibold">₹{(bill.total || 0).toLocaleString()}</p>
                    {bill.balanceDue > 0 && (
                      <p className="text-xs text-red-600">Balance: ₹{bill.balanceDue.toLocaleString()}</p>
                    )}
                  </div>
                </div>
              ))
            )}
          </div>
        </div>

        {/* Low Stock Alerts */}
        <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-lg font-semibold flex items-center gap-2">
              <AlertTriangle className="w-5 h-5 text-orange-600" />
              Low Stock Alerts ({stats.lowStockCount})
            </h3>
            <a href="/stock-alerts" className="text-sm text-green-600 hover:text-green-700">View All →</a>
          </div>
          <div className="space-y-3">
            {lowStockItems.length === 0 ? (
              <p className="text-gray-500 text-center py-8">All items are well stocked! ✓</p>
            ) : (
              lowStockItems.slice(0, 5).map((item) => (
                <div key={item.id} className="flex items-center justify-between p-3 bg-orange-50 rounded-lg">
                  <div>
                    <p className="font-medium">{item.productName}</p>
                    <p className="text-sm text-gray-600">Reorder at: {item.reorderLevel} {item.unit}</p>
                  </div>
                  <div className="text-right">
                    <p className={`font-semibold ${item.quantity === 0 ? 'text-red-600' : 'text-orange-600'}`}>
                      {item.quantity} {item.unit}
                    </p>
                    <p className="text-xs text-gray-600">
                      {item.quantity === 0 ? 'Out of Stock' : 'Low Stock'}
                    </p>
                  </div>
                </div>
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  )
}
