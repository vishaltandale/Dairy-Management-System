package com.DM.dairyManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bills")
public class Bill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private Long billNo;
    
    @NotBlank(message = "Customer name is required")
    private String fullName;
    
    private String mobileNumber;
    private LocalDate date;
    private String customerType;
    
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();
    
    private BigDecimal subtotal;
    private BigDecimal cgst;
    private BigDecimal sgst;
    private BigDecimal discount;
    private BigDecimal total;
    private BigDecimal paidAmount;
    private BigDecimal balanceDue;
    
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (date == null) {
            date = LocalDate.now();
        }
    }
    
    // Constructors
    public Bill() {
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getBillNo() {
        return billNo;
    }
    
    public void setBillNo(Long billNo) {
        this.billNo = billNo;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getMobileNumber() {
        return mobileNumber;
    }
    
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    
    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    public String getCustomerType() {
        return customerType;
    }
    
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
    
    public List<Item> getItems() {
        return items;
    }
    
    public void setItems(List<Item> items) {
        this.items = items;
    }
    
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
    
    public BigDecimal getCgst() {
        return cgst;
    }
    
    public void setCgst(BigDecimal cgst) {
        this.cgst = cgst;
    }
    
    public BigDecimal getSgst() {
        return sgst;
    }
    
    public void setSgst(BigDecimal sgst) {
        this.sgst = sgst;
    }
    
    public BigDecimal getDiscount() {
        return discount;
    }
    
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }
    
    public BigDecimal getTotal() {
        return total;
    }
    
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    
    public BigDecimal getPaidAmount() {
        return paidAmount;
    }
    
    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }
    
    public BigDecimal getBalanceDue() {
        return balanceDue;
    }
    
    public void setBalanceDue(BigDecimal balanceDue) {
        this.balanceDue = balanceDue;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public void addItem(Item item) {
        items.add(item);
        item.setBill(this);
    }
}
