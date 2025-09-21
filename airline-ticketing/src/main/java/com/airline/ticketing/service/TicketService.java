package com.airline.ticketing.service;

import com.airline.ticketing.model.*;
import com.airline.ticketing.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TicketService {
    
    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private TicketNumberGenerator ticketNumberGenerator;
    
    /**
     * Books a new ticket with generated ticket number
     */
    public Ticket bookTicket(String pnr, Passenger passenger, 
                           Itinerary itinerary, FareDetails fareDetails) {
        
        // Generate unique ticket number
        String ticketNumber;
        do {
            ticketNumber = ticketNumberGenerator.generateTicketNumber(pnr, passenger, itinerary, fareDetails);
        } while (ticketRepository.existsByTicketNumber(ticketNumber));
        
        // Create and save ticket
        Ticket ticket = new Ticket(ticketNumber, pnr, passenger, itinerary, fareDetails);
        return ticketRepository.save(ticket);
    }
    
    /**
     * Finds ticket by ticket number
     */
    public Optional<Ticket> findByTicketNumber(String ticketNumber) {
        return ticketRepository.findByTicketNumber(ticketNumber);
    }
    
    /**
     * Finds tickets by PNR
     */
    public List<Ticket> findByPnr(String pnr) {
        return ticketRepository.findByPnr(pnr);
    }
    
    /**
     * Finds tickets by passenger name
     */
    public List<Ticket> findByPassengerName(String passengerName) {
        return ticketRepository.findByPassengerNameContainingIgnoreCase(passengerName);
    }
    
    /**
     * Updates ticket status
     */
    public Ticket updateTicketStatus(String ticketNumber, Ticket.TicketStatus status) {
        Optional<Ticket> ticketOpt = ticketRepository.findByTicketNumber(ticketNumber);
        if (ticketOpt.isPresent()) {
            Ticket ticket = ticketOpt.get();
            ticket.setStatus(status);
            return ticketRepository.save(ticket);
        }
        throw new RuntimeException("Ticket not found with number: " + ticketNumber);
    }
    
    /**
     * Cancels a ticket
     */
    public Ticket cancelTicket(String ticketNumber) {
        return updateTicketStatus(ticketNumber, Ticket.TicketStatus.CANCELLED);
    }
    
    /**
     * Gets all tickets
     */
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
