import { useEffect, useState } from 'react'
import { DollarSign, TrendingDown, Calendar, Plus } from 'lucide-react'
import { expenseService, type Expense } from '@/services'
import { toast } from 'sonner'

export function ExpensesPage() {
  const [expenses, setExpenses] = useState<Expense[]>([])
  const [loading, setLoading] = useState(true)
  const [filter, setFilter] = useState<string>('all')

  useEffect(() => {
    loadExpenses()
  }, [])

  const loadExpenses = async () => {
    try {
      const data = await expenseService.getAll()
      setExpenses(data)
    } catch (error) {
      toast.error('Failed to load expenses')
    } finally {
      setLoading(false)
    }
  }

  const filteredExpenses = expenses.filter(e => {
    if (filter === 'all') return true
    return e.category === filter
  })

  const stats = {
    totalExpenses: expenses.length,
    totalAmount: expenses.reduce((sum, e) => sum + (e.amount || 0), 0),
    thisMonth: expenses.filter(e => {
      const expenseDate = new Date(e.date || '')
      const now = new Date()
      return expenseDate.getMonth() === now.getMonth() && expenseDate.getFullYear() === now.getFullYear()
    }).reduce((sum, e) => sum + (e.amount || 0), 0)
  }

  const categories = ['FEED', 'TRANSPORT', 'MAINTENANCE', 'SALARY', 'UTILITIES', 'OTHER']

  const getCategoryLabel = (category: string | undefined) => {
    if (!category) return 'Other'
    return category.charAt(0) + category.slice(1).toLowerCase()
  }

  const handleAddExpense = () => {
    toast.info('Add expense feature - Coming soon!')
  }

  return (
    <div className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <DollarSign className="w-5 h-5 text-blue-600" />
          </div>
          <p className="text-2xl font-bold mt-2">₹{stats.totalAmount.toLocaleString()}</p>
          <p className="text-xs text-gray-600">Total Expenses</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <TrendingDown className="w-5 h-5 text-orange-600" />
          </div>
          <p className="text-2xl font-bold mt-2">₹{stats.thisMonth.toLocaleString()}</p>
          <p className="text-xs text-gray-600">This Month</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <Calendar className="w-5 h-5 text-green-600" />
          </div>
          <p className="text-2xl font-bold mt-2">{stats.totalExpenses}</p>
          <p className="text-xs text-gray-600">Total Transactions</p>
        </div>
      </div>

      <div className="bg-white rounded-xl border border-gray-200 shadow-sm">
        <div className="p-6 border-b border-gray-200">
          <div className="flex items-center justify-between">
            <h3 className="text-lg font-semibold">Expense Tracker</h3>
            <div className="flex gap-3">
              <select
                value={filter}
                onChange={(e) => setFilter(e.target.value)}
                className="px-3 py-2 border border-gray-300 rounded-lg text-sm"
              >
                <option value="all">All Categories</option>
                {categories.map(cat => (
                  <option key={cat} value={cat}>{getCategoryLabel(cat)}</option>
                ))}
              </select>
              <button
                onClick={handleAddExpense}
                className="flex items-center gap-2 px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition"
              >
                <Plus className="w-4 h-4" />
                Add Expense
              </button>
            </div>
          </div>
        </div>

        {loading ? (
          <div className="p-8 text-center text-gray-500">Loading expenses...</div>
        ) : (
          <table className="w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Date</th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Category</th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Description</th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Payment</th>
                <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Amount</th>
                <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredExpenses.length === 0 ? (
                <tr>
                  <td colSpan={6} className="px-6 py-12 text-center text-gray-500">
                    No expenses recorded yet
                  </td>
                </tr>
              ) : (
                filteredExpenses.map((expense) => (
                  <tr key={expense.id} className="border-b border-gray-100 hover:bg-gray-50">
                    <td className="px-6 py-4 text-sm text-gray-600">
                      {new Date(expense.date || '').toLocaleDateString()}
                    </td>
                    <td className="px-6 py-4">
                      <span className="px-2 py-1 bg-blue-50 text-blue-700 text-xs font-semibold rounded-full">
                        {getCategoryLabel(expense.category)}
                      </span>
                    </td>
                    <td className="px-6 py-4 text-sm">{expense.description || '-'}</td>
                    <td className="px-6 py-4 text-sm">{expense.paymentMethod || '-'}</td>
                    <td className="px-6 py-4 text-right font-semibold text-red-600">
                      ₹{(expense.amount || 0).toLocaleString()}
                    </td>
                    <td className="px-6 py-4 text-right">
                      <button className="px-3 py-1 text-blue-600 hover:bg-blue-50 rounded-lg text-sm transition">
                        Edit
                      </button>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        )}
      </div>

      <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
        <h3 className="text-lg font-semibold mb-4">Quick Add Expense</h3>
        <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
          {categories.map((category) => (
            <button
              key={category}
              onClick={() => toast.info(`Add ${getCategoryLabel(category)} expense`)}
              className="p-4 border-2 border-dashed border-gray-300 rounded-lg hover:border-green-500 hover:bg-green-50 transition"
            >
              <p className="text-sm font-medium text-gray-700">{getCategoryLabel(category)}</p>
            </button>
          ))}
        </div>
      </div>
    </div>
  )
}
