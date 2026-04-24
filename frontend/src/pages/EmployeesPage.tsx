import { useEffect, useState } from 'react'
import { Users, Phone, Calendar, DollarSign, Plus } from 'lucide-react'
import { employeeService, type Employee } from '@/services'
import { toast } from 'sonner'

export function EmployeesPage() {
  const [employees, setEmployees] = useState<Employee[]>([])
  const [loading, setLoading] = useState(true)

  useEffect(() => {
    loadEmployees()
  }, [])

  const loadEmployees = async () => {
    try {
      const data = await employeeService.getAll()
      setEmployees(data)
    } catch (error) {
      toast.error('Failed to load employees')
    } finally {
      setLoading(false)
    }
  }

  const stats = {
    totalEmployees: employees.length,
    totalSalary: employees.reduce((sum, e) => sum + (e.monthlySalary || 0), 0),
    roles: {
      MANAGER: employees.filter(e => e.role === 'MANAGER').length,
      EMPLOYEE: employees.filter(e => e.role === 'EMPLOYEE').length,
      ACCOUNTANT: employees.filter(e => e.role === 'ACCOUNTANT').length,
    }
  }

  const getRoleLabel = (role: string) => {
    return role.charAt(0) + role.slice(1).toLowerCase()
  }

  const getRoleColor = (role: string) => {
    switch (role) {
      case 'MANAGER': return 'bg-purple-100 text-purple-700'
      case 'ACCOUNTANT': return 'bg-blue-100 text-blue-700'
      default: return 'bg-green-100 text-green-700'
    }
  }

  return (
    <div className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-4 gap-4">
        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <Users className="w-5 h-5 text-blue-600" />
          </div>
          <p className="text-2xl font-bold mt-2">{stats.totalEmployees}</p>
          <p className="text-xs text-gray-600">Total Employees</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <Users className="w-5 h-5 text-green-600" />
          </div>
          <p className="text-2xl font-bold mt-2">{stats.totalEmployees}</p>
          <p className="text-xs text-gray-600">All Active</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <DollarSign className="w-5 h-5 text-purple-600" />
          </div>
          <p className="text-2xl font-bold mt-2">₹{stats.totalSalary.toLocaleString()}</p>
          <p className="text-xs text-gray-600">Monthly Salary</p>
        </div>

        <div className="bg-white p-4 rounded-xl border border-gray-200">
          <div className="flex items-center justify-between">
            <Calendar className="w-5 h-5 text-orange-600" />
          </div>
          <p className="text-2xl font-bold mt-2">{stats.roles.MANAGER}</p>
          <p className="text-xs text-gray-600">Managers</p>
        </div>
      </div>

      <div className="bg-white rounded-xl border border-gray-200 shadow-sm">
        <div className="p-6 border-b border-gray-200">
          <div className="flex items-center justify-between">
            <h3 className="text-lg font-semibold">Employee Directory</h3>
            <button className="flex items-center gap-2 px-4 py-2 bg-green-600 text-white rounded-lg hover:bg-green-700 transition">
              <Plus className="w-4 h-4" />
              Add Employee
            </button>
          </div>
        </div>

        {loading ? (
          <div className="p-8 text-center text-gray-500">Loading employees...</div>
        ) : (
          <table className="w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Name</th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Contact</th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Role</th>
                <th className="px-6 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Join Date</th>
                <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Salary</th>
                <th className="px-6 py-3 text-center text-xs font-semibold text-gray-600 uppercase">Status</th>
                <th className="px-6 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Actions</th>
              </tr>
            </thead>
            <tbody>
              {employees.length === 0 ? (
                <tr>
                  <td colSpan={7} className="px-6 py-12 text-center text-gray-500">
                    No employees found
                  </td>
                </tr>
              ) : (
                employees.map((employee) => (
                  <tr key={employee.id} className="border-b border-gray-100 hover:bg-gray-50">
                    <td className="px-6 py-4">
                      <div>
                        <p className="font-medium">{employee.name}</p>
                        <p className="text-xs text-gray-500">ID: EMP{String(employee.id).padStart(3, '0')}</p>
                      </div>
                    </td>
                    <td className="px-6 py-4">
                      <div className="flex flex-col gap-1 text-sm">
                        <div className="flex items-center gap-2">
                          <Phone className="w-3 h-3" />
                          {employee.mobileNumber}
                        </div>
                      </div>
                    </td>
                    <td className="px-6 py-4">
                      <span className={`px-2 py-1 text-xs font-semibold rounded-full ${
                        getRoleColor(employee.role)
                      }`}>
                        {getRoleLabel(employee.role)}
                      </span>
                    </td>
                    <td className="px-6 py-4 text-sm">
                      {employee.joiningDate ? new Date(employee.joiningDate).toLocaleDateString() : '-'}
                    </td>
                    <td className="px-6 py-4 text-right font-semibold">
                      ₹{(employee.monthlySalary || 0).toLocaleString()}
                    </td>
                    <td className="px-6 py-4 text-center">
                      <span className="px-2 py-1 text-xs font-semibold rounded-full bg-green-100 text-green-700">
                        Active
                      </span>
                    </td>
                    <td className="px-6 py-4 text-right">
                      <button className="px-3 py-1 text-blue-600 hover:bg-blue-50 rounded-lg text-sm transition">
                        View Details
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
        <h3 className="text-lg font-semibold mb-4">Role Distribution</h3>
        <div className="grid grid-cols-3 gap-4">
          {Object.entries(stats.roles).map(([role, count]) => (
            <div key={role} className="p-4 bg-gray-50 rounded-lg text-center">
              <p className="text-3xl font-bold text-green-600">{count}</p>
              <p className="text-sm text-gray-600 mt-1">{getRoleLabel(role)}{count > 1 ? 's' : ''}</p>
            </div>
          ))}
        </div>
      </div>
    </div>
  )
}
