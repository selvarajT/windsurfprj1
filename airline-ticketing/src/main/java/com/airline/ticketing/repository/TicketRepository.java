package com.airline.ticketing.repository;

import com.airline.ticketing.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    
    Optional<Ticket> findByTicketNumber(String ticketNumber);
    
    boolean existsByTicketNumber(String ticketNumber);
    
    List<Ticket> findByPnr(String pnr);
    
    List<Ticket> findByPassengerNameContainingIgnoreCase(String passengerName);
    
    List<Ticket> findByStatus(Ticket.TicketStatus status);
}
