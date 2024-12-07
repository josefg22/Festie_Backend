package com.example.festie_backend.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendsDTO {
    private Long id;
    private String name;
    private String nick;

    // Constructores, getters y setters

    public FriendsDTO() {}

    public FriendsDTO(Long id, String name, String nick) {
        this.id = id;
        this.name = name;
        this.nick = nick;
    }


}

