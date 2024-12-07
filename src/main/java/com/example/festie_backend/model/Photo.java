package com.example.festie_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "photo")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob // Indica que este campo será de tipo BLOB
    @Column(nullable = false)
    private byte[] imageData;

    @Column(nullable = false)
    private Date timestamp;

    public Photo() {}

    public Photo(Event event, User user, byte[] imageData, Date timestamp) {
        this.event = event;
        this.user = user;
        this.imageData = imageData;
        this.timestamp = timestamp;
    }
}
