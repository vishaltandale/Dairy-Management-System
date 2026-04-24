import { useAuthStore } from '@/stores/authStore'
import { LayoutDashboard, Receipt, Package, TrendingUp, Users, DollarSign, BarChart3, AlertTriangle, Building2 } from 'lucide-react'
import { Link, useLocation } from 'react-router-dom'

const navItems = [
  { icon: LayoutDashboard, label: 'Dashboard', path: '/dashboard' },
  { icon: Receipt, label: 'Bills', path: '/bills' },
  { icon: Package, label: 'Products', path: '/products' },
  { icon: TrendingUp, label: 'Payments', path: '/payments' },
  { icon: Users, label: 'Customers', path: '/customers' },
  { icon: Building2, label: 'Companies', path: '/companies' },
  { icon: DollarSign, label: 'Expenses', path: '/expenses' },
  { icon: Users, label: 'Employees', path: '/employees' },
  { icon: AlertTriangle, label: 'Stock Alerts', path: '/stock-alerts' },
  { icon: BarChart3, label: 'Reports', path: '/reports' },
]

export function MainLayout({ children }: { children: React.ReactNode }) {
  const { user, logout } = useAuthStore()
  const location = useLocation()

  return (
    <div className="flex h-screen bg-gray-50">
      {/* Sidebar */}
      <aside className="w-64 bg-white border-r border-gray-200">
        <div className="p-6">
          <h1 className="text-2xl font-bold text-green-600">🐄 MilkMate</h1>
          <p className="text-xs text-gray-500 mt-1">Modern Dairy Management</p>
        </div>

        <nav className="px-4 space-y-2">
          {navItems.map((item) => {
            const isActive = location.pathname === item.path || 
              (item.path !== '/dashboard' && location.pathname.startsWith(item.path))
            
            return (
              <Link
                key={item.path}
                to={item.path}
                className={`flex items-center gap-3 px-4 py-3 rounded-lg transition ${
                  isActive
                    ? 'bg-green-50 text-green-600 font-semibold'
                    : 'text-gray-700 hover:bg-green-50 hover:text-green-600'
                }`}
              >
                <item.icon className="w-5 h-5" />
                <span>{item.label}</span>
              </Link>
            )
          })}
        </nav>

        <div className="absolute bottom-0 w-64 p-4 border-t border-gray-200 bg-white">
          <div className="flex items-center gap-3 mb-3">
            <div className="w-10 h-10 bg-green-100 rounded-full flex items-center justify-center">
              <span className="text-green-600 font-bold">
                {user?.name?.charAt(0) || 'U'}
              </span>
            </div>
            <div className="flex-1 min-w-0">
              <p className="text-sm font-semibold truncate">{user?.name || 'User'}</p>
              <p className="text-xs text-gray-500">{user?.role || 'Role'}</p>
            </div>
          </div>
          <button
            onClick={logout}
            className="w-full px-4 py-2 bg-red-50 text-red-600 rounded-lg hover:bg-red-100 transition text-sm font-medium"
          >
            Logout
          </button>
        </div>
      </aside>

      {/* Main Content */}
      <main className="flex-1 overflow-auto">
        <header className="bg-white border-b border-gray-200 px-8 py-4">
          <h2 className="text-2xl font-bold text-gray-800">
            {navItems.find((item) => location.pathname === item.path || 
              (item.path !== '/dashboard' && location.pathname.startsWith(item.path)))?.label || 'Dashboard'}
          </h2>
        </header>
        <div className="p-8">{children}</div>
      </main>
    </div>
  )
}
