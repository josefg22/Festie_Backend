package com.example.festie_backend.repository;

import com.example.festie_backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //Agregar consultas personalizadas si es necesario
}
