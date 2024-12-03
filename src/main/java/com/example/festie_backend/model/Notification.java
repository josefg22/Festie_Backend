package com.example.festie_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String senderId;   // ID del usuario que envía
    private String receiverId; // ID del usuario que recibe
    private String message;    // Contenido del evento
    private boolean isRead;    // Para indicar si la notificación fue leída

    private LocalDateTime timestamp; // Fecha y hora del envío

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}