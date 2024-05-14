package com.example.pflanzenhandel.service;

import com.example.pflanzenhandel.entity.Rolle;
import com.example.pflanzenhandel.repository.RolleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolleService {

    @Autowired
    private RolleRepository roleRepository;

    public Rolle saveRole(Rolle role) {
        return roleRepository.save(role);
    }

    public List<Rolle> findAllUsers() {
        return roleRepository.findAll();
    }
}
