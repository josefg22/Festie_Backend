package com.example.festie_backend.controller;

import com.example.festie_backend.model.UserFriend;
import com.example.festie_backend.service.UserFriendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-friend")
public class UserFriendController {
    private final UserFriendService userFriendService;

    public UserFriendController(UserFriendService userFriendService){
        this.userFriendService = userFriendService;
    }

    @GetMapping
    public ResponseEntity<List<UserFriend>> getAllUserFriend(){
        return ResponseEntity.ok(userFriendService.getAllUserFriends());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserFriend> getUserFriendById(@PathVariable Long id){
        return userFriendService.getUserFriendById(id)
                .map(ResponseEntity::ok)
                .orElse((ResponseEntity.notFound().build()));
    }

    @PostMapping
    public ResponseEntity<UserFriend> createUserFriend(@RequestBody UserFriend userFriend) {
        return ResponseEntity.ok(userFriendService.saveUserFriend(userFriend));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserFriend(@PathVariable Long id){
        userFriendService.deleteUserFriendById(id);
        return ResponseEntity.noContent().build();
    }
}
