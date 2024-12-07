package com.example.festie_backend.service;

import com.example.festie_backend.model.Event;
import com.example.festie_backend.model.Photo;
import com.example.festie_backend.model.User;
import com.example.festie_backend.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
public class PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserService userService;

    public Photo uploadPhoto(Long eventId, Long userId, MultipartFile file) throws IOException {

        Event event = eventService.getEventById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado con ID: " + eventId));

        User user = userService.getUserById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + userId));


        byte[] imageData = file.getBytes();
        //prueba
        Photo photo = new Photo();
        photo.setEvent(event);
        photo.setUser(user);
        photo.setImageData(imageData);
        photo.setTimestamp(new Date());

        return photoRepository.save(photo);
    }

    public Photo getPhotoById(Long photoId) {
        return photoRepository.findById(photoId)
                .orElseThrow(() -> new IllegalArgumentException("Foto no encontrada con ID: " + photoId));
    }
}
