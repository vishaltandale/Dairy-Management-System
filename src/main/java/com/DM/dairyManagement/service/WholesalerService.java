package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Wholesaler;
import com.DM.dairyManagement.repository.WholesalerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WholesalerService {
    
    @Autowired
    private WholesalerRepository wholesalerRepository;
    
    public Wholesaler saveWholesaler(Wholesaler wholesaler) {
        return wholesalerRepository.save(wholesaler);
    }
    
    public Page<Wholesaler> getAllWholesalers(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return wholesalerRepository.findByNameContainingIgnoreCase(search, pageable);
        }
        return wholesalerRepository.findAll(pageable);
    }
    
    public Wholesaler getWholesalerById(Long id) {
        return wholesalerRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Wholesaler not found with id: " + id));
    }
    
    public Wholesaler updateWholesaler(Wholesaler wholesaler) {
        return wholesalerRepository.save(wholesaler);
    }
    
    public void deleteWholesaler(Long id) {
        wholesalerRepository.deleteById(id);
    }
    
    public List<Wholesaler> searchWholesalers(String keyword) {
        return wholesalerRepository.findByNameContainingIgnoreCase(keyword);
    }
}
