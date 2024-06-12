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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Initialisiere Datenbank mit Testdaten...");

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

        if (normalBenutzer.getId() == null) {
            logger.error("Fehler beim Speichern des Benutzers 'user'");
        } else {
            logger.info("Benutzer 'user' erfolgreich gespeichert mit ID: " + normalBenutzer.getId());
        }

        Benutzer debbyBenutzer = new Benutzer();
        debbyBenutzer.setUsername("Debby");
        debbyBenutzer.setPassword("1234");
        debbyBenutzer.setRoles(userRolles);
        userService.saveUser(debbyBenutzer);

        if (debbyBenutzer.getId() == null) {
            logger.error("Fehler beim Speichern des Benutzers 'Debby'");
        } else {
            logger.info("Benutzer 'Debby' erfolgreich gespeichert mit ID: " + debbyBenutzer.getId());
        }

        Benutzer admin = new Benutzer();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRoles(adminRolles);
        userService.saveUser(admin);

        if (admin.getId() == null) {
            logger.error("Fehler beim Speichern des Benutzers 'admin'");
        } else {
            logger.info("Benutzer 'admin' erfolgreich gespeichert mit ID: " + admin.getId());
        }

Product product1 = new Product();
        product1.setName("Clivia Mininata");
        product1.setPrice(6.90);
        product1.setHeight(10.5);
        product1.setOverPot(false);
        product1.setShippingCosts(5.50);
        product1.setDescription("This a Pflanze and it does Pflanzen things");
        product1.setVerkaufer(debbyBenutzer);
        product1.setImageUrl("https://eastgate.megapaints.co.za/wp-content/uploads/671826-1.jpg");
        productService.saveProduct(product1);

        Product product2 = new Product();
        product2.setName("Pachira aquatica");
        product2.setPrice(69.69);
        product2.setHeight(10.5);
        product2.setOverPot(false);
        product2.setShippingCosts(5.50);
        product2.setDescription("Baum zu verschenken");
        product2.setVerkaufer(normalBenutzer);
        product2.setImageUrl("https://cf.ltkcdn.net/feng-shui/images/std-xs/270900-340x340-money-tree-feng-shui.jpg");
        productService.saveProduct(product2);

        Product product3 = new Product();
        product3.setName("Palme");
        product3.setPrice(30.00);
        product3.setHeight(1.5);
        product3.setOverPot(false);
        product3.setShippingCosts(5.50);
        product3.setDescription("Palme sehr schöm");
        product3.setVerkaufer(debbyBenutzer);
        product3.setImageUrl("https://img.kleinanzeigen.de/api/v1/prod-ads/images/78/78ce54d4-f1b0-48c4-9299-325d63c9d880?rule=$_59.JPG");
        productService.saveProduct(product3);

        Product product4 = new Product();
        product4.setName("Orchidee");
        product4.setPrice(5.5);
        product4.setHeight(0.5);
        product4.setOverPot(true);
        product4.setShippingCosts(5.50);
        product4.setDescription("Toll");
        product4.setVerkaufer(debbyBenutzer);
        product4.setImageUrl("https://cdn.mdr.de/mdr-garten/pflanzen/phalaenopsis-orchideen-zimmerpflanze-bluete-102_v-variantBig1x1_w-1280_zc-3061602c.jpg?version=58136");
        productService.saveProduct(product4);

        Product product5 = new Product();
        product5.setName("Bonsai Baum");
        product5.setPrice(0.15);
        product5.setHeight(2.4);
        product5.setOverPot(true);
        product5.setShippingCosts(5.50);
        product5.setDescription("Wunderschön!");
        product5.setImageUrl("https://i.ebayimg.com/images/g/O98AAOSw415mGjj0/s-l1600.jpg");
        product5.setVerkaufer(normalBenutzer);
        productService.saveProduct(product5);
    }
}
