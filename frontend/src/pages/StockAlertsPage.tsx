import { useEffect, useState } from 'react'
import { AlertTriangle, Package, TrendingDown, CheckCircle, RefreshCw } from 'lucide-react'
import { stockService, type Stock } from '@/services'
import { toast } from 'sonner'

export function StockAlertsPage() {
  const [stockItems, setStockItems] = useState<Stock[]>([])
  const [loading, setLoading] = useState(true)
  const [refreshing, setRefreshing] = useState(false)

  useEffect(() => {
    loadStock()
  }, [])

  const loadStock = async () => {
    try {
      setLoading(true)
      const data = await stockService.getAll()
      setStockItems(data)
    } catch (error) {
      toast.error('Failed to load stock data')
    } finally {
      setLoading(false)
    }
  }

  const handleRefresh = async () => {
    try {
      setRefreshing(true)
      await loadStock()
      toast.success('Stock data refreshed')
    } catch (error) {
      toast.error('Failed to refresh stock')
    } finally {
      setRefreshing(false)
    }
  }

  const stats = {
    totalProducts: stockItems.length,
    inStock: stockItems.filter(s => s.status === 'IN_STOCK').length,
    lowStock: stockItems.filter(s => s.status === 'LOW_STOCK').length,
    outOfStock: stockItems.filter(s => s.status === 'OUT_OF_STOCK').length,
  }

  const getStockPercentage = (current: number, reorder: number) => {
    if (reorder === 0) return 0
    return Math.round((current / (reorder * 3)) * 100)
  }

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'IN_STOCK': return 'bg-green-100 text-green-700'
      case 'LOW_STOCK': return 'bg-orange-100 text-orange-700'
      case 'OUT_OF_STOCK': return 'bg-red-100 text-red-700'
      default: return 'bg-gray-100 text-gray-700'
    }
  }

  const getStatusLabel = (status: string) => {
    switch (status) {
      case 'IN_STOCK': return 'In Stock'
      case 'LOW_STOCK': return 'Low Stock'
      case 'OUT_OF_STOCK': return 'Out of Stock'
      default: return status
    }
  }

  return (
    <div className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div className="bg-gradient-to-br from-green-500 to-green-600 p-4 rounded-xl text-white">
          <Package className="w-6 h-6 mb-2" />
          <p className="text-3xl font-bold">{stats.totalProducts}</p>
          <p className="text-sm opacity-90">Total Products</p>
        </div>

        <div className="bg-gradient-to-br from-blue-500 to-blue-600 p-4 rounded-xl text-white">
          <CheckCircle className="w-6 h-6 mb-2" />
          <p className="text-3xl font-bold">{stats.inStock}</p>
          <p className="text-sm opacity-90">In Stock</p>
        </div>

        <div className="bg-gradient-to-br from-orange-500 to-orange-600 p-4 rounded-xl text-white">
          <TrendingDown className="w-6 h-6 mb-2" />
          <p className="text-3xl font-bold">{stats.lowStock}</p>
          <p className="text-sm opacity-90">Low Stock</p>
        </div>

        <div className="bg-gradient-to-br from-red-500 to-red-600 p-4 rounded-xl text-white">
          <AlertTriangle className="w-6 h-6 mb-2" />
          <p className="text-3xl font-bold">{stats.outOfStock}</p>
          <p className="text-sm opacity-90">Out of Stock</p>
        </div>
      </div>

      {/* Critical Alerts */}
      {(stats.lowStock > 0 || stats.outOfStock > 0) && (
        <div className="bg-red-50 border-2 border-red-200 rounded-xl p-6">
          <div className="flex items-center justify-between mb-4">
            <div className="flex items-center gap-3">
              <AlertTriangle className="w-6 h-6 text-red-600" />
              <h3 className="text-lg font-bold text-red-800">Critical Stock Alerts</h3>
            </div>
            <button
              onClick={handleRefresh}
              disabled={refreshing}
              className="flex items-center gap-2 px-3 py-1 bg-white text-red-600 rounded-lg hover:bg-red-50 disabled:opacity-50"
            >
              <RefreshCw className={`w-4 h-4 ${refreshing ? 'animate-spin' : ''}`} />
              Refresh
            </button>
          </div>
          
          <div className="space-y-3">
            {stockItems
              .filter(item => item.status === 'OUT_OF_STOCK' || item.status === 'LOW_STOCK')
              .map(item => (
                <div key={item.id} className="flex items-center justify-between p-3 bg-white rounded-lg">
                  <div className="flex items-center gap-3">
                    <div className={`w-2 h-2 rounded-full ${
                      item.status === 'OUT_OF_STOCK' ? 'bg-red-600' : 'bg-orange-600'
                    }`} />
                    <div>
                      <p className="font-semibold">{item.productName}</p>
                      <p className="text-sm text-gray-600">
                        Current: {item.quantity} {item.unit} | Reorder at: {item.reorderLevel} {item.unit}
                      </p>
                    </div>
                  </div>
                  <span className={`px-3 py-1 text-sm font-semibold rounded-full ${
                    getStatusColor(item.status)
                  }`}>
                    {getStatusLabel(item.status)}
                  </span>
                </div>
              ))}
          </div>
        </div>
      )}

      {/* Stock Table */}
      <div className="bg-white rounded-xl border border-gray-200 shadow-sm">
        <div className="p-6 border-b border-gray-200">
          <div className="flex items-center justify-between">
            <h3 className="text-lg font-semibold">Inventory Status</h3>
            <button
              onClick={handleRefresh}
              disabled={refreshing}
              className="flex items-center gap-2 px-3 py-1 text-gray-600 hover:bg-gray-100 rounded-lg disabled:opacity-50"
            >
              <RefreshCw className={`w-4 h-4 ${refreshing ? 'animate-spin' : ''}`} />
              Refresh
            </button>
          </div>
        </div>

        {loading ? (
          <div className="p-8 text-center text-gray-500">Loading stock data...</div>
        ) : (
          <table className="w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Product</th>
                <th className="px-6 py-3 text-center text-xs font-semibold text-gray-600 uppercase">Current Stock</th>
                <th className="px-6 py-3 text-center text-xs font-semibold text-gray-600 uppercase">Reorder Point</th>
                <th className="px-6 py-3 text-center text-xs font-semibold text-gray-600 uppercase">Stock Level</th>
                <th className="px-6 py-3 text-center text-xs font-semibold text-gray-600 uppercase">Status</th>
                <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Actions</th>
              </tr>
            </thead>
            <tbody>
              {stockItems.length === 0 ? (
                <tr>
                  <td colSpan={6} className="px-6 py-12 text-center text-gray-500">
                    No stock items found
                  </td>
                </tr>
              ) : (
                stockItems.map((item) => (
                  <tr key={item.id} className="border-b border-gray-100 hover:bg-gray-50">
                    <td className="px-6 py-4 font-medium">{item.productName}</td>
                    <td className="px-6 py-4 text-center font-semibold">
                      {item.quantity} {item.unit}
                    </td>
                    <td className="px-6 py-4 text-center text-gray-600">
                      {item.reorderLevel} {item.unit}
                    </td>
                    <td className="px-6 py-4">
                      <div className="flex items-center gap-2">
                        <div className="flex-1 bg-gray-200 rounded-full h-2">
                          <div
                            className={`h-2 rounded-full ${
                              getStockPercentage(item.quantity, item.reorderLevel) > 100
                                ? 'bg-green-600'
                                : getStockPercentage(item.quantity, item.reorderLevel) > 50
                                ? 'bg-orange-600'
                                : 'bg-red-600'
                            }`}
                            style={{ width: `${Math.min(getStockPercentage(item.quantity, item.reorderLevel), 100)}%` }}
                          />
                        </div>
                        <span className="text-xs text-gray-600 w-12">
                          {getStockPercentage(item.quantity, item.reorderLevel)}%
                        </span>
                      </div>
                    </td>
                    <td className="px-6 py-4 text-center">
                      <span className={`px-2 py-1 text-xs font-semibold rounded-full ${
                        getStatusColor(item.status)
                      }`}>
                        {getStatusLabel(item.status)}
                      </span>
                    </td>
                    <td className="px-6 py-4 text-right">
                      <button className="px-3 py-1 bg-green-600 text-white rounded-lg text-sm hover:bg-green-700 transition">
                        Restock
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        )}
      </div>
    </div>
  )
}
