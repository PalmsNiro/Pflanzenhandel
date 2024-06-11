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
        normalBenutzer.setPassword("1234");
        normalBenutzer.setRoles(userRolles);
        userService.saveUser(normalBenutzer);

        Benutzer debbyBenutzer = new Benutzer();
        debbyBenutzer.setUsername("Debby");
        debbyBenutzer.setPassword("1234");
        debbyBenutzer.setRoles(userRolles);
        userService.saveUser(debbyBenutzer);

        Benutzer admin = new Benutzer();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRoles(adminRolles);
        userService.saveUser(admin);



        // create and save products
        Product product1 = new Product();
        product1.setName("Pflanze lol");
        product1.setPrice(6.90);
        product1.setHeight(10.5);
        product1.setOverPot(false);
        product1.setShippingCosts(5.50);
        product1.setDescription("This a Pflanze and it does Pflanzen things");
        product1.setVerkaufer(debbyBenutzer);
        productService.saveProduct(product1);

        Product product2 = new Product();
        product2.setName("Baum");
        product2.setPrice(69.69);
        product2.setHeight(10.5);
        product2.setOverPot(false);
        product2.setShippingCosts(5.50);
        product2.setDescription("Baum zu verschenken");
        product2.setVerkaufer(normalBenutzer);
        productService.saveProduct(product2);

        Product product3 = new Product();
        product3.setName("Palme");
        product3.setPrice(30);
        product3.setHeight(1.5);
        product3.setOverPot(false);
        product3.setShippingCosts(5.50);
        product3.setDescription("Palme sehr schöm");
        product3.setVerkaufer(debbyBenutzer);
        productService.saveProduct(product3);

        Product product4 = new Product();
        product4.setName("Orchidee");
        product4.setPrice(5.5);
        product4.setHeight(0.5);
        product4.setOverPot(true);
        product4.setShippingCosts(5.50);
        product4.setDescription("Toll");
        product4.setVerkaufer(debbyBenutzer);
        productService.saveProduct(product4);

        Product product5 = new Product();
        product5.setName("Kaktus");
        product5.setPrice(0.15);
        product5.setHeight(2.4);
        product5.setOverPot(true);
        product5.setShippingCosts(5.50);
        product5.setDescription("Achtung spitz");
        product5.setVerkaufer(normalBenutzer);
        productService.saveProduct(product5);
    }
}