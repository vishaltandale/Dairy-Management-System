package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Retailer;
import com.DM.dairyManagement.repository.RetailerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RetailerService {
    
    @Autowired
    private RetailerRepository retailerRepository;
    
    public Retailer saveRetailer(Retailer retailer) {
        return retailerRepository.save(retailer);
    }
    
    public Page<Retailer> getAllRetailers(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return retailerRepository.findByNameContainingIgnoreCase(search, pageable);
        }
        return retailerRepository.findAll(pageable);
    }
    
    public Retailer getRetailerById(Long id) {
        return retailerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Retailer not found with id: " + id));
    }
    
    public Retailer updateRetailer(Retailer retailer) {
        return retailerRepository.save(retailer);
    }
    
    public void deleteRetailer(Long id) {
        retailerRepository.deleteById(id);
    }
    
    public List<Retailer> searchRetailers(String keyword) {
        return retailerRepository.findByNameContainingIgnoreCase(keyword);
    }
}
