package com.DM.dairyManagement.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "other_expenses")
public class OtherExpense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDate date;
    private double shopRent;
    private double lightBill;
    private double diesel;
    private double otherExpense;
    private String remarks;
    
    // Constructors
    public OtherExpense() {
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public double getShopRent() { return shopRent; }
    public void setShopRent(double shopRent) { this.shopRent = shopRent; }
    public double getLightBill() { return lightBill; }
    public void setLightBill(double lightBill) { this.lightBill = lightBill; }
    public double getDiesel() { return diesel; }
    public void setDiesel(double diesel) { this.diesel = diesel; }
    public double getOtherExpense() { return otherExpense; }
    public void setOtherExpense(double otherExpense) { this.otherExpense = otherExpense; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    
    public double getTotal() {
        return shopRent + lightBill + diesel + otherExpense;
    }
}
