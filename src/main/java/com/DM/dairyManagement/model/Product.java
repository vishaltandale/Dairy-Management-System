package com.DM.dairyManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Product name is required")
    private String name;
    
    private String description;
    private String hsnCode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private Unit unit;
    
    private double customerPrice;
    private double retailerPrice;
    private double wholesalerPrice;
    private double companyPrice;
    
    // Constructors
    public Product() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getHsnCode() {
        return hsnCode;
    }
    
    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }
    
    public Company getCompany() {
        return company;
    }
    
    public void setCompany(Company company) {
        this.company = company;
    }
    
    public Unit getUnit() {
        return unit;
    }
    
    public void setUnit(Unit unit) {
        this.unit = unit;
    }
    
    public double getCustomerPrice() {
        return customerPrice;
    }
    
    public void setCustomerPrice(double customerPrice) {
        this.customerPrice = customerPrice;
    }
    
    public double getRetailerPrice() {
        return retailerPrice;
    }
    
    public void setRetailerPrice(double retailerPrice) {
        this.retailerPrice = retailerPrice;
    }
    
    public double getWholesalerPrice() {
        return wholesalerPrice;
    }
    
    public void setWholesalerPrice(double wholesalerPrice) {
        this.wholesalerPrice = wholesalerPrice;
    }
    
    public double getCompanyPrice() {
        return companyPrice;
    }
    
    public void setCompanyPrice(double companyPrice) {
        this.companyPrice = companyPrice;
    }
}
