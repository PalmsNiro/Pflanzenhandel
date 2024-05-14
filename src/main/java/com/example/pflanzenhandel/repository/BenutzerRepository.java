package com.example.pflanzenhandel.repository;

import com.example.pflanzenhandel.entity.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BenutzerRepository extends JpaRepository<Benutzer, Integer>{

    Benutzer findByusername( String username);
}
