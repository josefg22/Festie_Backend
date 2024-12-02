package com.example.festie_backend.service;

import com.example.festie_backend.model.Event;

import com.example.festie_backend.repository.EventRepository;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;

import org.mockito.Mock;

import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock

    EventRepository eventRepository;

    @InjectMocks

    EventService eventService;

    @BeforeEach

    void setUp() {

        MockitoAnnotations.openMocks(this);

    }

    @Test

    void testGetAllEvents() {

        // Mock setup

        List<Event> mockEvents = List.of(new Event(1L, "Event1", "latitude", "longitude", new Date(), "url", 0d, "image"));

        when(eventRepository.findAll()).thenReturn(mockEvents);

        // Execute

        List<Event> result = eventService.getAllEvents();

        // Verify

        Assertions.assertEquals(mockEvents, result);

        verify(eventRepository).findAll();

    }

    @Test

    void testGetEventById() {

        // Mock setup

        Long eventId = 1L;

        Optional<Event> mockEvent = Optional.of(new Event(1L, "Event1", "latitude", "longitude", new Date(), "url", 0d, "image"));

        when(eventRepository.findById(eventId)).thenReturn(mockEvent);

        // Execute

        Optional<Event> result = eventService.getEventById(eventId);

        // Verify

        Assertions.assertEquals(mockEvent, result);

        verify(eventRepository).findById(eventId);

    }

    @Test

    void testSaveEvent() {

        // Mock setup

        Event eventToSave = new Event(null, "Event1", "latitude", "longitude", new Date(), "url", 0d, "image");

        Event savedEvent = new Event(1L, "Event1", "latitude", "longitude", new Date(), "url", 0d, "image");

        when(eventRepository.save(eventToSave)).thenReturn(savedEvent);

        // Execute

        Event result = eventService.saveEvent(eventToSave);

        // Verify

        Assertions.assertEquals(savedEvent, result);

        verify(eventRepository).save(eventToSave);

    }

    @Test

    void testUpdateEvent() {

        // Mock setup

        Long eventId = 1L;

        Event eventDetails = new Event(eventId, "UpdatedEvent", "latitude", "longitude", new Date(), "url", 0d, "image");

        when(eventRepository.existsById(eventId)).thenReturn(true);

        when(eventRepository.save(eventDetails)).thenReturn(eventDetails);

        // Execute

        Optional<Event> result = eventService.updateEvent(eventId, eventDetails);

        // Verify

        Assertions.assertTrue(result.isPresent());

        Assertions.assertEquals(eventDetails, result.get());

        verify(eventRepository).existsById(eventId);

        verify(eventRepository).save(eventDetails);

    }

    @Test

    void testDeleteEventById() {

        // Mock setup

        Long eventId = 1L;

        when(eventRepository.existsById(eventId)).thenReturn(true);

        // Execute

        boolean result = eventService.deleteEventById(eventId);

        // Verify

        Assertions.assertTrue(result);

        verify(eventRepository).existsById(eventId);

        verify(eventRepository).deleteById(eventId);

    }

    @Test

    void testGetUpcomingEvents() {

        // Mock setup

        Date today = new Date();

        List<Event> mockEvents = List.of(new Event(1L, "FutureEvent", "latitude", "longitude", new Date(today.getTime() + 86400000), "url", 0d, "image")); // 1 día en el futuro

        when(eventRepository.findByDateAfterOrderByDateAsc(today)).thenReturn(mockEvents);

        // Execute

        List<Event> result = eventService.getUpcomingEvents();

        // Verify

        Assertions.assertEquals(mockEvents, result);

        verify(eventRepository).findByDateAfterOrderByDateAsc(today);

    }

}

