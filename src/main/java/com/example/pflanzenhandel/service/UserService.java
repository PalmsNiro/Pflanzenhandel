package com.example.pflanzenhandel.service;

import com.example.pflanzenhandel.entity.*;
import com.example.pflanzenhandel.repository.BenutzerRepository;
import com.example.pflanzenhandel.repository.RolleRepository;
import com.example.pflanzenhandel.repository.UserQuestRepository;
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
    private UserQuestRepository userQuestRepository;

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

    @Transactional
    public Benutzer assignRandomQuestsToUser(Long userId, int numberOfQuests) {
        Benutzer user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Set<UserQuest> userQuests = user.getUserQuests();

        // Berechnung der Quests in der aktuellen Woche
        int currentWeekQuestCount = (int) userQuests.stream()
                .filter(userQuest -> userQuest.getAssignedDate() != null && userQuest.getAssignedDate().isAfter(LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))))
                .count();

        System.out.println("Current week quest count: " + currentWeekQuestCount);

        int maxQuestsThisWeek = 5;
        int maxQuestsAtStartOfWeek = 5;

        // Berechnung der Anzahl der zuzuweisenden Quests unter Berücksichtigung der Limits
        int questsToAssign = Math.min(numberOfQuests, maxQuestsAtStartOfWeek);
        if (currentWeekQuestCount + questsToAssign > maxQuestsThisWeek) {
            questsToAssign = maxQuestsThisWeek - currentWeekQuestCount;
        }

        System.out.println("Quests to assign: " + questsToAssign);

        // Finalize the user variable to use it inside the lambda
        final Benutzer finalUser = user;

        // Zuweisung der Quests, wenn es Quests gibt, die zugewiesen werden müssen
        if (questsToAssign > 0) {
            List<Quest> randomQuests = questService.getRandomQuests(questsToAssign);
            randomQuests.forEach(quest -> {
                UserQuest userQuest = new UserQuest();
                userQuest.setUser(finalUser);
                userQuest.setQuest(quest);
                userQuest.setAssignedDate(LocalDateTime.now());
                userQuest.setProgress(0);
                userQuests.add(userQuest);
                userQuestRepository.save(userQuest); // Speichern der UserQuest
            });

            System.out.println("Assigned Quests: " + randomQuests);
            System.out.println("Number Of Quests assigned to User total: " + userQuests.size());

            user.setUserQuests(userQuests); // Setze die aktualisierten Quests

            user = userRepository.save(user); // Speichern des Benutzers mit den neuen Quests
        }

        return user;
    }

    @Transactional
    public void addExperiencePoints(Benutzer user, int points) {
        user.setExperiencePoints(user.getExperiencePoints() + points);
        if (user.getExperiencePoints() >= 10) {
            user.setExperiencePoints(0);
            user.setLevel(user.getLevel() + 1);
        }

        // Aktualisieren des Fortschritts bei den Quests
        List<UserQuest> userQuests = userQuestRepository.findByUser(user);
        for (UserQuest userQuest : userQuests) {
            if (userQuest.getQuest().getDescription().contains("Erfahrungs Punkte.")) {
                userQuest.setProgress(userQuest.getProgress() + points);
                if (userQuest.getProgress() >= userQuest.getQuest().getNeededAmount()) {
                    // Markiere die Quest als abgeschlossen (hier können Sie zusätzliche Logik hinzufügen)
                    System.out.println("Quest abgeschlossen: " + userQuest.getQuest().getDescription());
                }
                userQuestRepository.save(userQuest);
            }
        }

        userRepository.save(user);
    }
}