package com.example.festie_backend.service;

import com.example.festie_backend.dto.FriendsDTO;
import com.example.festie_backend.model.User;
import com.example.festie_backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
    public boolean areFriends(Long userId, Long friendId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> friendOpt = userRepository.findById(friendId);
        if (userOpt.isEmpty() || friendOpt.isEmpty()) {
            return false;
        }

        User user = userOpt.get();
        User friend = friendOpt.get();

        return user.getFriends().contains(friend);
    }
    public boolean addFriend(Long userId, Long friendId) {
        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> friendOpt = userRepository.findById(friendId);

        if (userOpt.isPresent() && friendOpt.isPresent()) {
            User user = userOpt.get();
            User friend = friendOpt.get();

            if (!user.getFriends().contains(friend)) {
                user.getFriends().add(friend);
            }

            if (!friend.getFriends().contains(user)) {
                friend.getFriends().add(user);
            }

            userRepository.save(user);
            userRepository.save(friend);
            return true;
        }
        return false;
    }
    public boolean removeFriend(Long userId, Long friendId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<User> friend = userRepository.findById(friendId);

        if (user.isPresent() && friend.isPresent()) {
            user.get().removeFriend(friend.get());
            userRepository.save(user.get());
            userRepository.save(friend.get());
            return true;
        }
        return false;
    }
    public List<FriendsDTO> getFriendsOfUser(Long id) {
        return userRepository.findById(id)
                .map(User::getAllFriends)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .stream()
                .map(friend -> new FriendsDTO(friend.getId(), friend.getName(), friend.getNick()))
                .collect(Collectors.toList());
    }
}
