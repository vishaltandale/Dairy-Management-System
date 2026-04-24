import { useEffect, useState } from 'react'
import { Building2, Phone, Mail, MapPin, Plus, X, Search, ChevronLeft, ChevronRight } from 'lucide-react'
import { companyService, type Company } from '@/services'
import { toast } from 'sonner'

interface CompanyModalProps {
  isOpen: boolean
  onClose: () => void
  company?: Partial<Company> | null
  onSubmit: (data: Partial<Company>) => void
}

function CompanyModal({ isOpen, onClose, company, onSubmit }: CompanyModalProps) {
  const [formData, setFormData] = useState({
    name: '',
    contactPerson: '',
    contactNo: '',
    email: '',
    address: '',
    gstin: '',
  })

  useEffect(() => {
    if (company) {
      setFormData({
        name: company.name || '',
        contactPerson: company.contactPerson || '',
        contactNo: company.contactNo || '',
        email: company.email || '',
        address: company.address || '',
        gstin: company.gstin || '',
      })
    } else {
      setFormData({
        name: '',
        contactPerson: '',
        contactNo: '',
        email: '',
        address: '',
        gstin: '',
      })
    }
  }, [company, isOpen])

  if (!isOpen) return null

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    
    if (!formData.name.trim()) {
      toast.error('Company name is required')
      return
    }

    if (!formData.contactNo.trim() || formData.contactNo.length !== 10) {
      toast.error('Valid 10-digit contact number is required')
      return
    }

    onSubmit(formData)
  }

  return (
    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div className="bg-white rounded-xl shadow-xl w-full max-w-lg mx-4">
        <div className="flex items-center justify-between p-6 border-b border-gray-200">
          <h3 className="text-lg font-bold">
            {company?.id ? 'Edit' : 'Add'} Company
          </h3>
          <button onClick={onClose} className="text-gray-400 hover:text-gray-600">
            <X className="w-5 h-5" />
          </button>
        </div>

        <form onSubmit={handleSubmit} className="p-6 space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Company Name *
            </label>
            <input
              type="text"
              value={formData.name}
              onChange={(e) => setFormData({ ...formData, name: e.target.value })}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
              placeholder="Enter company name"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Contact Person
            </label>
            <input
              type="text"
              value={formData.contactPerson}
              onChange={(e) => setFormData({ ...formData, contactPerson: e.target.value })}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
              placeholder="Contact person name"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Contact Number *
            </label>
            <input
              type="tel"
              value={formData.contactNo}
              onChange={(e) => setFormData({ ...formData, contactNo: e.target.value.replace(/\D/g, '').slice(0, 10) })}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
              placeholder="10-digit contact number"
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
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
              placeholder="company@example.com"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              Address
            </label>
            <textarea
              value={formData.address}
              onChange={(e) => setFormData({ ...formData, address: e.target.value })}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
              placeholder="Company address"
              rows={2}
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-2">
              GSTIN
            </label>
            <input
              type="text"
              value={formData.gstin}
              onChange={(e) => setFormData({ ...formData, gstin: e.target.value.toUpperCase() })}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
              placeholder="22AAAAA0000A1Z5"
              maxLength={15}
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
              className="flex-1 px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 font-semibold"
            >
              {company?.id ? 'Update' : 'Add'} Company
            </button>
          </div>
        </form>
      </div>
    </div>
  )
}

