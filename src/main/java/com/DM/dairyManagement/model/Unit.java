package com.DM.dairyManagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "units")
public class Unit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Unit name is required")
    private String name;
    
    private String shortName;
    private double kg;
    private double ltr;
    
    // Constructors
    public Unit() {
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
    
    public String getShortName() {
        return shortName;
    }
    
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public double getKg() {
        return kg;
    }
    
    public void setKg(double kg) {
        this.kg = kg;
    }
    
    public double getLtr() {
        return ltr;
    }
    
    public void setLtr(double ltr) {
        this.ltr = ltr;
    }
}
