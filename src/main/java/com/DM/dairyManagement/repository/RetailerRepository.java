package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Retailer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetailerRepository extends JpaRepository<Retailer, Long> {
    List<Retailer> findByNameContainingIgnoreCase(String name);
    Page<Retailer> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Retailer> findByCompanyId(Long companyId);
}
