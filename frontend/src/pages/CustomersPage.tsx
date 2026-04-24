import { useEffect, useState } from 'react'
import { Users, Phone, Mail, Building2, Plus, X } from 'lucide-react'
import { customerService, type Customer } from '@/services'
import { toast } from 'sonner'

interface CustomerModalProps {
  isOpen: boolean
  onClose: () => void
  customer?: Partial<Customer> | null
  type: 'RETAILER' | 'WHOLESALER'
  onSubmit: (data: Partial<Customer>) => void
}

function CustomerModal({ isOpen, onClose, customer, type, onSubmit }: CustomerModalProps) {
  const [formData, setFormData] = useState({
    name: '',
    mobileNumber: '',
    email: '',
    companyName: '',
    wholesalerName: '',
    creditLimit: 0,
  })

  useEffect(() => {
    if (customer) {
      setFormData({
        name: customer.name || '',
        mobileNumber: customer.mobileNumber || '',
        email: customer.email || '',
        companyName: customer.companyName || '',
        wholesalerName: customer.wholesalerName || '',
        creditLimit: customer.creditLimit || 0,
      })
    } else {
      setFormData({
        name: '',
        mobileNumber: '',
        email: '',
        companyName: '',
        wholesalerName: '',
        creditLimit: 0,
      })
    }
  }, [customer, isOpen])

  if (!isOpen) return null

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    
    if (!formData.name.trim()) {
      toast.error('Customer name is required')
      return
    }

    if (!formData.mobileNumber.trim() || formData.mobileNumber.length !== 10) {
      toast.error('Valid 10-digit mobile number is required')
      return
    }

    onSubmit({ ...formData, customerType: type })
  }

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white rounded-xl shadow-xl w-full max-w-lg mx-4">
        <div className="flex items-center justify-between p-6 border-b border-gray-200">
          <h3 className="text-lg font-bold">
            {customer?.id ? 'Edit' : 'Add'} {type === 'RETAILER' ? 'Retailer' : 'Wholesaler'}
          </h3>
          <button onClick={onClose} className="text-gray-400 hover:text-gray-600">
            <X className="w-5 h-5" />
          </button>
        </div>

        <form onSubmit={handleSubmit} className="p-6 space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Full Name *
            </label>
            <input
              type="text"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
              placeholder="Enter customer name"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Mobile Number *
            </label>
            <input
              type="tel"
              value={formData.mobileNumber}
              onChange={(e) => setFormData({ ...formData, mobileNumber: e.target.value.replace(/\D/g, '').slice(0, 10) })}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
              placeholder="10-digit mobile number"
              maxLength={10}
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Email
            </label>
            <input
              type="email"
              value={formData.email}
              onChange={(e) => setFormData({ ...formData, email: e.target.value })}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
              placeholder="customer@example.com"
            />
          </div>

          {type === 'RETAILER' ? (
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Company Name
              </label>
              <input
                type="text"
                value={formData.companyName}
                onChange={(e) => setFormData({ ...formData, companyName: e.target.value })}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
                placeholder="e.g., Amul, Mother Dairy"
              />
            </div>
          ) : (
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Wholesaler Name
              </label>
              <input
                type="text"
                value={formData.wholesalerName}
                onChange={(e) => setFormData({ ...formData, wholesalerName: e.target.value })}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
                placeholder="Wholesaler company name"
              />
            </div>
          )}

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Credit Limit (₹)
            </label>
            <input
              type="number"
              value={formData.creditLimit}
              onChange={(e) => setFormData({ ...formData, creditLimit: parseFloat(e.target.value) || 0 })}
              step="1000"
              min="0"
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
              placeholder="e.g., 50000"
            />
          </div>

          <div className="flex gap-3 pt-4 border-t border-gray-200">
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
              {customer?.id ? 'Update' : 'Add'} Customer
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export function CustomersPage() {
  const [customers, setCustomers] = useState<Customer[]>([])
  const [loading, setLoading] = useState(true)
  const [filter, setFilter] = useState<'all' | 'RETAILER' | 'WHOLESALER'>('all')
  const [customerModal, setCustomerModal] = useState<{
    isOpen: boolean
    customer: Partial<Customer> | null
    type: 'RETAILER' | 'WHOLESALER'
  }>({
    isOpen: false,
    customer: null,
    type: 'RETAILER',
  })

  useEffect(() => {
    loadCustomers()
  }, [])

  const loadCustomers = async () => {
    try {
      const [retailers, wholesalers] = await Promise.all([
        customerService.getAllRetailers(),
        customerService.getAllWholesalers()
      ])
      
      // Combine and add type field
      const allCustomers = [
        ...retailers.map(r => ({ ...r, customerType: 'RETAILER' as const })),
        ...wholesalers.map(w => ({ ...w, customerType: 'WHOLESALER' as const }))
      ]
      
      setCustomers(allCustomers)
    } catch (error) {
      toast.error('Failed to load customers')
    } finally {
      setLoading(false)
    }
  }

  const filteredCustomers = customers.filter(c => {
    if (filter === 'all') return true
    return c.customerType === filter
  })

  const stats = {
    totalCustomers: customers.length,
    retailers: customers.filter(c => c.customerType === 'RETAILER').length,
    wholesalers: customers.filter(c => c.customerType === 'WHOLESALER').length,
    totalCredit: customers.reduce((sum, c) => sum + (c.creditLimit || 0), 0)
  }

  const handleSaveCustomer = async (data: Partial<Customer>) => {
    try {
      if (customerModal.customer?.id) {
        // Update existing customer
        if (data.customerType === 'RETAILER') {
          await customerService.updateRetailer(customerModal.customer.id, data)
        } else {
          await customerService.updateWholesaler(customerModal.customer.id, data)
        }
        toast.success('Customer updated successfully')
      } else {
        // Create new customer
        if (data.customerType === 'RETAILER') {
          await customerService.createRetailer(data)
        } else {
          await customerService.createWholesaler(data)
        }
        toast.success('Customer added successfully')
      }
      setCustomerModal({ isOpen: false, customer: null, type: 'RETAILER' })
      loadCustomers()
    } catch (error: unknown) {
      const message = error instanceof Error ? error.message : 'Failed to save customer'
      toast.error(message)
    }
  }

  const handleDeleteCustomer = async (id: number, name: string, type: string) => {
    if (!window.confirm(`Are you sure you want to delete "${name}"?`)) {
      return
    }

    try {
      if (type === 'RETAILER') {
        await customerService.deleteRetailer(id)
      } else {
        await customerService.deleteWholesaler(id)
      }
      toast.success('Customer deleted successfully')
      loadCustomers()
    } catch (error: unknown) {
      const message = error instanceof Error ? error.message : 'Failed to delete customer'
      toast.error(message)
    }
  }

  return (
    <div className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <Users className="w-5 h-5 text-blue-600" />
          </div>
          <p className="text-2xl font-bold mt-2">{stats.totalCustomers}</p>
          <p className="text-xs text-gray-600">Total Customers</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <Building2 className="w-5 h-5 text-green-600" />
          </div>
          <p className="text-2xl font-bold mt-2">{stats.retailers}</p>
          <p className="text-xs text-gray-600">Retailers</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <Building2 className="w-5 h-5 text-purple-600" />
          </div>
          <p className="text-2xl font-bold mt-2">{stats.wholesalers}</p>
          <p className="text-xs text-gray-600">Wholesalers</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <Mail className="w-5 h-5 text-orange-600" />
          </div>
          <p className="text-2xl font-bold mt-2">₹{stats.totalCredit.toLocaleString()}</p>
          <p className="text-xs text-gray-600">Total Credit Limit</p>
        </div>
      </div>

      <div className="bg-white rounded-xl border border-gray-200 shadow-sm">
        <div className="p-6 border-b border-gray-200">
          <div className="flex items-center justify-between">
            <h3 className="text-lg font-semibold">Customer Directory</h3>
            <div className="flex gap-3">
              <select
                value={filter}
                onChange={(e) => setFilter(e.target.value as any)}
                className="px-3 py-2 border border-gray-300 rounded-lg text-sm"
              >
                <option value="all">All Types</option>
                <option value="RETAILER">Retailers</option>
                <option value="WHOLESALER">Wholesalers</option>
              </select>
              <div className="flex gap-2">
                <button
                  onClick={() => setCustomerModal({ isOpen: true, customer: null, type: 'RETAILER' })}
                  className="flex items-center gap-2 px-3 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition text-sm"
                >
                  <Plus className="w-4 h-4" />
                  Add Retailer
                </button>
                <button
                  onClick={() => setCustomerModal({ isOpen: true, customer: null, type: 'WHOLESALER' })}
                  className="flex items-center gap-2 px-3 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition text-sm"
                >
                  <Plus className="w-4 h-4" />
                  Add Wholesaler
                </button>
              </div>
            </div>
          </div>
        </div>

        {loading ? (
          <div className="p-8 text-center text-gray-500">Loading customers...</div>
        ) : (
          <table className="w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Name</th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Contact</th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Company</th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Type</th>
                <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Credit Limit</th>
                <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Balance</th>
                <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredCustomers.length === 0 ? (
                <tr>
                  <td colSpan={7} className="px-6 py-12 text-center text-gray-500">
                    No customers found
                  </td>
                </tr>
              ) : (
                filteredCustomers.map((customer) => (
                  <tr key={customer.id} className="border-b border-gray-100 hover:bg-gray-50">
                    <td className="px-6 py-4 font-medium">{customer.name}</td>
                    <td className="px-6 py-4">
                      <div className="flex flex-col gap-1">
                        <div className="flex items-center gap-2 text-sm">
                          <Phone className="w-3 h-3" />
                          {customer.mobileNumber}
                        </div>
                        {customer.email && (
                          <div className="flex items-center gap-2 text-sm text-gray-600">
                            <Mail className="w-3 h-3" />
                            {customer.email}
                          </div>
                        )}
                      </div>
                    </td>
                    <td className="px-6 py-4 text-sm">{customer.companyName || customer.wholesalerName || '-'}</td>
                    <td className="px-6 py-4">
                      <span className={`px-2 py-1 text-xs font-semibold rounded-full ${
                        customer.customerType === 'RETAILER'
                          ? 'bg-green-100 text-green-700'
                          : 'bg-purple-100 text-purple-700'
                      }`}>
                        {customer.customerType === 'RETAILER' ? 'Retailer' : 'Wholesaler'}
                      </span>
                    </td>
                    <td className="px-6 py-4 text-right">₹{(customer.creditLimit || 0).toLocaleString()}</td>
                    <td className="px-6 py-4 text-right font-medium">₹{(customer.currentBalance || 0).toLocaleString()}</td>
                    <td className="px-6 py-4 text-right">
                      <div className="flex items-center justify-end gap-2">
                        <button
                          onClick={() => setCustomerModal({
                            isOpen: true,
                            customer,
                            type: customer.customerType
                          })}
                          className="px-3 py-1 text-blue-600 hover:bg-blue-50 rounded-lg text-sm transition"
                        >
                          Edit
                        </button>
                        <button
                          onClick={() => handleDeleteCustomer(customer.id, customer.name, customer.customerType)}
                          className="px-3 py-1 text-red-600 hover:bg-red-50 rounded-lg text-sm transition"
                        >
                          Delete
                        </button>
                      </div>
                    </td>
                  </tr>
                ))
              )}
            </tbody>
          </table>
        )}
      </div>

      <CustomerModal
        isOpen={customerModal.isOpen}
        onClose={() => setCustomerModal({ isOpen: false, customer: null, type: 'RETAILER' })}
        customer={customerModal.customer}
        type={customerModal.type}
        onSubmit={handleSaveCustomer}
      />
    </div>
  )
}
