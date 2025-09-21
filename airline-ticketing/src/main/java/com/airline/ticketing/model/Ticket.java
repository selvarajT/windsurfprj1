package com.airline.ticketing.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
public class Ticket {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Ticket number is required")
    @Column(nullable = false, unique = true, length = 13)
    private String ticketNumber;
    
    @NotBlank(message = "PNR is required")
    @Column(nullable = false)
    private String pnr;
    
    @NotNull(message = "Passenger is required")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passenger_id", referencedColumnName = "id")
    private Passenger passenger;
    
    @NotNull(message = "Itinerary is required")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "itinerary_id", referencedColumnName = "id")
    private Itinerary itinerary;
    
    @NotNull(message = "Fare details are required")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fare_details_id", referencedColumnName = "id")
    private FareDetails fareDetails;
    
    @Column(nullable = false)
    private LocalDateTime bookingTime;
    
    @Enumerated(EnumType.STRING)
    private TicketStatus status = TicketStatus.CONFIRMED;
    
    public enum TicketStatus {
        CONFIRMED, CANCELLED, CHECKED_IN, BOARDED
    }
    
    // Constructors
    public Ticket() {
        this.bookingTime = LocalDateTime.now();
    }
    
    public Ticket(String ticketNumber, String pnr, Passenger passenger, 
                 Itinerary itinerary, FareDetails fareDetails) {
        this.ticketNumber = ticketNumber;
        this.pnr = pnr;
        this.passenger = passenger;
        this.itinerary = itinerary;
        this.fareDetails = fareDetails;
        this.bookingTime = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public Passenger getPassenger() {
        return passenger;
    }
    
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
    
    public Itinerary getItinerary() {
        return itinerary;
    }
    
    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }
    
    public FareDetails getFareDetails() {
        return fareDetails;
    }
    
    public void setFareDetails(FareDetails fareDetails) {
        this.fareDetails = fareDetails;
    }
    
    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
    
    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }
    
    public TicketStatus getStatus() {
        return status;
    }
    
    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
