package com.example.pflanzenhandel.config;

import com.example.pflanzenhandel.entity.*;
import com.example.pflanzenhandel.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
/**
 * This class is responsible for loading test data into the database upon application startup.
 * The method 'onApplicationEvent' is triggered when the Spring context is initialized or refreshed.
 */
@Component
public class TestDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(TestDataLoader.class);

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    /**
     * Initializes the database with test data when the application context is initialized or refreshed.
     * This method creates sample objects and saves them using respective services.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Initialisiere Datenbank mit Testdaten...");

        // Initialize example objects and save them using respective services
        Rolle userRolle = new Rolle("ROLE_USER");
        Rolle adminRolle = new Rolle("ROLE_ADMIN");
        roleService.saveRole(userRolle);
        roleService.saveRole(adminRolle);

        Set<Rolle> userRolles = new HashSet<>();
        userRolles.add(userRolle);

        Set<Rolle> adminRolles = new HashSet<>();
        adminRolles.add(adminRolle);

        Benutzer normalBenutzer = new Benutzer();
        normalBenutzer.setUsername("user");
        normalBenutzer.setPassword(passwordEncoder.encode("1234"));
        normalBenutzer.setRoles(userRolles);
        userService.saveUser(normalBenutzer);

        Benutzer admin = new Benutzer();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRoles(adminRolles);
        userService.saveUser(admin);

        //Add new Product listings here
        Product product1 = new Product();
        product1.setName("Pflanze lol");
        product1.setPrice(6.90);
        product1.setHeight(10.5);
        product1.setOverPot(false);
        product1.setShippingCosts(5.50);
        product1.setDescription("This a Pflanze and it does Pflanzen things");
        productService.saveProduct(product1);
    }
}