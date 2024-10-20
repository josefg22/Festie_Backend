package com.example.festie_backend.service;

import com.example.festie_backend.model.Event;
import com.example.festie_backend.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    //Constructor
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // Obtener todos los eventos
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // Obtener un evento por ID
    public Optional<Event> getEventById(Long id) {
        return eventRepository.findById(id);
    }

    // Guardar un nuevo evento
    public Event saveEvent(Event event) {
        return eventRepository.save(event); // La base de datos asignará el ID automáticamente
    }

    // Actualizar un evento existente
    public Optional<Event> updateEvent(Long id, Event eventDetails) {
        if (eventRepository.existsById(id)) {
            eventDetails.setId(id); // Asegúrate de que se mantiene el mismo ID
            return Optional.of(eventRepository.save(eventDetails));
        }
        return Optional.empty();
    }

    // Eliminar un evento por ID
    public boolean deleteEventById(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
