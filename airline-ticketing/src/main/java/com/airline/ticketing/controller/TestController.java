package com.airline.ticketing.controller;

import com.airline.ticketing.model.*;
import com.airline.ticketing.service.TicketNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*")
public class TestController {
    
    @Autowired
    private TicketNumberGenerator ticketNumberGenerator;
    
    /**
     * Test endpoint to generate sample ticket numbers
     */
    @GetMapping("/generate-ticket-number")
    public Map<String, Object> testTicketGeneration() {
        // Create sample data
        Passenger passenger = new Passenger("John Doe", 30);
        
        Itinerary itinerary = new Itinerary(
            "NYC", "LAX", 
            LocalDateTime.of(2024, 1, 15, 10, 0),
            LocalDateTime.of(2024, 1, 15, 13, 0),
            "AA101"
        );
        
        FareDetails fareDetails = new FareDetails(
            new BigDecimal("299.99"),
            new BigDecimal("50.00"),
            "USD"
        );
        
        String pnr = "ABC123";
        
        // Generate ticket number
        String ticketNumber = ticketNumberGenerator.generateTicketNumber(pnr, passenger, itinerary, fareDetails);
        
        Map<String, Object> response = new HashMap<>();
        response.put("ticketNumber", ticketNumber);
        response.put("isValid", ticketNumberGenerator.isValidTicketNumber(ticketNumber));
        response.put("length", ticketNumber.length());
        response.put("pnr", pnr);
        response.put("passengerName", passenger.getName());
        response.put("passengerAge", passenger.getAge());
        response.put("origin", itinerary.getOrigin());
        response.put("destination", itinerary.getDestination());
        response.put("flightNumber", itinerary.getFlightNumber());
        response.put("totalFare", fareDetails.getTotalFare());
        
        return response;
    }
    
    /**
     * Test multiple ticket number generations to verify uniqueness
     */
    @GetMapping("/generate-multiple")
    public Map<String, Object> testMultipleGeneration() {
        Map<String, Object> response = new HashMap<>();
        
        // Generate 5 ticket numbers with same input to test uniqueness
        for (int i = 1; i <= 5; i++) {
            Passenger passenger = new Passenger("Jane Smith", 25);
            Itinerary itinerary = new Itinerary(
                "BOS", "SFO",
                LocalDateTime.of(2024, 2, 20, 14, 30),
                LocalDateTime.of(2024, 2, 20, 17, 45),
                "DL205"
            );
            FareDetails fareDetails = new FareDetails(
                new BigDecimal("450.00"),
                new BigDecimal("75.50"),
                "USD"
            );
            
            String ticketNumber = ticketNumberGenerator.generateTicketNumber("XYZ789", passenger, itinerary, fareDetails);
            response.put("ticket" + i, ticketNumber);
        }
        
        return response;
    }
}
