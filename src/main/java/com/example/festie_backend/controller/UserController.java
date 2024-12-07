package com.example.festie_backend.controller;

import com.example.festie_backend.dto.FriendsDTO;
import com.example.festie_backend.dto.UserDTO;
import com.example.festie_backend.model.User;
import com.example.festie_backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    // Método para convertir de User a UserDTO
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        // Mapear los amigos del usuario
        List<FriendsDTO> friends = user.getFriends().stream()
                .map(friend -> new FriendsDTO(friend.getId(), friend.getName(), friend.getNick()))
                .collect(Collectors.toList());
        userDTO.setFriends(friends);

        return userDTO;
    }


    // Método para convertir de UserDTO a User
    private User convertToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOList = userService.getAllUsers().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDTOList);
    }

    // Obtener un usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(this::convertToDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(convertToDTO(savedUser));
    }

    // Eliminar un usuario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    // Añadir un amigo
    @PostMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        if (userService.addFriend(userId, friendId)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    // Eliminar un amigo
    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<Void> removeFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        if (userService.removeFriend(userId, friendId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.badRequest().build();
    }

    // Obtener los amigos de un usuario
    @GetMapping("/{id}/friends")
    public ResponseEntity<UserDTO> getFriendsOfUser(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(user -> {
                    UserDTO userDTO = convertToDTO(user);

                    // Obtener todos los amigos bidireccionales
                    List<FriendsDTO> friends = user.getAllFriends().stream()
                            .map(friend -> new FriendsDTO(friend.getId(), friend.getName(), friend.getNick()))
                            .collect(Collectors.toList());

                    // Asignar amigos al DTO
                    userDTO.setFriends(friends);

                    return ResponseEntity.ok(userDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
