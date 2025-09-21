package com.airline.ticketing.service;

import com.airline.ticketing.model.Passenger;
import com.airline.ticketing.model.Itinerary;
import com.airline.ticketing.model.FareDetails;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TicketNumberGenerator {
    
    private final SecureRandom secureRandom = new SecureRandom();
    
    /**
     * Generates a 13-digit ticket number based on passenger details, PNR, itinerary, and fare
     * Algorithm combines hash of input data with random elements to ensure uniqueness
     */
    public String generateTicketNumber(String pnr, Passenger passenger, 
                                     Itinerary itinerary, FareDetails fareDetails) {
        try {
            // Create input string combining all parameters
            String inputData = buildInputString(pnr, passenger, itinerary, fareDetails);
            
            // Generate hash from input data
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(inputData.getBytes());
            
            // Convert hash to numeric string
            StringBuilder hashNumeric = new StringBuilder();
            for (byte b : hashBytes) {
                hashNumeric.append(Math.abs(b) % 10);
            }
            
            // Take first 8 digits from hash
            String hashPart = hashNumeric.substring(0, 8);
            
            // Generate 4 random digits
            String randomPart = String.format("%04d", secureRandom.nextInt(10000));
            
            // Add timestamp-based digit for additional uniqueness
            String timePart = String.valueOf(System.currentTimeMillis() % 10);
            
            // Combine to create 13-digit number
            String ticketNumber = hashPart + randomPart + timePart;
            
            // Ensure exactly 13 digits
            return ticketNumber.substring(0, 13);
            
        } catch (NoSuchAlgorithmException e) {
            // Fallback to pure random generation
            return generateFallbackTicketNumber();
        }
    }
    
    private String buildInputString(String pnr, Passenger passenger, 
                                  Itinerary itinerary, FareDetails fareDetails) {
        StringBuilder input = new StringBuilder();
        
        // Add PNR
        input.append(pnr != null ? pnr : "");
        
        // Add passenger details
        if (passenger != null) {
            input.append(passenger.getName() != null ? passenger.getName().replaceAll("\\s+", "").toUpperCase() : "");
            input.append(passenger.getAge() != null ? passenger.getAge().toString() : "");
        }
        
        // Add itinerary details
        if (itinerary != null) {
            input.append(itinerary.getOrigin() != null ? itinerary.getOrigin().toUpperCase() : "");
            input.append(itinerary.getDestination() != null ? itinerary.getDestination().toUpperCase() : "");
            input.append(itinerary.getFlightNumber() != null ? itinerary.getFlightNumber() : "");
            
            if (itinerary.getDepartureTime() != null) {
                input.append(itinerary.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
            }
        }
        
        // Add fare details
        if (fareDetails != null) {
            input.append(fareDetails.getTotalFare() != null ? 
                        fareDetails.getTotalFare().toString().replace(".", "") : "");
        }
        
        // Add current timestamp for uniqueness
        input.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        
        return input.toString();
    }
    
    private String generateFallbackTicketNumber() {
        StringBuilder ticketNumber = new StringBuilder();
        for (int i = 0; i < 13; i++) {
            ticketNumber.append(secureRandom.nextInt(10));
        }
        return ticketNumber.toString();
    }
    
    /**
     * Validates if a ticket number is in correct format (13 digits)
     */
    public boolean isValidTicketNumber(String ticketNumber) {
        return ticketNumber != null && 
               ticketNumber.matches("\\d{13}") && 
               ticketNumber.length() == 13;
    }
}
