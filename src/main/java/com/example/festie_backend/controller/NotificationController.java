package com.example.festie_backend.controller;

import com.example.festie_backend.model.Notification;
import com.example.festie_backend.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/notifications")  // Ruta base para las notificaciones en REST
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Crear una nueva notificación (REST)
    @PostMapping
    public ResponseEntity<Notification> createNotification(
            @RequestParam String senderId,
            @RequestParam String receiverId,
            @RequestParam String message) {
        Notification savedNotification = notificationService.saveNotification(senderId, receiverId, message);
        return ResponseEntity.ok(savedNotification);
    }

    // Obtener notificaciones de un usuario (REST)
    @GetMapping("/{receiverId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable String receiverId) {
        List<Notification> notifications = notificationService.getNotificationsForUser(receiverId);
        return ResponseEntity.ok(notifications);
    }

    // Marcar una notificación como leída (REST)
    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markNotificationAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }
}

