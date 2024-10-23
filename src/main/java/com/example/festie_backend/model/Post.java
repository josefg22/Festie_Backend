package com.example.festie_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "\"post\"")
public class Post {
    @ManyToOne
    @JoinColumn(name = "event_id",
            referencedColumnName = "id")
    private Event event;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;
    private String media_url;

    @ManyToOne
    @JoinColumn(name = "user_id",
    referencedColumnName = "id")
    private User user;
}
