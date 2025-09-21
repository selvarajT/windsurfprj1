package com.airline.ticketing.controller;

import com.airline.ticketing.dto.BookingRequest;
import com.airline.ticketing.dto.BookingResponse;
import com.airline.ticketing.model.*;
import com.airline.ticketing.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tickets")
@CrossOrigin(origins = "*")
public class TicketController {
    
    @Autowired
    private TicketService ticketService;
    
    /**
     * Book a new ticket
     */
    @PostMapping("/book")
    public ResponseEntity<BookingResponse> bookTicket(@Valid @RequestBody BookingRequest request) {
        try {
            // Create passenger
            Passenger passenger = new Passenger(request.getPassengerName(), request.getPassengerAge());
            
            // Create itinerary
            Itinerary itinerary = new Itinerary(
                request.getOrigin(),
                request.getDestination(),
                request.getDepartureTime(),
                request.getArrivalTime(),
                request.getFlightNumber()
            );
            
            // Create fare details
            FareDetails fareDetails = new FareDetails(
                request.getBaseFare(),
                request.getTaxAmount(),
                request.getCurrency()
            );
            
            // Book ticket
            Ticket ticket = ticketService.bookTicket(request.getPnr(), passenger, itinerary, fareDetails);
            
            return ResponseEntity.ok(new BookingResponse(ticket));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get ticket by ticket number
     */
    @GetMapping("/{ticketNumber}")
    public ResponseEntity<BookingResponse> getTicket(@PathVariable String ticketNumber) {
        Optional<Ticket> ticket = ticketService.findByTicketNumber(ticketNumber);
        if (ticket.isPresent()) {
            return ResponseEntity.ok(new BookingResponse(ticket.get()));
        }
        return ResponseEntity.notFound().build();
    }
    
    /**
     * Get tickets by PNR
     */
    @GetMapping("/pnr/{pnr}")
    public ResponseEntity<List<BookingResponse>> getTicketsByPnr(@PathVariable String pnr) {
        List<Ticket> tickets = ticketService.findByPnr(pnr);
        List<BookingResponse> responses = tickets.stream()
            .map(BookingResponse::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Search tickets by passenger name
     */
    @GetMapping("/search")
    public ResponseEntity<List<BookingResponse>> searchTickets(@RequestParam String passengerName) {
        List<Ticket> tickets = ticketService.findByPassengerName(passengerName);
        List<BookingResponse> responses = tickets.stream()
            .map(BookingResponse::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Update ticket status
     */
    @PutMapping("/{ticketNumber}/status")
    public ResponseEntity<BookingResponse> updateTicketStatus(
            @PathVariable String ticketNumber,
            @RequestParam String status) {
        try {
            Ticket.TicketStatus ticketStatus = Ticket.TicketStatus.valueOf(status.toUpperCase());
            Ticket updatedTicket = ticketService.updateTicketStatus(ticketNumber, ticketStatus);
            return ResponseEntity.ok(new BookingResponse(updatedTicket));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Cancel ticket
     */
    @PutMapping("/{ticketNumber}/cancel")
    public ResponseEntity<BookingResponse> cancelTicket(@PathVariable String ticketNumber) {
        try {
            Ticket cancelledTicket = ticketService.cancelTicket(ticketNumber);
            return ResponseEntity.ok(new BookingResponse(cancelledTicket));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get all tickets
     */
    @GetMapping("/all")
    public ResponseEntity<List<BookingResponse>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        List<BookingResponse> responses = tickets.stream()
            .map(BookingResponse::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
