import { create } from 'zustand'
import { persist } from 'zustand/middleware'
import { authService } from '@/services'

interface User {
  id: number
  name: string
  email: string
  role: string
}

interface AuthState {
  user: User | null
  login: (email: string, password: string) => Promise<void>
  logout: () => Promise<void>
  checkAuth: () => Promise<void>
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      user: null,
      
      login: async (email: string, password: string) => {
        try {
          const response = await authService.login({ email, password })
          set({ user: response.user })
        } catch (error) {
          console.error('Login failed:', error)
          throw error
        }
      },
      
      logout: async () => {
        try {
          await authService.logout()
        } catch (error) {
          console.error('Logout failed:', error)
        } finally {
          set({ user: null })
        }
      },

      checkAuth: async () => {
        try {
          const response = await authService.getCurrentUser()
          set({ user: response.user })
        } catch (error) {
          console.error('Auth check failed:', error)
          set({ user: null })
        }
      },
    }),
    {
      name: 'auth-storage',
    }
  )
)
