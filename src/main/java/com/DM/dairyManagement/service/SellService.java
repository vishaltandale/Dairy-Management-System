package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Sell;
import com.DM.dairyManagement.repository.SellRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class SellService {
    
    @Autowired
    private SellRepository sellRepository;
    
    public Sell saveSell(Sell sell) {
        return sellRepository.save(sell);
    }
    
    public List<Sell> getAllSells() {
        return sellRepository.findAllByOrderByDateDesc();
    }
    
    public Sell getSellById(Long id) {
        return sellRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sell not found with id: " + id));
    }
    
    public List<Sell> getSellsByDateRange(LocalDate startDate, LocalDate endDate) {
        return sellRepository.findByDateBetween(startDate, endDate);
    }
    
    public List<Sell> getSellsByCustomerName(String customerName) {
        return sellRepository.findAll().stream()
            .filter(s -> s.getCustomerName() != null && s.getCustomerName().toLowerCase().contains(customerName.toLowerCase()))
            .toList();
    }
    
    public void deleteSell(Long id) {
        sellRepository.deleteById(id);
    }
}
