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

import java.util.Arrays;
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

    @Autowired
    private QuestService questService;

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

        Benutzer normalUser = new Benutzer();
        normalUser.setUsername("user");
        normalUser.setPassword("1234");
        normalUser.setRoles(userRolles);
        normalUser.setExperiencePoints(5);
        normalUser.setLevel(2);
        userService.saveUser(normalUser);

        if (normalUser.getId() == null) {
            logger.error("Fehler beim Speichern des Benutzers 'user'");
        } else {
            logger.info("Benutzer 'user' erfolgreich gespeichert mit ID: " + normalUser.getId());
        }

        Benutzer debbyUser = new Benutzer();
        debbyUser.setUsername("Debby");
        debbyUser.setPassword("1234");
        debbyUser.setRoles(userRolles);
        userService.saveUser(debbyUser);

        if (debbyUser.getId() == null) {
            logger.error("Fehler beim Speichern des Benutzers 'Debby'");
        } else {
            logger.info("Benutzer 'Debby' erfolgreich gespeichert mit ID: " + debbyUser.getId());
        }

        Benutzer admin = new Benutzer();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setExperiencePoints(9);
        admin.setLevel(9);
        admin.setRoles(adminRolles);
        userService.saveUser(admin);

        if (admin.getId() == null) {
            logger.error("Fehler beim Speichern des Benutzers 'admin'");
        } else {
            logger.info("Benutzer 'admin' erfolgreich gespeichert mit ID: " + admin.getId());
        }

        /*-------------------------------------------------Products----------------------------------------------*/

        Product product1 = new Product();
        product1.setName("Clivia Mininata");
        product1.setPrice(6.90);
        product1.setHeight(10.5);
        product1.setOverPot(false);
        product1.setShippingCosts(5.50);
        product1.setDescription("This a Pflanze and it does Pflanzen things");
        product1.setVerkaufer(debbyUser);
        product1.setImageUrls(Arrays.asList("https://eastgate.megapaints.co.za/wp-content/uploads/671826-1.jpg"));
        product1.setMainImageUrl("https://eastgate.megapaints.co.za/wp-content/uploads/671826-1.jpg");
        productService.saveProduct(product1);

        Product product2 = new Product();
        product2.setName("Pachira aquatica");
        product2.setPrice(69.69);
        product2.setHeight(10.5);
        product2.setOverPot(false);
        product2.setShippingCosts(5.50);
        product2.setDescription("Baum zu verschenken");
        product2.setVerkaufer(normalUser);
        product2.setImageUrls(Arrays.asList("https://cf.ltkcdn.net/feng-shui/images/std-xs/270900-340x340-money-tree-feng-shui.jpg"));
        product2.setMainImageUrl("https://cf.ltkcdn.net/feng-shui/images/std-xs/270900-340x340-money-tree-feng-shui.jpg");
        productService.saveProduct(product2);

        Product product3 = new Product();
        product3.setName("Palme");
        product3.setPrice(30.00);
        product3.setHeight(1.5);
        product3.setOverPot(false);
        product3.setShippingCosts(5.50);
        product3.setDescription("Palme sehr schöm");
        product3.setVerkaufer(debbyUser);
        product3.setImageUrls(Arrays.asList("https://img.kleinanzeigen.de/api/v1/prod-ads/images/78/78ce54d4-f1b0-48c4-9299-325d63c9d880?rule=$_59.JPG"));
        product3.setMainImageUrl("https://img.kleinanzeigen.de/api/v1/prod-ads/images/78/78ce54d4-f1b0-48c4-9299-325d63c9d880?rule=$_59.JPG");
        productService.saveProduct(product3);

        Product product4 = new Product();
        product4.setName("Orchidee");
        product4.setPrice(5.5);
        product4.setHeight(0.5);
        product4.setOverPot(true);
        product4.setShippingCosts(5.50);
        product4.setDescription("Toll");
        product4.setVerkaufer(debbyUser);
        product4.setImageUrls(Arrays.asList("https://cdn.mdr.de/mdr-garten/pflanzen/phalaenopsis-orchideen-zimmerpflanze-bluete-102_v-variantBig1x1_w-1280_zc-3061602c.jpg?version=58136", "https://cdn.pflanzen-koelle.de/media/26/20/17/1683680685/0215152627-Phalaenopsis-Midi-Dark-Pink-2R-12cmT-H65cm2_124057.jpg?width=520&quality=80"));
        product4.setMainImageUrl("https://cdn.mdr.de/mdr-garten/pflanzen/phalaenopsis-orchideen-zimmerpflanze-bluete-102_v-variantBig1x1_w-1280_zc-3061602c.jpg?version=58136");
        productService.saveProduct(product4);

        Product product5 = new Product();
        product5.setName("Bonsai Baum");
        product5.setPrice(0.15);
        product5.setHeight(2.4);
        product5.setOverPot(true);
        product5.setShippingCosts(5.50);
        product5.setDescription("Wunderschön!");
        product5.setImageUrls(Arrays.asList("https://www.bonsai.saarland/images/product_images/info_images/DSC_0262.JPG", "https://www.bonsai.saarland/images/product_images/info_images/DSC_0263.JPG"));
        product5.setVerkaufer(normalUser);
        product5.setMainImageUrl("https://www.bonsai.saarland/images/product_images/info_images/DSC_0263.JPG");
        productService.saveProduct(product5);


        /*-------------------------------------------------Quests----------------------------------------------*/
        Quest quest1 = new Quest();
        quest1.setDescription("Schreibe 100 Nachrichten.");
        quest1.setNeededAmount(100);
        questService.save(quest1);

        Quest quest2 = new Quest();
        quest2.setDescription("Erhalte insgesammt 50 Erfahrungs Punkte.");
        quest2.setNeededAmount(50);
        questService.save(quest2);

        Quest quest3 = new Quest();
        quest3.setDescription("Kaufe eine Pflanze.");
        quest3.setNeededAmount(1);
        questService.save(quest3);

        Quest quest4 = new Quest();
        quest4.setDescription("Favorisiere 10 Pflanzen");
        quest4.setNeededAmount(100);
        questService.save(quest4);

        Quest quest5 = new Quest();
        quest5.setDescription("Erhöhe dein Level ein mal");
        quest5.setNeededAmount(100);
        questService.save(quest5);

        Quest quest6 = new Quest();
        quest6.setDescription("Schreibe 50 Nachrichten");
        quest6.setNeededAmount(100);
        questService.save(quest6);
    }
}
