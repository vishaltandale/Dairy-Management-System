package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Wholesaler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WholesalerRepository extends JpaRepository<Wholesaler, Long> {
    List<Wholesaler> findByNameContainingIgnoreCase(String name);
    Page<Wholesaler> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Wholesaler> findByCompanyId(Long companyId);
}
