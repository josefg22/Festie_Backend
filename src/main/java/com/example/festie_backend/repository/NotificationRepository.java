package com.example.festie_backend.repository;

import com.example.festie_backend.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Recuperar todas las notificaciones de un usuario
    List<Notification> findByReceiverIdOrderByTimestampDesc(String receiverId);
}
