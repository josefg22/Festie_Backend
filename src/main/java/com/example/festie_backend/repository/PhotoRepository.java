package com.example.festie_backend.repository;

import com.example.festie_backend.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    // Puedes agregar métodos personalizados si es necesario
}
