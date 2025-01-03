package com.example.festie_backend.controller;

import com.example.festie_backend.dto.TicketPurchaseRequest;
import com.example.festie_backend.model.Ticket;
import com.example.festie_backend.service.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyTicket(@RequestBody TicketPurchaseRequest request) {
        boolean isBought = ticketService.hasTicket(request.getUserId(), request.getEventId());

        if (isBought) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("You already own this ticket.");
        }

        ticketService.buyTicket(request.getUserId(), request.getEventId());
        return ResponseEntity.ok("Ticket purchased successfully.");
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getUserTickets(@RequestParam Long userId) {
        List<Ticket> tickets = ticketService.getTicketsByUserId(userId);
        return ResponseEntity.ok(tickets);
    }
}


