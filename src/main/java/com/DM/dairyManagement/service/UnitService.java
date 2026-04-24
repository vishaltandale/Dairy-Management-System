package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Unit;
import com.DM.dairyManagement.repository.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UnitService {
    
    @Autowired
    private UnitRepository unitRepository;
    
    public Unit saveUnit(Unit unit) {
        return unitRepository.save(unit);
    }
    
    public Page<Unit> getAllUnits(String search, Pageable pageable) {
        return unitRepository.findAll(pageable);
    }
    
    public Unit getUnitById(Long id) {
        return unitRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Unit not found with id: " + id));
    }
    
    public Unit updateUnit(Unit unit) {
        return unitRepository.save(unit);
    }
    
    public void deleteUnit(Long id) {
        unitRepository.deleteById(id);
    }
}
