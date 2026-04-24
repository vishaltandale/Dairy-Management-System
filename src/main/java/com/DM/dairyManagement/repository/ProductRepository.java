package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
    List<Product> findByCompanyId(Long companyId);
    
    // Analytics query - Top products by quantity sold
    @Query(value = "SELECT bi.product_name as productName, SUM(bi.quantity) as totalQuantity, " +
                   "SUM(bi.total) as totalRevenue " +
                   "FROM bill_items bi GROUP BY bi.product_name " +
                   "ORDER BY totalQuantity DESC LIMIT :limit", nativeQuery = true)
    List<Map<String, Object>> getTopProductsByQuantity(@Param("limit") int limit);
}
