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

import java.time.DayOfWeek;
import java.util.*;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private BenutzerRepository userRepository;

    @Autowired
    private RolleRepository roleRepository;

    @Autowired
    private QuestService questService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Saves a Benutzer entity to the database.
     *
     * @param user the Benutzer entity to save.
     * @return the saved Benutzer entity.
     */
    public Benutzer saveUser(Benutzer user) {
        String rawPassword = user.getPassword();

        // Check if the password is already encoded (BCrypt passwords start with $2a, $2b, or $2y)
        if (!rawPassword.startsWith("$2a$") && !rawPassword.startsWith("$2b$") && !rawPassword.startsWith("$2y$")) {
            String encodedPassword = passwordEncoder.encode(rawPassword);
            System.out.println("Raw Password: " + rawPassword);
            System.out.println("Encoded Password: " + encodedPassword);
            user.setPassword(encodedPassword);
        }

        // Assign default role USER to the new user
        Rolle userRole = roleRepository.findByRolename("ROLE_USER");
        if (userRole == null) {
            userRole = new Rolle("ROLE_USER");
            roleRepository.save(userRole);
        }
        Set<Rolle> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        return userRepository.save(user);
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
        Benutzer user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Could not find the user for username " + username);
        }
        List<GrantedAuthority> grantedAuthorities = getUserAuthorities(user.getRoles());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                user.isEnabled(), true, true, user.isEnabled(), grantedAuthorities);
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

//    public Benutzer assignRandomQuestsToUser(Long userId, int numberOfQuests) {
//        Benutzer user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
//        List<Quest> randomQuests = questService.getRandomQuests(numberOfQuests);
//
//        // Debugging
//        System.out.println("Random Quests: " + randomQuests);
//        if (randomQuests.isEmpty()) {
//            System.out.println("No quests found to assign.");
//        }
//
//        user.getQuests().addAll(randomQuests);
//
//        // Debugging
//        System.out.println("User after adding quests: " + user);
//
//        Benutzer savedUser = userRepository.save(user);
//
//        // Debugging
//        System.out.println("Saved User: " + savedUser);
//
//        return savedUser;
//    }

    @Transactional
    public Benutzer assignRandomQuestsToUser(Long userId, int numberOfQuests) {
        Benutzer user = userRepository.findWithQuestsById(userId); // Use the new method to fetch user with quests
        if (user == null) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        Set<Quest> existingQuests = user.getQuests();

        int currentWeekQuestCount = (int) existingQuests.stream()
                .filter(quest -> quest.getAssignedDate().isAfter(LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))))
                .count();

        int maxQuestsThisWeek = 7;
        int maxQuestsAtStartOfWeek = 4;
        int questsToAssign = Math.min(numberOfQuests, maxQuestsAtStartOfWeek);

        if (currentWeekQuestCount + questsToAssign > maxQuestsThisWeek) {
            questsToAssign = maxQuestsThisWeek - currentWeekQuestCount;
        }

        if (questsToAssign > 0) {
            List<Quest> randomQuests = questService.getRandomQuests(questsToAssign);
            randomQuests.forEach(quest -> quest.setAssignedDate(LocalDateTime.now()));
            user.getQuests().addAll(randomQuests);
            userRepository.save(user);
        }

        return user;
    }
}