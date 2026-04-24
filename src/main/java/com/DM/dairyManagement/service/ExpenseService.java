package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.OtherExpense;
import com.DM.dairyManagement.repository.OtherExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExpenseService {
    
    @Autowired
    private OtherExpenseRepository expenseRepository;
    
    public OtherExpense saveExpense(OtherExpense expense) {
        return expenseRepository.save(expense);
    }
    
    public Page<OtherExpense> getAllExpenses(String search, Pageable pageable) {
        return expenseRepository.findAll(pageable);
    }
    
    public OtherExpense getExpenseById(Long id) {
        return expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
    }
    
    public OtherExpense updateExpense(OtherExpense expense) {
        return expenseRepository.save(expense);
    }
    
    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }
}
