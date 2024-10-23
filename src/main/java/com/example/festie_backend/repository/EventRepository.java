package com.example.festie_backend.repository;

import com.example.festie_backend.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    // consultas personalizadas si es necesario
    List<Event> findByDateAfterOrderByDateAsc(Date today);
}
