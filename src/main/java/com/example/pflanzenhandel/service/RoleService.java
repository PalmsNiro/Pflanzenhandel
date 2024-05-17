package com.example.pflanzenhandel.service;

import com.example.pflanzenhandel.entity.Rolle;
import com.example.pflanzenhandel.repository.RolleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing roles in the application.
 */
@Service
public class RoleService {

    @Autowired
    private RolleRepository roleRepository;

    /**
     * Saves a Rolle entity to the database.
     *
     * @param rolle the Rolle entity to save.
     * @return the saved Rolle entity.
     */
    public Rolle saveRole(Rolle rolle) {
        return roleRepository.save(rolle);
    }

    /**
     * Retrieves all Rolle entities from the database.
     *
     * @return a list of all Rolle entities.
     */
    public List<Rolle> findAllUsers() {
        return roleRepository.findAll();
    }
}
