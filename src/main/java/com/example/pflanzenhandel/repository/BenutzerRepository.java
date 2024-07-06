package com.example.pflanzenhandel.repository;

import com.example.pflanzenhandel.entity.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;

/**
 * Repository interface for accessing Benutzer entities from the database.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
public interface BenutzerRepository extends JpaRepository<Benutzer, Long>{

    /**
     * Finds a Benutzer entity by its username.
     *
     * @param username the username of the Benutzer to find.
     * @return the Benutzer entity with the given username, or null if none found.
     */
    Benutzer findByUsername(String username);
    @Query("SELECT DISTINCT m.sender FROM Message m WHERE m.recipient.id = :userId " +
            "UNION " +
            "SELECT DISTINCT m.recipient FROM Message m WHERE m.sender.id = :userId")
    List<Benutzer> findConversationsByUserId(@Param("userId") Long userId);

    @EntityGraph(attributePaths = {"quests"})
    Benutzer findWithQuestsById(Long id);
}
