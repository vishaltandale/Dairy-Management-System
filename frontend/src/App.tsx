import { BrowserRouter } from 'react-router-dom'
import { Toaster } from 'sonner'
import { AppRoutes } from './routes'
import { ErrorBoundary } from './components/ErrorBoundary'

function App() {
  return (
    <ErrorBoundary>
      <BrowserRouter>
        <AppRoutes />
        <Toaster richColors position="top-right" />
      </BrowserRouter>
    </ErrorBoundary>
  )
}

export default App
