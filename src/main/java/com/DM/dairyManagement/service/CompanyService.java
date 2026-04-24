package com.DM.dairyManagement.service;

import com.DM.dairyManagement.model.Company;
import com.DM.dairyManagement.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompanyService {
    
    @Autowired
    private CompanyRepository companyRepository;
    
    public Page<Company> getAllCompanies(String search, Pageable pageable) {
        if (search != null && !search.isEmpty()) {
            return companyRepository.findByNameContainingIgnoreCase(search, pageable);
        }
        return companyRepository.findAll(pageable);
    }
    
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Company not found with id: " + id));
    }
    
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }
    
    public Company updateCompany(Long id, Company companyDetails) {
        Company company = getCompanyById(id);
        company.setName(companyDetails.getName());
        company.setContactPerson(companyDetails.getContactPerson());
        company.setContactNo(companyDetails.getContactNo());
        company.setEmail(companyDetails.getEmail());
        company.setAddress(companyDetails.getAddress());
        company.setGstin(companyDetails.getGstin());
        return companyRepository.save(company);
    }
    
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }
    
    public List<Company> searchCompaniesByName(String name) {
        return companyRepository.findByNameContainingIgnoreCase(name);
    }
}
