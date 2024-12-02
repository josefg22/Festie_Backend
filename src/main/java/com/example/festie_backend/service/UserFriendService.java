package com.example.festie_backend.service;

import com.example.festie_backend.model.UserFriend;
import com.example.festie_backend.repository.UserFriendRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserFriendService {
    private final UserFriendRepository userFriendRepository;

    public UserFriendService(UserFriendRepository userFriendRepository){
        this.userFriendRepository = userFriendRepository;
    }

    public List<UserFriend> getAllUserFriends () {
        return userFriendRepository.findAll();
    }

    public Optional<UserFriend> getUserFriendById(Long id){
        return userFriendRepository.findById(id);
    }

    public UserFriend saveUserFriend (UserFriend userFriend) {
        return userFriendRepository.save(userFriend);
    }

    public void deleteUserFriendById(Long id){
        userFriendRepository.deleteById(id);
    }

    public boolean areFriends(Long userId, Long friendId) {
        return userFriendRepository.existsByUserIdAndFriendId(userId, friendId) ||
                userFriendRepository.existsByUserIdAndFriendId(friendId, userId);
    }

}
