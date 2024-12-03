package com.example.festie_backend.controller;

import com.example.festie_backend.model.Notification;
import com.example.festie_backend.service.NotificationService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class NotificationWebSocketController {

    private final NotificationService notificationService;

    public NotificationWebSocketController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Método WebSocket para recibir eventos y enviarlos a los suscritos
    @MessageMapping("/send-event") // Endpoint para recibir eventos
    @SendTo("/topic/notifications") // Broadcast a todos los suscritos
    public Notification sendEvent(Notification notification) {
        notification.setTimestamp(LocalDateTime.now());
        // Guardar la notificación en la base de datos si es necesario
        notificationService.saveNotification(notification.getSenderId(), notification.getReceiverId(), notification.getMessage());
        return notification;
    }
}
