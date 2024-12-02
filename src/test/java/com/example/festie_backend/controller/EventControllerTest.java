package com.example.festie_backend.controller;

import com.example.festie_backend.model.Event;
import com.example.festie_backend.service.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.mockito.Mockito.*;

class EventControllerTest {
    @Mock
    EventService eventService;
    @InjectMocks
    EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEvents() {
        when(eventService.getAllEvents()).thenReturn(List.of(new Event(Long.valueOf(1), "name", "latitude", "longitude", new GregorianCalendar(2024, Calendar.NOVEMBER, 30, 14, 27).getTime(), "url", 0d, "image")));

        ResponseEntity<List<Event>> result = eventController.getAllEvents();
        Assertions.assertEquals(new ResponseEntity<List<Event>>(List.of(new Event(Long.valueOf(1), "name", "latitude", "longitude", new GregorianCalendar(2024, Calendar.NOVEMBER, 30, 14, 27).getTime(), "url", 0d, "image")), null, 200), result);
    }

    @Test
    void testGetEventById() {
        when(eventService.getEventById(anyLong())).thenReturn(null);

        ResponseEntity<Event> result = eventController.getEventById(Long.valueOf(1));
        Assertions.assertEquals(new ResponseEntity<Event>(new Event(Long.valueOf(1), "name", "latitude", "longitude", new GregorianCalendar(2024, Calendar.NOVEMBER, 30, 14, 27).getTime(), "url", 0d, "image"), null, 200), result);
    }

    @Test
    void testGetUpcomingEvents() {
        when(eventService.getUpcomingEvents()).thenReturn(List.of(new Event(Long.valueOf(1), "name", "latitude", "longitude", new GregorianCalendar(2024, Calendar.NOVEMBER, 30, 14, 27).getTime(), "url", 0d, "image")));

        ResponseEntity<List<Event>> result = eventController.getUpcomingEvents();
        Assertions.assertEquals(new ResponseEntity<List<Event>>(List.of(new Event(Long.valueOf(1), "name", "latitude", "longitude", new GregorianCalendar(2024, Calendar.NOVEMBER, 30, 14, 27).getTime(), "url", 0d, "image")), null, 200), result);
    }

    @Test
    void testCreateEvent() {
        when(eventService.saveEvent(any(Event.class))).thenReturn(new Event(Long.valueOf(1), "name", "latitude", "longitude", new GregorianCalendar(2024, Calendar.NOVEMBER, 30, 14, 27).getTime(), "url", 0d, "image"));

        ResponseEntity<Event> result = eventController.createEvent(new Event(Long.valueOf(1), "name", "latitude", "longitude", new GregorianCalendar(2024, Calendar.NOVEMBER, 30, 14, 27).getTime(), "url", 0d, "image"));
        Assertions.assertEquals(new ResponseEntity<Event>(new Event(Long.valueOf(1), "name", "latitude", "longitude", new GregorianCalendar(2024, Calendar.NOVEMBER, 30, 14, 27).getTime(), "url", 0d, "image"), null, 200), result);
    }

    @Test
    void testUpdateEvent() {
        when(eventService.updateEvent(anyLong(), any(Event.class))).thenReturn(null);

        ResponseEntity<Event> result = eventController.updateEvent(Long.valueOf(1), new Event(Long.valueOf(1), "name", "latitude", "longitude", new GregorianCalendar(2024, Calendar.NOVEMBER, 30, 14, 27).getTime(), "url", 0d, "image"));
        Assertions.assertEquals(new ResponseEntity<Event>(new Event(Long.valueOf(1), "name", "latitude", "longitude", new GregorianCalendar(2024, Calendar.NOVEMBER, 30, 14, 27).getTime(), "url", 0d, "image"), null, 200), result);
    }

    @Test
    void testDeleteEventById() {
        when(eventService.deleteEventById(anyLong())).thenReturn(true);

        ResponseEntity<Void> result = eventController.deleteEventById(Long.valueOf(1));
        Assertions.assertEquals(new ResponseEntity<Void>(null, null, 204), result);
    }
}

//Generated with love by TestMe :) Please raise issues & feature requests at: https://weirddev.com/forum#!/testme