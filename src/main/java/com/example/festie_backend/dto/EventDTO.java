package com.example.festie_backend.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class EventDTO {
    private Date date;
    private String description;
    private String image;
    private String location;
    private String name;
    private double price;
}
