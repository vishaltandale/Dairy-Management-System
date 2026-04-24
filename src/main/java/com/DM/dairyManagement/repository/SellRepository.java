package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.Sell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SellRepository extends JpaRepository<Sell, Long> {
    List<Sell> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Sell> findAllByOrderByDateDesc();
}
