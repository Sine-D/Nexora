package com.admin.modules.ticket;

import com.admin.core.exception.ResourceNotFoundException;
import com.admin.modules.ticket.application.service.TicketService;
import com.admin.modules.ticket.domain.Ticket;
import com.admin.modules.ticket.infrastructure.TicketRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    @DisplayName("Should create and save a new ticket")
    void testCreateTicket() {
        Ticket ticket = Ticket.builder().title("UI Bug").description("Fix alignment").priority("HIGH").status("OPEN").build();
        when(ticketRepository.save(ticket)).thenAnswer(i -> {
            Ticket t = i.getArgument(0);
            t.setId(100L);
            return t;
        });

        Ticket created = ticketService.createTicket(ticket);

        assertNotNull(created);
        assertEquals(100L, created.getId());
        assertEquals("UI Bug", created.getTitle());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when getting non-existent ticket")
    void testGetTicketNotFound() {
        when(ticketRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ticketService.getTicketById(999L));
    }
}
