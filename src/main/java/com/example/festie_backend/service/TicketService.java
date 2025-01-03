package com.example.festie_backend.service;

import com.example.festie_backend.model.Ticket;
import com.example.festie_backend.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public boolean hasTicket(Long userId, Long eventId) {
        return ticketRepository.existsByUserIdAndEventId(userId, eventId);
    }

    public void buyTicket(Long userId, Long eventId) {
        Ticket ticket = new Ticket();
        ticket.setUserId(userId);
        ticket.setEventId(eventId);
        ticket.setPrice(0.0);
        ticket.setPurchaseDate(LocalDateTime.now());
        ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByUserId(Long userId) {
        return ticketRepository.findAllByUserId(userId);
    }
}


