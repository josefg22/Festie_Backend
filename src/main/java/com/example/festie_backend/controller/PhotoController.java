package com.example.festie_backend.controller;

import com.example.festie_backend.model.Photo;
import com.example.festie_backend.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/events/{eventId}/photos")
public class PhotoController {

    @Autowired
    private PhotoService photoService;

    @PostMapping
    public ResponseEntity<Photo> uploadPhoto(
            @PathVariable Long eventId,
            @RequestParam("userId") Long userId,
            @RequestParam("file") MultipartFile file) {
        try {
            Photo photo = photoService.uploadPhoto(eventId, userId, file);
            return ResponseEntity.ok(photo);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<byte[]> getPhotoById(@PathVariable Long photoId) {
        Photo photo = photoService.getPhotoById(photoId);

        if (photo == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg") // Ajusta el tipo MIME según el formato de tus imágenes
                .body(photo.getImageData());
    }
}
