package com.airline.ticketing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BookingRequest {
    
    @NotBlank(message = "PNR is required")
    private String pnr;
    
    // Passenger details
    @NotBlank(message = "Passenger name is required")
    private String passengerName;
    
    @NotNull(message = "Passenger age is required")
    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 120, message = "Age must be less than 120")
    private Integer passengerAge;
    
    // Itinerary details
    @NotBlank(message = "Origin is required")
    private String origin;
    
    @NotBlank(message = "Destination is required")
    private String destination;
    
    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;
    
    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;
    
    @NotBlank(message = "Flight number is required")
    private String flightNumber;
    
    // Fare details
    @NotNull(message = "Base fare is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Base fare must be greater than 0")
    private BigDecimal baseFare;
    
    @NotNull(message = "Tax amount is required")
    @DecimalMin(value = "0.0", message = "Tax amount must be greater than or equal to 0")
    private BigDecimal taxAmount;
    
    private String currency = "USD";
    
    // Constructors
    public BookingRequest() {}
    
    // Getters and Setters
    public String getPnr() {
        return pnr;
    }
    
    public void setPnr(String pnr) {
        this.pnr = pnr;
    }
    
    public String getPassengerName() {
        return passengerName;
    }
    
    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }
    
    public Integer getPassengerAge() {
        return passengerAge;
    }
    
    public void setPassengerAge(Integer passengerAge) {
        this.passengerAge = passengerAge;
    }
    
    public String getOrigin() {
        return origin;
    }
    
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }
    
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }
    
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }
    
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
    
    public String getFlightNumber() {
        return flightNumber;
    }
    
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    
    public BigDecimal getBaseFare() {
        return baseFare;
    }
    
    public void setBaseFare(BigDecimal baseFare) {
        this.baseFare = baseFare;
    }
    
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }
    
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