export function CompaniesPage() {
  const [companies, setCompanies] = useState<Company[]>([])
  const [loading, setLoading] = useState(true)
  const [companyModal, setCompanyModal] = useState<{
    isOpen: boolean
    company: Partial<Company> | null
  }>({
    isOpen: false,
    company: null,
  })
  
  // Pagination state
  const [currentPage, setCurrentPage] = useState(0)
  const [pageSize] = useState(10)
  const [totalElements, setTotalElements] = useState(0)
  const [searchTerm, setSearchTerm] = useState('')

  useEffect(() => {
    loadCompanies()
  }, [currentPage, searchTerm])

  const loadCompanies = async () => {
    try {
      setLoading(true)
      const response = await companyService.getAll(currentPage, pageSize, searchTerm || undefined)
      setCompanies(response.content)
      setTotalElements(response.totalElements)
    } catch (error) {
      toast.error('Failed to load companies')
    } finally {
      setLoading(false)
    }
  }

  const totalPages = Math.ceil(totalElements / pageSize)

  const stats = {
    totalCompanies: totalElements,
  }

  const handleSaveCompany = async (data: Partial<Company>) => {
    try {
      if (companyModal.company?.id) {
        await companyService.update(companyModal.company.id, data)
        toast.success('Company updated successfully')
      } else {
        await companyService.create(data)
        toast.success('Company added successfully')
      }
      setCompanyModal({ isOpen: false, company: null })
      loadCompanies()
    } catch (error: unknown) {
      const message = error instanceof Error ? error.message : 'Failed to save company'
      toast.error(message)
    }
  }

  const handleDeleteCompany = async (id: number, name: string) => {
    if (!window.confirm(`Are you sure you want to delete "${name}"?`)) {
      return
    }

    try {
      await companyService.delete(id)
      toast.success('Company deleted successfully')
      loadCompanies()
    } catch (error: unknown) {
      const message = error instanceof Error ? error.message : 'Failed to delete company'
      toast.error(message)
    }
  }

  const handleSearch = (e: React.FormEvent) => {
    e.preventDefault()
    setCurrentPage(0)
    loadCompanies()
  }

  return (
    <div className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <Building2 className="w-5 h-5 text-blue-600" />
          </div>
          <p className="text-2xl font-bold mt-2">{stats.totalCompanies}</p>
          <p className="text-xs text-gray-600">Total Companies</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-gray-200 md:col-span-2">
          <form onSubmit={handleSearch} className="flex gap-2">
            <div className="flex-1 relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-gray-400" />
              <input
                type="text"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                placeholder="Search companies..."
                className="w-full pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-500"
              />
            </div>
            <button
              type="submit"
              className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
            >
              Search
            </button>
            <button
              type="button"
              onClick={() => {
                setSearchTerm('')
                setCurrentPage(0)
              }}
              className="px-4 py-2 border border-gray-300 rounded-lg hover:bg-gray-50 transition"
            >
              Clear
            </button>
          </form>
        </div>
      </div>

      <div className="bg-white rounded-xl border border-gray-200 shadow-sm">
        <div className="p-6 border-b border-gray-200">
          <div className="flex items-center justify-between">
            <h3 className="text-lg font-semibold">Supplier Companies</h3>
            <button
              onClick={() => setCompanyModal({ isOpen: true, company: null })}
              className="flex items-center gap-2 px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition"
            >
              <Plus className="w-4 h-4" />
              Add Company
            </button>
          </div>
        </div>

        {loading ? (
          <div className="p-8 text-center text-gray-500">Loading companies...</div>
        ) : (
          <>
            <table className="w-full">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Company Name</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Contact Person</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Contact Info</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Address</th>
                  <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">GSTIN</th>
                  <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Actions</th>
                </tr>
              </thead>
              <tbody>
                {companies.length === 0 ? (
                  <tr>
                    <td colSpan={6} className="px-6 py-12 text-center text-gray-500">
                      No companies found
                    </td>
                  </tr>
                ) : (
                  companies.map((company) => (
                    <tr key={company.id} className="border-b border-gray-100 hover:bg-gray-50">
                      <td className="px-6 py-4 font-medium">{company.name}</td>
                      <td className="px-6 py-4 text-sm">{company.contactPerson || '-'}</td>
                      <td className="px-6 py-4">
                        <div className="flex flex-col gap-1">
                          <div className="flex items-center gap-2 text-sm">
                            <Phone className="w-3 h-3" />
                            {company.contactNo}
                          </div>
                          {company.email && (
                            <div className="flex items-center gap-2 text-sm text-gray-600">
                              <Mail className="w-3 h-3" />
                              {company.email}
                            </div>
                          )}
                        </div>
                      </td>
                      <td className="px-6 py-4 text-sm max-w-xs truncate">
                        {company.address ? (
                          <div className="flex items-start gap-2">
                            <MapPin className="w-3 h-3 mt-0.5 flex-shrink-0" />
                            <span className="truncate">{company.address}</span>
                          </div>
                        ) : (
                          '-'
                        )}
                      </td>
                      <td className="px-6 py-4 text-sm font-mono">{company.gstin || '-'}</td>
                      <td className="px-6 py-4 text-right">
                        <div className="flex items-center justify-end gap-2">
                          <button
                            onClick={() => setCompanyModal({
                              isOpen: true,
                              company,
                            })}
                            className="px-3 py-1 text-blue-600 hover:bg-blue-50 rounded-lg text-sm transition"
                          >
                            Edit
                          </button>
                          <button
                            onClick={() => handleDeleteCompany(company.id, company.name)}
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

            {/* Pagination */}
            {totalPages > 1 && (
              <div className="flex items-center justify-between px-6 py-4 border-t border-gray-200">
                <p className="text-sm text-gray-600">
                  Showing {currentPage * pageSize + 1} to {Math.min((currentPage + 1) * pageSize, totalElements)} of {totalElements} companies
                </p>
                <div className="flex gap-2">
                  <button
                    onClick={() => setCurrentPage(prev => Math.max(0, prev - 1))}
                    disabled={currentPage === 0}
                    className="px-3 py-1 border border-gray-300 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed flex items-center gap-1"
                  >
                    <ChevronLeft className="w-4 h-4" />
                    Previous
                  </button>
                  <button
                    onClick={() => setCurrentPage(prev => Math.min(totalPages - 1, prev + 1))}
                    disabled={currentPage >= totalPages - 1}
                    className="px-3 py-1 border border-gray-300 rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed flex items-center gap-1"
                  >
                    Next
                    <ChevronRight className="w-4 h-4" />
                  </button>
                </div>
              </div>
            )}
          </>
        )}
      </div>

      <CompanyModal
        isOpen={companyModal.isOpen}
        onClose={() => setCompanyModal({ isOpen: false, company: null })}
        company={companyModal.company}
        onSubmit={handleSaveCompany}
      />
    </div>
  )
}
