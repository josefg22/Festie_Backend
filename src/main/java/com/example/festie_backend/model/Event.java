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
    private Date date;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String image;
    private String latitude;
    private String longitude;
    private String name;
    private double price;
    private String url;

    public Event() {
    }

    public Event(Long id, String name, String latitude, String longitude, Date date,
                 String url, double price, String image) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.url = url;
        this.price = price;
        this.image = image;
    }
}
