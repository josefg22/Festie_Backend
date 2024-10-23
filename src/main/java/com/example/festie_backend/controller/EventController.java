package com.example.festie_backend.controller;

import com.example.festie_backend.model.Event;
import com.example.festie_backend.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<Event>>  getAllEvents (){
        List<Event> events = eventService.getAllEvents();
        if(events.isEmpty()){
            return ResponseEntity.noContent().build(); // 204 No Content si no hay eventos
        }
        return ResponseEntity.ok(events); // 200 OK con lista de eventos
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById (@PathVariable Long id) {
        return eventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build()); //404
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<Event>> getUpcomingEvents() {
        List<Event> upcomingEvents = eventService.getUpcomingEvents();
        return ResponseEntity.ok(upcomingEvents);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event){
        Event createdEvent = eventService.saveEvent(event);
        return ResponseEntity.ok(createdEvent); //200 OK con el evento creado
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event eventDetails){
        return eventService.updateEvent(id, eventDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventById(@PathVariable Long id){
        if(eventService.deleteEventById(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content si se elimina correctamente
        }
        return  ResponseEntity.notFound().build();
    }
}
