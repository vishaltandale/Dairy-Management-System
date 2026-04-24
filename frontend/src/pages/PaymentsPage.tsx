import { useEffect, useState } from 'react'
import { DollarSign, TrendingUp, CheckCircle, AlertCircle, X, Plus } from 'lucide-react'
import { billService, type Bill } from '@/services'
import { toast } from 'sonner'
import { format } from 'date-fns'

interface PaymentModalProps {
  isOpen: boolean
  onClose: () => void
  bill: Bill | null
  onSubmit: (amount: number, method: string, reference: string, remarks: string) => void
}

function PaymentModal({ isOpen, onClose, bill, onSubmit }: PaymentModalProps) {
  const [amount, setAmount] = useState('')
  const [method, setMethod] = useState('CASH')
  const [reference, setReference] = useState('')
  const [remarks, setRemarks] = useState('')

  if (!isOpen || !bill) return null

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    const paymentAmount = parseFloat(amount)
    
    if (!paymentAmount || paymentAmount <= 0) {
      toast.error('Please enter a valid amount')
      return
    }

    if (paymentAmount > bill.balanceDue) {
      toast.error(`Payment amount cannot exceed balance due (₹${bill.balanceDue})`)
      return
    }

    onSubmit(paymentAmount, method, reference, remarks)
    setAmount('')
    setMethod('CASH')
    setReference('')
    setRemarks('')
  }

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white rounded-xl shadow-xl w-full max-w-md mx-4">
        <div className="flex items-center justify-between p-6 border-b border-gray-200">
          <h3 className="text-lg font-bold">Record Payment</h3>
          <button onClick={onClose} className="text-gray-400 hover:text-gray-600">
            <X className="w-5 h-5" />
          </button>
        </div>

        <form onSubmit={handleSubmit} className="p-6 space-y-4">
          <div className="bg-blue-50 p-4 rounded-lg">
            <p className="text-sm text-gray-600">Bill #{bill.billNo}</p>
            <p className="font-semibold">{bill.fullName}</p>
            <p className="text-sm text-gray-600 mt-1">
              Total: ₹{bill.total} | Paid: ₹{bill.paidAmount}
            </p>
            <p className="text-lg font-bold text-red-600 mt-2">
              Balance Due: ₹{bill.balanceDue}
            </p>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Payment Amount (₹) *
            </label>
            <input
              type="number"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              step="0.01"
              min="0"
              max={bill.balanceDue}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
              placeholder="Enter amount"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Payment Method *
            </label>
            <select
              value={method}
              onChange={(e) => setMethod(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
            >
              <option value="CASH">Cash</option>
              <option value="UPI">UPI</option>
              <option value="BANK_TRANSFER">Bank Transfer</option>
              <option value="CHEQUE">Cheque</option>
              <option value="CARD">Card</option>
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Reference Number
            </label>
            <input
              type="text"
              value={reference}
              onChange={(e) => setReference(e.target.value)}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
              placeholder="Transaction ID, Cheque No, etc."
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Remarks
            </label>
            <textarea
              value={remarks}
              onChange={(e) => setRemarks(e.target.value)}
              rows={3}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
              placeholder="Any additional notes..."
            />
          </div>

          <div className="flex gap-3 pt-4">
            <button
              type="button"
              onClick={onClose}
              className="flex-1 px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50"
            >
              Cancel
            </button>
            <button
              type="submit"
              className="flex-1 px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 font-semibold"
            >
              Record Payment
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export function PaymentsPage() {
  const [bills, setBills] = useState<Bill[]>([])
  const [loading, setLoading] = useState(true)
  const [filter, setFilter] = useState<'all' | 'pending' | 'paid'>('all')
  const [paymentModal, setPaymentModal] = useState<{ isOpen: boolean; bill: Bill | null }>({
    isOpen: false,
    bill: null,
  })

  useEffect(() => {
    loadBills()
  }, [])

  const loadBills = async () => {
    try {
      const data = await billService.getAll()
      setBills(data)
    } catch (error) {
      toast.error('Failed to load bills')
    } finally {
      setLoading(false)
    }
  }

  const filteredBills = bills.filter(bill => {
    if (filter === 'pending') return bill.balanceDue > 0
    if (filter === 'paid') return bill.balanceDue === 0
    return true
  })

  const handleRecordPayment = async (
    amount: number,
    _method: string,
    _reference: string,
    _remarks: string
  ) => {
    if (!paymentModal.bill) return

    try {
      // TODO: Call payment API when backend is ready
      // await paymentService.create({
      //   billId: paymentModal.bill.id,
      //   amount,
      //   method,
      //   reference,
      //   remarks,
      //   date: new Date().toISOString().split('T')[0]
      // })

      // For now, update the bill locally
      const updatedBill = {
        ...paymentModal.bill,
        paidAmount: (paymentModal.bill.paidAmount || 0) + amount,
        balanceDue: (paymentModal.bill.balanceDue || 0) - amount,
      }

      setBills(bills.map(b => b.id === paymentModal.bill!.id ? updatedBill : b))
      toast.success(`Payment of ₹${amount} recorded successfully!`)
      setPaymentModal({ isOpen: false, bill: null })
      loadBills()
    } catch (error: unknown) {
      const message = error instanceof Error ? error.message : 'Failed to record payment'
      toast.error(message)
    }
  }

  const stats = {
    totalBills: bills.length,
    pendingBills: bills.filter(b => b.balanceDue > 0).length,
    paidBills: bills.filter(b => b.balanceDue === 0).length,
    totalPending: bills.reduce((sum, b) => sum + (b.balanceDue || 0), 0),
    totalRevenue: bills.reduce((sum, b) => sum + (b.total || 0), 0),
  }

  return (
    <div className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <DollarSign className="w-5 h-5 text-blue-600" />
            <span className="text-xs text-gray-500">Revenue</span>
          </div>
          <p className="text-2xl font-bold mt-2">₹{stats.totalRevenue.toLocaleString()}</p>
          <p className="text-xs text-gray-600">Total Revenue</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <AlertCircle className="w-5 h-5 text-orange-600" />
            <span className="text-xs text-gray-500">Pending</span>
          </div>
          <p className="text-2xl font-bold mt-2">{stats.pendingBills}</p>
          <p className="text-xs text-gray-600">Unpaid Bills</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <CheckCircle className="w-5 h-5 text-green-600" />
            <span className="text-xs text-gray-500">Paid</span>
          </div>
          <p className="text-2xl font-bold mt-2">{stats.paidBills}</p>
          <p className="text-xs text-gray-600">Completed</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <TrendingUp className="w-5 h-5 text-red-600" />
            <span className="text-xs text-gray-500">Amount</span>
          </div>
          <p className="text-2xl font-bold mt-2">₹{stats.totalPending.toLocaleString()}</p>
          <p className="text-xs text-gray-600">Pending Amount</p>
        </div>
      </div>

      <div className="bg-white rounded-xl border border-gray-200 shadow-sm">
        <div className="p-6 border-b border-gray-200">
          <div className="flex items-center justify-between">
            <h3 className="text-lg font-semibold">Payment Tracking</h3>
            <div className="flex gap-2">
              <button
                onClick={() => setFilter('all')}
                className={`px-3 py-1 rounded-lg text-sm ${
                  filter === 'all' ? 'bg-green-600 text-white' : 'bg-gray-100 text-gray-700'
                }`}
              >
                All
              </button>
              <button
                onClick={() => setFilter('pending')}
                className={`px-3 py-1 rounded-lg text-sm ${
                  filter === 'pending' ? 'bg-orange-600 text-white' : 'bg-gray-100 text-gray-700'
                }`}
              >
                Pending
              </button>
              <button
                onClick={() => setFilter('paid')}
                className={`px-3 py-1 rounded-lg text-sm ${
                  filter === 'paid' ? 'bg-green-600 text-white' : 'bg-gray-100 text-gray-700'
                }`}
              >
                Paid
              </button>
            </div>
          </div>
        </div>

        {loading ? (
          <div className="p-8 text-center text-gray-500">Loading payments...</div>
        ) : (
          <table className="w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Bill #</th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Customer</th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Date</th>
                <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Total</th>
                <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Paid</th>
                <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Balance</th>
                <th className="px-6 py-3 text-center text-xs font-semibold text-gray-600 uppercase">Status</th>
                <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredBills.length === 0 ? (
                <tr>
                  <td colSpan={8} className="px-6 py-12 text-center text-gray-500">
                    No bills found
                  </td>
                </tr>
              ) : (
                filteredBills.map((bill) => (
                  <tr key={bill.id} className="border-b border-gray-100 hover:bg-gray-50">
                    <td className="px-6 py-4 font-mono text-sm">#{bill.billNo}</td>
                    <td className="px-6 py-4 font-medium">{bill.fullName}</td>
                    <td className="px-6 py-4 text-sm text-gray-600">
                      {format(new Date(bill.date), 'MMM dd, yyyy')}
                    </td>
                    <td className="px-6 py-4 text-right font-semibold">₹{(bill.total || 0).toLocaleString()}</td>
                    <td className="px-6 py-4 text-right text-green-600">₹{(bill.paidAmount || 0).toLocaleString()}</td>
                    <td className="px-6 py-4 text-right text-red-600 font-medium">₹{(bill.balanceDue || 0).toLocaleString()}</td>
                    <td className="px-6 py-4 text-center">
                      <span className={`px-2 py-1 text-xs font-semibold rounded-full ${
                        bill.balanceDue === 0
                          ? 'bg-green-100 text-green-700'
                          : 'bg-orange-100 text-orange-700'
                      }`}>
                        {bill.balanceDue === 0 ? 'Paid' : 'Pending'}
                      </span>
                    </td>
                    <td className="px-6 py-4 text-right">
                      {bill.balanceDue > 0 && (
                        <button
                          onClick={() => setPaymentModal({ isOpen: true, bill })}
                          className="flex items-center gap-2 px-3 py-1 bg-green-600 text-white rounded-lg text-sm hover:bg-green-700 transition"
                        >
                          <Plus className="w-3 h-3" />
                          Record Payment
                        </button>
                      )}
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        )}
      </div>

      <PaymentModal
        isOpen={paymentModal.isOpen}
        onClose={() => setPaymentModal({ isOpen: false, bill: null })}
        bill={paymentModal.bill}
        onSubmit={handleRecordPayment}
      />
    </div>
  )
}
