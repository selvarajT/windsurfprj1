package com.airline.ticketing.dto;

import com.airline.ticketing.model.Ticket;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class BookingResponse {
    
    private String ticketNumber;
    private String pnr;
    private String passengerName;
    private Integer passengerAge;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String flightNumber;
    private BigDecimal totalFare;
    private String currency;
    private LocalDateTime bookingTime;
    private String status;
    
    // Constructors
    public BookingResponse() {}
    
    public BookingResponse(Ticket ticket) {
        this.ticketNumber = ticket.getTicketNumber();
        this.pnr = ticket.getPnr();
        this.passengerName = ticket.getPassenger().getName();
        this.passengerAge = ticket.getPassenger().getAge();
        this.origin = ticket.getItinerary().getOrigin();
        this.destination = ticket.getItinerary().getDestination();
        this.departureTime = ticket.getItinerary().getDepartureTime();
        this.arrivalTime = ticket.getItinerary().getArrivalTime();
        this.flightNumber = ticket.getItinerary().getFlightNumber();
        this.totalFare = ticket.getFareDetails().getTotalFare();
        this.currency = ticket.getFareDetails().getCurrency();
        this.bookingTime = ticket.getBookingTime();
        this.status = ticket.getStatus().toString();
    }
    
    // Getters and Setters
    public String getTicketNumber() {
        return ticketNumber;
    }
    
    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
    
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
    
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
    
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
