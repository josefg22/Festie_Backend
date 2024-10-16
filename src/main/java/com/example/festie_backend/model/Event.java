package com.example.festie_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;

    @Column(name = "event_date") // Nombre de la columna en la base de datos
    private LocalDateTime date; // Fecha y hora del evento
    private String url;

    private double price;
    private String image;

    // Constructor por defecto
    public Event() {
    }

    // Constructor con parámetros
    public Event(Long id, String name, String location, LocalDateTime date,
                 String url, double price, String image) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.url = url;
        this.price = price;
        this.image = image;
    }
}
