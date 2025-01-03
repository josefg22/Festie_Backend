package com.example.festie_backend.repository;

import com.example.festie_backend.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    boolean existsByUserIdAndEventId(Long userId, Long eventId);

    List<Ticket> findAllByUserId(Long userId);
}


