import { useEffect, useState } from 'react'
import { Plus, Edit, Trash2, Package, X } from 'lucide-react'
import { productService, type Product } from '@/services'
import { toast } from 'sonner'

interface ProductModalProps {
  isOpen: boolean
  onClose: () => void
  product?: Product | null
  onSubmit: (data: Partial<Product>) => void
}

function ProductModal({ isOpen, onClose, product, onSubmit }: ProductModalProps) {
  const [formData, setFormData] = useState({
    name: '',
    description: '',
    customerPrice: 0,
    retailerPrice: 0,
    wholesalerPrice: 0,
    companyPrice: 0,
    hsnCode: '',
    unitName: 'Pieces',
    companyName: '',
  })

  useEffect(() => {
    if (product) {
      setFormData({
        name: product.name || '',
        description: product.description || '',
        customerPrice: product.customerPrice || 0,
        retailerPrice: product.retailerPrice || 0,
        wholesalerPrice: product.wholesalerPrice || 0,
        companyPrice: product.companyPrice || 0,
        hsnCode: product.hsnCode || '',
        unitName: product.unitName || 'Pieces',
        companyName: product.companyName || '',
      })
    } else {
      setFormData({
        name: '',
        description: '',
        customerPrice: 0,
        retailerPrice: 0,
        wholesalerPrice: 0,
        companyPrice: 0,
        hsnCode: '',
        unitName: 'Pieces',
        companyName: '',
      })
    }
  }, [product, isOpen])

  if (!isOpen) return null

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    
    if (!formData.name.trim()) {
      toast.error('Product name is required')
      return
    }

    onSubmit(formData)
  }

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white rounded-xl shadow-xl w-full max-w-2xl mx-4 max-h-[90vh] overflow-y-auto">
        <div className="flex items-center justify-between p-6 border-b border-gray-200">
          <h3 className="text-lg font-bold">
            {product ? 'Edit Product' : 'Add New Product'}
          </h3>
          <button onClick={onClose} className="text-gray-400 hover:text-gray-600">
            <X className="w-5 h-5" />
          </button>
        </div>

        <form onSubmit={handleSubmit} className="p-6 space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div className="md:col-span-2">
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Product Name *
              </label>
              <input
                type="text"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
                placeholder="e.g., Fresh Milk (1L)"
                required
              />
            </div>

            <div className="md:col-span-2">
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Description
              </label>
              <textarea
                value={formData.description}
                onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                rows={2}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
                placeholder="Product description..."
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                HSN Code
              </label>
              <input
                type="text"
                value={formData.hsnCode}
                onChange={(e) => setFormData({ ...formData, hsnCode: e.target.value })}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
                placeholder="e.g., 0401"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Unit
              </label>
              <select
                value={formData.unitName}
                onChange={(e) => setFormData({ ...formData, unitName: e.target.value })}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
              >
                <option value="Pieces">Pieces</option>
                <option value="Kg">Kilograms (Kg)</option>
                <option value="Ltr">Liters (Ltr)</option>
                <option value="Pack">Packs</option>
                <option value="Box">Boxes</option>
              </select>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-2">
                Company/Brand
              </label>
              <input
                type="text"
                value={formData.companyName}
                onChange={(e) => setFormData({ ...formData, companyName: e.target.value })}
                className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
                placeholder="e.g., Amul, Mother Dairy"
              />
            </div>

            <div className="md:col-span-2 grid grid-cols-2 md:grid-cols-4 gap-3 p-4 bg-gray-50 rounded-lg">
              <div>
                <label className="block text-xs font-medium text-gray-700 mb-1">
                  Customer Price (₹) *
                </label>
                <input
                  type="number"
                  value={formData.customerPrice}
                  onChange={(e) => setFormData({ ...formData, customerPrice: parseFloat(e.target.value) || 0 })}
                  step="0.01"
                  min="0"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
                  required
                />
              </div>

              <div>
                <label className="block text-xs font-medium text-gray-700 mb-1">
                  Retailer Price (₹) *
                </label>
                <input
                  type="number"
                  value={formData.retailerPrice}
                  onChange={(e) => setFormData({ ...formData, retailerPrice: parseFloat(e.target.value) || 0 })}
                  step="0.01"
                  min="0"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
                  required
                />
              </div>

              <div>
                <label className="block text-xs font-medium text-gray-700 mb-1">
                  Wholesaler Price (₹) *
                </label>
                <input
                  type="number"
                  value={formData.wholesalerPrice}
                  onChange={(e) => setFormData({ ...formData, wholesalerPrice: parseFloat(e.target.value) || 0 })}
                  step="0.01"
                  min="0"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
                  required
                />
              </div>

              <div>
                <label className="block text-xs font-medium text-gray-700 mb-1">
                  Company Price (₹)
                </label>
                <input
                  type="number"
                  value={formData.companyPrice}
                  onChange={(e) => setFormData({ ...formData, companyPrice: parseFloat(e.target.value) || 0 })}
                  step="0.01"
                  min="0"
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-green-500"
                />
              </div>
            </div>
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
              {product ? 'Update Product' : 'Add Product'}
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export function ProductsPage() {
  const [products, setProducts] = useState<Product[]>([])
  const [loading, setLoading] = useState(true)
  const [productModal, setProductModal] = useState<{ isOpen: boolean; product: Product | null }>({
    isOpen: false,
    product: null,
  })

  useEffect(() => {
    loadProducts()
  }, [])

  const loadProducts = async () => {
    try {
      const data = await productService.getAll()
      setProducts(data)
    } catch (error) {
      toast.error('Failed to load products')
    } finally {
      setLoading(false)
    }
  }

  const handleSaveProduct = async (data: Partial<Product>) => {
    try {
      if (productModal.product) {
        await productService.update(productModal.product.id, data)
        toast.success('Product updated successfully')
      } else {
        await productService.create(data)
        toast.success('Product added successfully')
      }
      setProductModal({ isOpen: false, product: null })
      loadProducts()
    } catch (error: unknown) {
      const message = error instanceof Error ? error.message : 'Failed to save product'
      toast.error(message)
    }
  }

  const handleDeleteProduct = async (id: number, name: string) => {
    if (!window.confirm(`Are you sure you want to delete "${name}"?`)) {
      return
    }

    try {
      await productService.delete(id)
      toast.success('Product deleted successfully')
      loadProducts()
    } catch (error: unknown) {
      const message = error instanceof Error ? error.message : 'Failed to delete product'
      toast.error(message)
    }
  }

  return (
    <div className="space-y-6">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-2xl font-bold text-gray-800">Products</h2>
          <p className="text-gray-600 mt-1">Manage your product catalog and pricing</p>
        </div>
        <button
          onClick={() => setProductModal({ isOpen: true, product: null })}
          className="flex items-center gap-2 px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition"
        >
          <Plus className="w-4 h-4" />
          Add Product
        </button>
      </div>

      <div className="bg-white rounded-xl border border-gray-200 shadow-sm">
        {loading ? (
          <div className="p-8 text-center text-gray-500">Loading products...</div>
        ) : (
          <table className="w-full">
            <thead className="bg-gray-50 border-b border-gray-200">
              <tr>
                <th className="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase">Name</th>
                <th className="px-6 py-4 text-left text-xs font-semibold text-gray-600 uppercase">Company</th>
                <th className="px-6 py-4 text-right text-xs font-semibold text-gray-600 uppercase">Customer Price</th>
                <th className="px-6 py-4 text-right text-xs font-semibold text-gray-600 uppercase">Retailer Price</th>
                <th className="px-6 py-4 text-right text-xs font-semibold text-gray-600 uppercase">Wholesaler Price</th>
                <th className="px-6 py-4 text-right text-xs font-semibold text-gray-600 uppercase">Actions</th>
              </tr>
            </thead>
            <tbody>
              {products.length === 0 ? (
                <tr>
                  <td colSpan={6} className="px-6 py-12 text-center text-gray-500">
                    <Package className="w-12 h-12 mx-auto mb-4 opacity-50" />
                    <p>No products found. Add your first product!</p>
                  </td>
                </tr>
              ) : (
                products.map((product) => (
                  <tr key={product.id} className="border-b border-gray-100 hover:bg-gray-50">
                    <td className="px-6 py-4">
                      <div>
                        <p className="font-medium">{product.name}</p>
                        <p className="text-xs text-gray-500">{product.description}</p>
                      </div>
                    </td>
                    <td className="px-6 py-4 text-sm">{product.companyName || '-'}</td>
                    <td className="px-6 py-4 text-right font-semibold">₹{product.customerPrice}</td>
                    <td className="px-6 py-4 text-right text-green-600">₹{product.retailerPrice}</td>
                    <td className="px-6 py-4 text-right text-blue-600">₹{product.wholesalerPrice}</td>
                    <td className="px-6 py-4 text-right">
                      <div className="flex items-center justify-end gap-2">
                        <button
                          onClick={() => setProductModal({ isOpen: true, product })}
                          className="p-2 text-blue-600 hover:bg-blue-50 rounded-lg transition"
                          title="Edit Product"
                        >
                          <Edit className="w-4 h-4" />
                        </button>
                        <button
                          onClick={() => handleDeleteProduct(product.id, product.name)}
                          className="p-2 text-red-600 hover:bg-red-50 rounded-lg transition"
                          title="Delete Product"
                        >
                          <Trash2 className="w-4 h-4" />
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

      <ProductModal
        isOpen={productModal.isOpen}
        onClose={() => setProductModal({ isOpen: false, product: null })}
        product={productModal.product}
        onSubmit={handleSaveProduct}
      />
    </div>
  )
}
