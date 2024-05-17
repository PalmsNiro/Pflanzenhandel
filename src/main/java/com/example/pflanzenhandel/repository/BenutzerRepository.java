package com.example.pflanzenhandel.repository;

import com.example.pflanzenhandel.entity.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing Benutzer entities from the database.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface BenutzerRepository extends JpaRepository<Benutzer, Integer>{

    /**
     * Finds a Benutzer entity by its username.
     *
     * @param username the username of the Benutzer to find.
     * @return the Benutzer entity with the given username, or null if none found.
     */
    Benutzer findByUsername(String username);
    }
