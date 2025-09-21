package com.airline.ticketing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;

@Entity
@Table(name = "fare_details")
public class FareDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Base fare is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Base fare must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal baseFare;
    
    @NotNull(message = "Tax amount is required")
    @DecimalMin(value = "0.0", message = "Tax amount must be greater than or equal to 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal taxAmount;
    
    @NotNull(message = "Total fare is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Total fare must be greater than 0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalFare;
    
    private String currency = "USD";
    
    // Constructors
    public FareDetails() {}
    
    public FareDetails(BigDecimal baseFare, BigDecimal taxAmount, String currency) {
        this.baseFare = baseFare;
        this.taxAmount = taxAmount;
        this.totalFare = baseFare.add(taxAmount);
        this.currency = currency;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public BigDecimal getBaseFare() {
        return baseFare;
    }
    
    public void setBaseFare(BigDecimal baseFare) {
        this.baseFare = baseFare;
        if (this.taxAmount != null) {
            this.totalFare = baseFare.add(this.taxAmount);
        }
    }
    
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }
    
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
        if (this.baseFare != null) {
            this.totalFare = this.baseFare.add(taxAmount);
        }
    }
    
    public BigDecimal getTotalFare() {
        return totalFare;
    }
    
    public void setTotalFare(BigDecimal totalFare) {
        this.totalFare = totalFare;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
