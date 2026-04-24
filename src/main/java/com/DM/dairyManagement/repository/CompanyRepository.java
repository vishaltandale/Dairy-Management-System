package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findByNameContainingIgnoreCase(String name);
    Page<Company> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
