package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Stock;
import com.DM.dairyManagement.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StockService {
    
    @Autowired
    private StockRepository stockRepository;
    
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }
    
    public Page<Stock> getAllStock(String search, Pageable pageable) {
        return stockRepository.findAll(pageable);
    }
    
    public Stock getStockById(Long id) {
        return stockRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));
    }
    
    public Stock getStockByProductId(Long productId) {
        return stockRepository.findByProductId(productId)
            .orElse(null);
    }
    
    public Stock saveStock(Stock stock) {
        return stockRepository.save(stock);
    }
    
    public Stock updateStock(Stock stock) {
        return stockRepository.save(stock);
    }
    
    public void deductStock(String productName, int quantity) {
        // Simplified - in production, you'd find by product name
        List<Stock> allStocks = stockRepository.findAll();
        for (Stock stock : allStocks) {
            if (stock.getProduct() != null && stock.getProduct().getName().equals(productName)) {
                stock.setQuantity(stock.getQuantity() - quantity);
                stockRepository.save(stock);
                break;
            }
        }
    }
    
    public List<Stock> getLowStockItems(double threshold) {
        return stockRepository.findByQuantityLessThan(threshold);
    }
    
    public List<Stock> getLowStockItems() {
        return stockRepository.findByQuantityLessThan(50.0); // Default threshold
    }
}
