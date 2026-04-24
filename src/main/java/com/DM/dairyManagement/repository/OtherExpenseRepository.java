package com.DM.dairyManagement.repository;

import com.DM.dairyManagement.model.OtherExpense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OtherExpenseRepository extends JpaRepository<OtherExpense, Long> {
    List<OtherExpense> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<OtherExpense> findAllByOrderByDateDesc();
    Page<OtherExpense> findAll(Pageable pageable);
}
