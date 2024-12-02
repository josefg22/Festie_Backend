package com.example.festie_backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "\"user\"")
public class User {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;
    private String email;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends;
    @ManyToMany(mappedBy = "friends")
    private List<User> inverseFriends = new ArrayList<>();

    // Método para obtener amigos bidireccionales
    public List<User> getAllFriends() {
        Set<User> allFriends = new HashSet<>(friends);
        allFriends.addAll(inverseFriends);
        return new ArrayList<>(allFriends);
    }





    public void addFriend(User friend) {
        this.friends.add(friend);
        friend.getFriends().add(this);
    }
    public void removeFriend(User friend) {
        this.friends.remove(friend);
        friend.getFriends().remove(this);
    }
    public List<User> getFriends() {
        return friends.stream().toList();
    }
}
