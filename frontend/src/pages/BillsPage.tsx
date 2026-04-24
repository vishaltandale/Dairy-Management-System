import { useState, useEffect } from 'react'
import { Plus, Search, Filter, Eye, Edit, Trash2, X, Printer } from 'lucide-react'
import { Link } from 'react-router-dom'
import { billService, type Bill } from '@/services'
import { toast } from 'sonner'

interface BillDetailsModalProps {
  isOpen: boolean
  onClose: () => void
  bill: Bill | null
}

function BillDetailsModal({ isOpen, onClose, bill }: BillDetailsModalProps) {
  if (!isOpen || !bill) return null

  const handlePrint = () => {
    window.print()
  }

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white rounded-xl shadow-xl w-full max-w-3xl mx-4 max-h-[90vh] overflow-y-auto">
        <div className="flex items-center justify-between p-6 border-b border-gray-200">
          <div>
            <h3 className="text-lg font-bold">Bill Details</h3>
            <p className="text-sm text-gray-500">Bill #{bill.billNo}</p>
          </div>
          <div className="flex items-center gap-2">
            <button
              onClick={handlePrint}
              className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition"
              title="Print Bill"
            >
              <Printer className="w-5 h-5" />
            </button>
            <button onClick={onClose} className="text-gray-400 hover:text-gray-600">
              <X className="w-5 h-5" />
            </button>
          </div>
        </div>

        <div className="p-6 space-y-6">
          {/* Customer Info */}
          <div className="bg-gray-50 p-4 rounded-lg">
            <h4 className="text-sm font-semibold text-gray-600 uppercase mb-3">Customer Information</h4>
            <div className="grid grid-cols-2 gap-4">
              <div>
                <p className="text-sm text-gray-500">Full Name</p>
                <p className="font-semibold">{bill.fullName}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Mobile Number</p>
                <p className="font-semibold">{bill.mobileNumber}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Customer Type</p>
                <p className="font-semibold">
                  <span className="px-2 py-1 text-xs font-semibold rounded bg-blue-100 text-blue-700">
                    {bill.customerType}
                  </span>
                </p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Date</p>
                <p className="font-semibold">{bill.date}</p>
              </div>
            </div>
          </div>

          {/* Items Table */}
          <div>
            <h4 className="text-sm font-semibold text-gray-600 uppercase mb-3">Bill Items</h4>
            <table className="w-full">
              <thead className="bg-gray-100">
                <tr>
                  <th className="px-4 py-2 text-left text-xs font-semibold text-gray-600">Product</th>
                  <th className="px-4 py-2 text-center text-xs font-semibold text-gray-600">Quantity</th>
                  <th className="px-4 py-2 text-right text-xs font-semibold text-gray-600">Price</th>
                  <th className="px-4 py-2 text-right text-xs font-semibold text-gray-600">Subtotal</th>
                </tr>
              </thead>
              <tbody>
                {bill.items?.map((item, index) => (
                  <tr key={index} className="border-b border-gray-100">
                    <td className="px-4 py-3 font-medium">{item.productName}</td>
                    <td className="px-4 py-3 text-center">{item.quantity}</td>
                    <td className="px-4 py-3 text-right">₹{item.price}</td>
                    <td className="px-4 py-3 text-right font-semibold">₹{(item.quantity * item.price).toFixed(2)}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>

          {/* Payment Summary */}
          <div className="bg-green-50 p-4 rounded-lg">
            <h4 className="text-sm font-semibold text-gray-600 uppercase mb-3">Payment Summary</h4>
            <div className="space-y-2">
              <div className="flex justify-between">
                <span className="text-gray-600">Subtotal</span>
                <span className="font-semibold">₹{(bill.subtotal || 0).toFixed(2)}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-gray-600">CGST (9%)</span>
                <span className="font-semibold">₹{(bill.cgst || 0).toFixed(2)}</span>
              </div>
              <div className="flex justify-between">
                <span className="text-gray-600">SGST (9%)</span>
                <span className="font-semibold">₹{(bill.sgst || 0).toFixed(2)}</span>
              </div>
              {(bill.discount || 0) > 0 && (
                <div className="flex justify-between text-red-600">
                  <span>Discount</span>
                  <span className="font-semibold">- ₹{(bill.discount || 0).toFixed(2)}</span>
                </div>
              )}
              <div className="border-t border-green-200 pt-2 flex justify-between text-lg font-bold">
                <span>Total Amount</span>
                <span className="text-green-600">₹{(bill.total || 0).toFixed(2)}</span>
              </div>
              <div className="flex justify-between text-green-600">
                <span>Paid Amount</span>
                <span className="font-semibold">₹{(bill.paidAmount || 0).toFixed(2)}</span>
              </div>
              <div className="flex justify-between text-red-600 text-lg font-bold border-t border-green-200 pt-2">
                <span>Balance Due</span>
                <span>₹{(bill.balanceDue || 0).toFixed(2)}</span>
              </div>
            </div>
          </div>

          {/* Status Badge */}
          <div className="flex items-center justify-center">
            <span className={`px-4 py-2 text-sm font-semibold rounded-full ${
              (bill.balanceDue || 0) <= 0
                ? 'bg-green-100 text-green-700'
                : (bill.paidAmount || 0) > 0
                ? 'bg-yellow-100 text-yellow-700'
                : 'bg-red-100 text-red-700'
            }`}>
              {(bill.balanceDue || 0) <= 0 ? '✓ Fully Paid' : (bill.paidAmount || 0) > 0 ? '⏳ Partially Paid' : '✗ Unpaid'}
            </span>
          </div>
        </div>

        <div className="p-6 border-t border-gray-200 flex gap-3">
          <button
            onClick={onClose}
            className="flex-1 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50"
          >
            Close
          </button>
          <button
            onClick={handlePrint}
            className="flex-1 flex items-center justify-center gap-2 px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
          >
            <Printer className="w-4 h-4" />
            Print Bill
          </button>
        </div>
      </div>
    </div>
  )
}

export function BillsPage() {
  const [bills, setBills] = useState<Bill[]>([])
  const [loading, setLoading] = useState(true)
  const [search, setSearch] = useState('')
  const [customerType, setCustomerType] = useState('')
  const [status, setStatus] = useState('')
  const [showFilters, setShowFilters] = useState(false)
  const [detailsModal, setDetailsModal] = useState<{ isOpen: boolean; bill: Bill | null }>({
    isOpen: false,
    bill: null,
  })

  useEffect(() => {
    loadBills()
  }, [search, customerType, status])

  const loadBills = async () => {
    try {
      setLoading(true)
      const data = await billService.getAll(search, customerType, status)
      setBills(data)
    } catch (error: unknown) {
      console.error('Failed to load bills:', error)
      toast.error('Failed to load bills')
    } finally {
      setLoading(false)
    }
  }

  const handleDelete = async (id: number, billNo: number) => {
    if (!window.confirm(`Are you sure you want to delete bill #${billNo}?`)) {
      return
    }

    try {
      await billService.delete(id)
      toast.success('Bill deleted successfully')
      loadBills()
    } catch (error: unknown) {
      console.error('Failed to delete bill:', error)
      const message = error instanceof Error ? error.message : 'Failed to delete bill'
      toast.error(message)
    }
  }

  const getStatusBadge = (bill: Bill) => {
    const balance = bill.balanceDue || 0
    if (balance <= 0) {
      return <span className="px-2 py-1 text-xs font-semibold rounded bg-green-100 text-green-700">Paid</span>
    } else if (bill.paidAmount > 0) {
      return <span className="px-2 py-1 text-xs font-semibold rounded bg-yellow-100 text-yellow-700">Partial</span>
    } else {
      return <span className="px-2 py-1 text-xs font-semibold rounded bg-red-100 text-red-700">Unpaid</span>
    }
  }

  const clearFilters = () => {
    setSearch('')
    setCustomerType('')
    setStatus('')
  }

  return (
    <div className="space-y-6">
      {/* Header */}
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-2xl font-bold text-gray-800">Bills</h2>
          <p className="text-gray-600 mt-1">Manage customer invoices and payments</p>
        </div>
        <Link
          to="/bills/new"
          className="flex items-center gap-2 px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition"
        >
          <Plus className="w-4 h-4" />
          New Bill
        </Link>
      </div>

      {/* Search and Filters */}
      <div className="bg-white p-4 rounded-xl border border-gray-200 shadow-sm">
        <div className="flex gap-4">
          {/* Search */}
          <div className="flex-1 relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-4 h-4" />
            <input
              type="text"
              placeholder="Search by customer name or bill number..."
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500 focus:border-transparent"
            />
          </div>

          {/* Filter Toggle */}
          <button
            onClick={() => setShowFilters(!showFilters)}
            className={`flex items-center gap-2 px-4 py-2 border rounded-lg transition ${
              showFilters || customerType || status
                ? 'bg-green-50 border-green-500 text-green-700'
                : 'border-gray-300 hover:bg-gray-50'
            }`}
          >
            <Filter className="w-4 h-4" />
            Filters
          </button>
        </div>

        {/* Filter Options */}
        {showFilters && (
          <div className="mt-4 pt-4 border-t border-gray-200">
            <div className="flex gap-4">
              <div className="flex-1">
                <label className="block text-sm font-medium text-gray-700 mb-1">Customer Type</label>
                <select
                  value={customerType}
                  onChange={(e) => setCustomerType(e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
                >
                  <option value="">All Types</option>
                  <option value="CUSTOMER">Customer</option>
                  <option value="RETAILER">Retailer</option>
                  <option value="WHOLESALER">Wholesaler</option>
                </select>
              </div>

              <div className="flex-1">
                <label className="block text-sm font-medium text-gray-700 mb-1">Payment Status</label>
                <select
                  value={status}
                  onChange={(e) => setStatus(e.target.value)}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
                >
                  <option value="">All Statuses</option>
                  <option value="PAID">Paid</option>
                  <option value="PARTIAL">Partial</option>
                  <option value="UNPAID">Unpaid</option>
                </select>
              </div>

              {(customerType || status) && (
                <div className="flex items-end">
                  <button
                    onClick={clearFilters}
                    className="flex items-center gap-2 px-4 py-2 text-red-600 hover:bg-red-50 rounded-lg transition"
                  >
                    <X className="w-4 h-4" />
                    Clear
                  </button>
                </div>
              )}
            </div>
          </div>
        )}
      </div>

      {/* Bills Table */}
      <div className="bg-white rounded-xl border border-gray-200 shadow-sm">
        {loading ? (
          <div className="flex items-center justify-center py-12">
            <div className="text-center">
              <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-green-600 mx-auto"></div>
              <p className="mt-2 text-gray-600">Loading bills...</p>
            </div>
          </div>
        ) : bills.length === 0 ? (
          <div className="text-center py-12">
            <p className="text-gray-500">No bills found</p>
            <Link to="/bills/new" className="text-green-600 hover:text-green-700 mt-2 inline-block">
              Create your first bill →
            </Link>
          </div>
        ) : (
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-gray-50 border-b border-gray-200">
                <tr>
                  <th className="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase">Bill No</th>
                  <th className="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase">Customer</th>
                  <th className="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase">Date</th>
                  <th className="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase">Type</th>
                  <th className="px-6 py-4 text-right text-xs font-semibold text-gray-600 uppercase">Total</th>
                  <th className="px-6 py-4 text-right text-xs font-semibold text-gray-600 uppercase">Paid</th>
                  <th className="px-6 py-4 text-right text-xs font-semibold text-gray-600 uppercase">Balance</th>
                  <th className="px-6 py-4 text-center text-xs font-semibold text-gray-600 uppercase">Status</th>
                  <th className="px-6 py-4 text-center text-xs font-semibold text-gray-600 uppercase">Actions</th>
                </tr>
              </thead>
              <tbody>
                {bills.map((bill) => (
                  <tr key={bill.id} className="border-b border-gray-100 hover:bg-gray-50 transition">
                    <td className="px-6 py-4 font-mono text-sm font-semibold">#{bill.billNo}</td>
                    <td className="px-6 py-4">
                      <div>
                        <p className="font-medium">{bill.fullName}</p>
                        <p className="text-sm text-gray-600">{bill.mobileNumber}</p>
                      </div>
                    </td>
                    <td className="px-6 py-4 text-sm text-gray-600">{bill.date}</td>
                    <td className="px-6 py-4">
                      <span className="px-2 py-1 text-xs font-semibold rounded bg-blue-100 text-blue-700">
                        {bill.customerType}
                      </span>
                    </td>
                    <td className="px-6 py-4 text-right font-semibold">₹{(bill.total || 0).toLocaleString()}</td>
                    <td className="px-6 py-4 text-right text-green-600">₹{(bill.paidAmount || 0).toLocaleString()}</td>
                    <td className="px-6 py-4 text-right text-red-600 font-semibold">
                      ₹{(bill.balanceDue || 0).toLocaleString()}
                    </td>
                    <td className="px-6 py-4 text-center">{getStatusBadge(bill)}</td>
                    <td className="px-6 py-4">
                      <div className="flex items-center justify-center gap-2">
                        <button
                          onClick={() => setDetailsModal({ isOpen: true, bill })}
                          className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition"
                          title="View Details"
                        >
                          <Eye className="w-4 h-4" />
                        </button>
                        <button
                          className="p-2 text-yellow-600 hover:bg-yellow-50 rounded-lg transition"
                          title="Edit Bill"
                        >
                          <Edit className="w-4 h-4" />
                        </button>
                        <button
                          onClick={() => handleDelete(bill.id, bill.billNo)}
                          className="p-2 text-red-600 hover:bg-red-50 rounded-lg transition"
                          title="Delete Bill"
                        >
                          <Trash2 className="w-4 h-4" />
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {/* Summary */}
      {!loading && bills.length > 0 && (
        <div className="bg-gray-50 p-4 rounded-lg">
          <div className="flex justify-between text-sm text-gray-600">
            <span>Total Bills: <strong>{bills.length}</strong></span>
            <span>
              Total Revenue: <strong className="text-green-600">₹{bills.reduce((sum, b) => sum + (b.total || 0), 0).toLocaleString()}</strong>
            </span>
            <span>
              Pending: <strong className="text-red-600">₹{bills.reduce((sum, b) => sum + (b.balanceDue || 0), 0).toLocaleString()}</strong>
            </span>
          </div>
        </div>
      )}

      {/* Bill Details Modal */}
      <BillDetailsModal
        isOpen={detailsModal.isOpen}
        onClose={() => setDetailsModal({ isOpen: false, bill: null })}
        bill={detailsModal.bill}
      />
    </div>
  )
}
