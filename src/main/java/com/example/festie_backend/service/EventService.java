package com.example.festie_backend.service;

import com.example.festie_backend.dto.EventDTO;
import com.example.festie_backend.model.Event;
import com.example.festie_backend.model.User;
import com.example.festie_backend.repository.EventRepository;
import com.example.festie_backend.repository.UserRepository;
import com.example.festie_backend.service.geocoding.GeoCodingService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    @Autowired
    private GeoCodingService geoCodingService;


    public EventService(EventRepository eventRepository, UserRepository userRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.userService = userService;
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
    public Event saveEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setLocation(eventDTO.getLocation());
        event.setDate(eventDTO.getDate());
        event.setPrice(eventDTO.getPrice());
        event.setDescription(eventDTO.getDescription());
        event.setImage(eventDTO.getImage());

        String[] coordinates = geoCodingService.getCoordinates(eventDTO.getLocation());
        event.setLatitude(coordinates[0]);
        event.setLongitude(coordinates[1]);

        return eventRepository.save(event);
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

    public List<Event> getUpcomingEvents() {

        Date today = new Date();

        return eventRepository.findByDateAfterOrderByDateAsc(today);
    }

    public boolean sendEventToFriend(Long eventId, Long senderId, Long receiverId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            return false;
        }
        if (!userService.areFriends(senderId, receiverId)) {
            return false;
        }
        return true;
    }
}