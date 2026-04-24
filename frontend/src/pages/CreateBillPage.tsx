import { useForm, useFieldArray } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { Plus, Trash2, Calculator, Save } from 'lucide-react'
import { billService, productService, type Product } from '@/services'
import { toast } from 'sonner'
import { useNavigate } from 'react-router-dom'
import { useState, useMemo, useEffect } from 'react'

const billSchema = z.object({
  fullName: z.string().min(2, 'Customer name must be at least 2 characters'),
  mobileNumber: z.string().min(10, 'Valid mobile number required').max(10),
  customerType: z.enum(['CUSTOMER', 'RETAILER', 'WHOLESALER']),
  date: z.string().min(1, 'Date is required'),
  items: z.array(
    z.object({
      productName: z.string().min(1, 'Product name required'),
      quantity: z.number().min(1, 'Quantity must be at least 1'),
      price: z.number().min(0, 'Price must be positive'),
    })
  ).min(1, 'At least one item required'),
  discount: z.number().min(0).default(0),
  paidAmount: z.number().min(0).default(0),
})

type BillForm = z.infer<typeof billSchema>

export function CreateBillPage() {
  const navigate = useNavigate()
  const [isSubmitting, setIsSubmitting] = useState(false)
  const [products, setProducts] = useState<Product[]>([])

  useEffect(() => {
    loadProducts()
  }, [])

  const loadProducts = async () => {
    try {
      const data = await productService.getAll()
      setProducts(data)
    } catch (error) {
      console.error('Failed to load products:', error)
    }
  }

  const { register, handleSubmit, control, watch, setValue, formState: { errors } } = useForm<BillForm>({
    resolver: zodResolver(billSchema),
    defaultValues: {
      customerType: 'CUSTOMER',
      date: new Date().toISOString().split('T')[0],
      items: [{ productName: '', quantity: 1, price: 0 }],
      discount: 0,
      paidAmount: 0,
    },
  })

  const { fields, append, remove } = useFieldArray({
    control,
    name: 'items',
  })

  const items = watch('items')
  const discount = watch('discount') || 0

  // Auto-calculate totals
  const calculations = useMemo(() => {
    const subtotal = items.reduce((sum, item) => {
      return sum + (item.quantity * item.price)
    }, 0)

    const cgst = subtotal * 0.09
    const sgst = subtotal * 0.09
    const total = subtotal + cgst + sgst - discount
    const paidAmount = watch('paidAmount') || 0
    const balanceDue = total - paidAmount

    return {
      subtotal,
      cgst,
      sgst,
      total,
      paidAmount,
      balanceDue,
    }
  }, [items, discount, watch('paidAmount')])

  const handleProductSelect = (index: number, productName: string) => {
    setValue(`items.${index}.productName`, productName)
    const product = products.find(p => p.name === productName)
    if (product) {
      // Auto-fill price based on customer type
      const customerType = watch('customerType')
      let price = product.customerPrice
      if (customerType === 'RETAILER') price = product.retailerPrice
      if (customerType === 'WHOLESALER') price = product.wholesalerPrice
      setValue(`items.${index}.price`, price || 0)
    }
  }

  const onSubmit = async (data: BillForm) => {
    try {
      setIsSubmitting(true)
      
      const billData = {
        fullName: data.fullName,
        mobileNumber: data.mobileNumber,
        customerType: data.customerType,
        date: data.date,
        items: data.items,
        discount: data.discount,
        paidAmount: data.paidAmount,
      }

      await billService.create(billData)
      toast.success('Bill created successfully!')
      navigate('/bills')
    } catch (error: unknown) {
      const message = error instanceof Error ? error.message : 'Failed to create bill. Please try again.'
      toast.error(message)
      console.error('Error creating bill:', error)
    } finally {
      setIsSubmitting(false)
    }
  }

  const addItem = () => {
    append({ productName: '', quantity: 1, price: 0 })
  }

  const removeItem = (index: number) => {
    if (fields.length > 1) {
      remove(index)
    } else {
      toast.warning('At least one item is required')
    }
  }

  return (
    <div className="max-w-5xl mx-auto">
      <form onSubmit={handleSubmit(onSubmit)} className="space-y-6">
        {/* Customer Details */}
        <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
          <div className="flex items-center gap-2 mb-4">
            <Calculator className="w-5 h-5 text-green-600" />
            <h3 className="text-lg font-semibold">Customer Details</h3>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Full Name *
              </label>
              <input
                {...register('fullName')}
                className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 ${
                  errors.fullName ? 'border-red-500' : 'border-gray-300'
                }`}
                placeholder="Enter customer name"
              />
              {errors.fullName && (
                <p className="text-sm text-red-600 mt-1">{errors.fullName.message}</p>
              )}
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Mobile Number *
              </label>
              <input
                {...register('mobileNumber')}
                className={`w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500 ${
                  errors.mobileNumber ? 'border-red-500' : 'border-gray-300'
                }`}
                placeholder="10-digit mobile number"
                maxLength={10}
              />
              {errors.mobileNumber && (
                <p className="text-sm text-red-600 mt-1">{errors.mobileNumber.message}</p>
              )}
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Customer Type *
              </label>
              <select
                {...register('customerType')}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
              >
                <option value="CUSTOMER">Customer</option>
                <option value="RETAILER">Retailer</option>
                <option value="WHOLESALER">Wholesaler</option>
              </select>
            </div>
          </div>

          <div className="mt-4">
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Bill Date *
            </label>
            <input
              {...register('date')}
              type="date"
              className="w-full md:w-1/3 px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
            />
          </div>
        </div>

        {/* Items */}
        <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
          <div className="flex items-center justify-between mb-4">
            <h3 className="text-lg font-semibold">Bill Items</h3>
            <button
              type="button"
              onClick={addItem}
              className="flex items-center gap-2 px-3 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition text-sm"
            >
              <Plus className="w-4 h-4" />
              Add Item
            </button>
          </div>

          <div className="space-y-3">
            {fields.map((field, index) => (
              <div key={field.id} className="grid grid-cols-12 gap-3 items-end">
                <div className="col-span-4">
                  <label className="block text-xs font-medium text-gray-600 mb-1">
                    Product *
                  </label>
                  <select
                    {...register(`items.${index}.productName`)}
                    onChange={(e) => handleProductSelect(index, e.target.value)}
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                  >
                    <option value="">Select product...</option>
                    {products.map(p => (
                      <option key={p.id} value={p.name}>
                        {p.name}
                      </option>
                    ))}
                  </select>
                  {errors.items?.[index]?.productName && (
                    <p className="text-xs text-red-600 mt-1">{errors.items[index].productName.message}</p>
                  )}
                </div>

                <div className="col-span-2">
                  <label className="block text-xs font-medium text-gray-600 mb-1">
                    Quantity *
                  </label>
                  <input
                    {...register(`items.${index}.quantity`, { valueAsNumber: true })}
                    type="number"
                    min="1"
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                  />
                </div>

                <div className="col-span-2">
                  <label className="block text-xs font-medium text-gray-600 mb-1">
                    Price (₹) *
                  </label>
                  <input
                    {...register(`items.${index}.price`, { valueAsNumber: true })}
                    type="number"
                    min="0"
                    step="0.01"
                    className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                  />
                </div>

                <div className="col-span-3">
                  <label className="block text-xs font-medium text-gray-600 mb-1">
                    Subtotal
                  </label>
                  <div className="px-3 py-2 bg-gray-50 rounded-lg font-semibold">
                    ₹{(items[index].quantity * items[index].price).toFixed(2)}
                  </div>
                </div>

                <div className="col-span-1">
                  <button
                    type="button"
                    onClick={() => removeItem(index)}
                    className="w-full p-2 text-red-600 hover:bg-red-50 rounded-lg transition"
                    title="Remove item"
                  >
                    <Trash2 className="w-5 h-5" />
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>

        {/* Calculations */}
        <div className="bg-white p-6 rounded-xl border border-gray-200 shadow-sm">
          <h3 className="text-lg font-semibold mb-4">Bill Summary</h3>
          
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div className="space-y-3">
              <div className="flex justify-between items-center py-2 border-b border-gray-100">
                <span className="text-gray-600">Subtotal</span>
                <span className="font-semibold">₹{calculations.subtotal.toFixed(2)}</span>
              </div>
              <div className="flex justify-between items-center py-2 border-b border-gray-100">
                <span className="text-gray-600">CGST (9%)</span>
                <span className="font-semibold">₹{calculations.cgst.toFixed(2)}</span>
              </div>
              <div className="flex justify-between items-center py-2 border-b border-gray-100">
                <span className="text-gray-600">SGST (9%)</span>
                <span className="font-semibold">₹{calculations.sgst.toFixed(2)}</span>
              </div>
              <div className="flex justify-between items-center py-2">
                <div>
                  <label className="text-gray-600">Discount</label>
                  <input
                    {...register('discount', { valueAsNumber: true })}
                    type="number"
                    min="0"
                    step="0.01"
                    className="ml-2 w-24 px-2 py-1 border border-gray-300 rounded text-sm"
                  />
                </div>
                <span className="font-semibold text-red-600">- ₹{discount.toFixed(2)}</span>
              </div>
              <div className="flex justify-between items-center py-3 border-t-2 border-green-600">
                <span className="text-lg font-bold">Total Amount</span>
                <span className="text-2xl font-bold text-green-600">₹{calculations.total.toFixed(2)}</span>
              </div>
            </div>

            <div className="space-y-3">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">
                  Paid Amount (₹)
                </label>
                <input
                  {...register('paidAmount', { valueAsNumber: true })}
                  type="number"
                  min="0"
                  step="0.01"
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-500"
                />
              </div>
              
              <div className={`p-4 rounded-lg ${
                calculations.balanceDue > 0 ? 'bg-red-50' : 'bg-green-50'
              }`}>
                <div className="flex justify-between items-center">
                  <span className="font-semibold">Balance Due</span>
                  <span className={`text-2xl font-bold ${
                    calculations.balanceDue > 0 ? 'text-red-600' : 'text-green-600'
                  }`}>
                    ₹{calculations.balanceDue.toFixed(2)}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Submit Button */}
        <div className="flex gap-4">
          <button
            type="button"
            onClick={() => navigate('/bills')}
            className="flex-1 px-6 py-3 border border-gray-300 rounded-lg font-semibold hover:bg-gray-50 transition"
          >
            Cancel
          </button>
          <button
            type="submit"
            disabled={isSubmitting}
            className="flex-1 flex items-center justify-center gap-2 bg-green-600 text-white py-3 rounded-lg font-semibold hover:bg-green-700 disabled:opacity-50 disabled:cursor-not-allowed transition"
          >
            <Save className="w-5 h-5" />
            {isSubmitting ? 'Creating Bill...' : 'Create Bill'}
          </button>
        </div>
      </form>
    </div>
  )
}
