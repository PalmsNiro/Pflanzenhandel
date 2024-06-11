package com.example.pflanzenhandel.service;

import com.example.pflanzenhandel.entity.*;
import com.example.pflanzenhandel.repository.BenutzerRepository;
import com.example.pflanzenhandel.repository.RolleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.pflanzenhandel.entity.Benutzer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private BenutzerRepository userRepository;

    @Autowired
    private RolleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Saves a Benutzer entity to the database.
     *
     * @param benutzer the Benutzer entity to save.
     * @return the saved Benutzer entity.
     */
    public Benutzer saveUser(Benutzer benutzer) {
        benutzer.setPassword(passwordEncoder.encode(benutzer.getPassword()));
        // Assign default role USER to the new user
        Rolle userRole = roleRepository.findByRolename("ROLE_USER");
        if (userRole == null) {
            userRole = new Rolle("ROLE_USER");
            roleRepository.save(userRole);
        }
        Set<Rolle> roles = new HashSet<>();
        roles.add(userRole);
        benutzer.setRoles(roles);

        return userRepository.save(benutzer);
    }

    /**
     * Retrieves all Benutzer entities from the database.
     *
     * @return a list of all Benutzer entities.
     */
    public List<Benutzer> findAllUsers() {
        return userRepository.findAll();
    }

    public Benutzer findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Sucht nach einem User mit einem bestimmten Usernamen.
     *
     * @param username der username.
     * @return User-Objekt.
     */
    public Benutzer getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    ///////////////////////////////////////////////////////////////////////////
    // Spring Security Authentication Methoden
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Gibt den aktuell eingeloggten User zurück.
     *
     * @return User.
     */
    public Benutzer getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        return userRepository.findByUsername(username);
    }

    /**
     * Gibt das UserDetails Objekt des aktuell eingeloggten Users zurück. Wird u.U. benötigt um
     * Rollen-Authentifizierungschecks durchzuführen.
     *
     * @return UserDetails Objekt der Spring Security.
     */
    public org.springframework.security.core.userdetails.User getCurrentUserDetails() {
        return (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    /**
     * Überschreibt die Methode, welche für den Login der Spring Security benötigt wird.
     *
     * @param username the username des Benutzers.
     * @return UserDetails Objekt des Spring Security Frameworks.
     * @throws UsernameNotFoundException exception.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Benutzer benutzer = userRepository.findByUsername(username);
        if (Objects.isNull(benutzer)) {
            throw new UsernameNotFoundException("Could not find the user for username " + username);
        }
        List<GrantedAuthority> grantedAuthorities = getUserAuthorities(benutzer.getRoles());
        return new org.springframework.security.core.userdetails.User(benutzer.getUsername(), benutzer.getPassword(),
                benutzer.isEnabled(), true, true, benutzer.isEnabled(), grantedAuthorities);
    }

    /**
     * Converts a set of Rolle entities to a list of GrantedAuthority objects for Spring Security.
     *
     * @param rolleSet the set of Rolle entities.
     * @return a list of GrantedAuthority objects.
     */
    private List<GrantedAuthority> getUserAuthorities(Set<Rolle> rolleSet) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Rolle rolle : rolleSet) {
            grantedAuthorities.add(new SimpleGrantedAuthority(rolle.getRolename()));
        }
        return grantedAuthorities;
    }

    /**
     * Checks if a username is unique.
     *
     * @param username the username to check.
     * @return true if the username is unique, false otherwise.
     */
    public boolean isUsernameUnique(String username) {
        // Perform the uniqueness check using the repository method
        Benutzer existingUser = userRepository.findByUsername(username);
        return existingUser == null;
    }

    public List<Benutzer> getConversations(Long userId) {
        return userRepository.findConversationsByUserId(userId);
    }
}