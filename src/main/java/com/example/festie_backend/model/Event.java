package com.example.festie_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "\"event\"")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    private Date date;
    private String url;

    private double price;
    private String image;

    public Event() {
    }

    public Event(Long id, String name, String location, Date date,
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
