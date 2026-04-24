package com.DM.dairyManagement.model;

import jakarta.persistence.*;

@Entity
@Table(name = "stocks")
public class Stock {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    
    private double quantity;
    private double reorderLevel;
    
    // Constructors
    public Stock() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public double getQuantity() {
        return quantity;
    }
    
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    
    public double getReorderLevel() {
        return reorderLevel;
    }
    
    public void setReorderLevel(double reorderLevel) {
        this.reorderLevel = reorderLevel;
    }
}
