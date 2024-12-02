package com.example.festie_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Date birthday;
    private List<FriendsDTO> friends;

    public UserDTO() {
    }

    public UserDTO(Long id, String name, String email, Date birthday,List<FriendsDTO> friends) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.friends = friends;
    }
    public void setFriends(List<FriendsDTO> friends) {
        this.friends = friends;
    }
}
