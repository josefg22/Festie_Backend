package com.example.festie_backend.service;

import com.example.festie_backend.model.Notification;
import com.example.festie_backend.repository.NotificationRepository;
import org.springframework.stereotype.*;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Guardar una nueva notificación
    public Notification saveNotification(String senderId, String receiverId, String message) {
        Notification notification = new Notification();
        notification.setSenderId(senderId);
        notification.setReceiverId(receiverId);
        notification.setMessage(message);
        notification.setIsRead(false);
        notification.setTimestamp(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

    // Obtener todas las notificaciones de un usuario
    public List<Notification> getNotificationsForUser(String receiverId) {
        return notificationRepository.findByReceiverIdOrderByTimestampDesc(receiverId);
    }

    // Marcar una notificación como leída
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
        notification.setIsRead(true);
        notificationRepository.save(notification);
    }
}