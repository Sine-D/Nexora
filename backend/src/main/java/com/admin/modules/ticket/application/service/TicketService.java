package com.admin.modules.ticket.application.service;

import com.admin.core.exception.ResourceNotFoundException;
import com.admin.modules.ticket.domain.Ticket;
import com.admin.modules.ticket.infrastructure.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {
    
    private final TicketRepository ticketRepository;
    
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
    
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));
    }
    
    @Transactional
    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
    
    @Transactional
    public Ticket updateTicket(Long id, Ticket ticketDetails) {
        Ticket ticket = getTicketById(id);
        
        ticket.setTitle(ticketDetails.getTitle());
        ticket.setDescription(ticketDetails.getDescription());
        ticket.setStatus(ticketDetails.getStatus());
        ticket.setPriority(ticketDetails.getPriority());
        
        return ticketRepository.save(ticket);
    }
    
    @Transactional
    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket not found");
        }
        ticketRepository.deleteById(id);
    }
}
