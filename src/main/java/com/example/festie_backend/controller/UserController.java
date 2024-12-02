package com.example.festie_backend.controller;

import com.example.festie_backend.model.User;
import com.example.festie_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        if (userService.addFriend(userId, friendId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        if (userService.removeFriend(userId, friendId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
