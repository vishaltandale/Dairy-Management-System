package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Employee;
import com.DM.dairyManagement.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeService {
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public Page<Employee> getAllEmployees(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return employeeRepository.findByNameContainingIgnoreCase(search, pageable);
        }
        return employeeRepository.findAll(pageable);
    }
    
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }
    
    public Employee updateEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
