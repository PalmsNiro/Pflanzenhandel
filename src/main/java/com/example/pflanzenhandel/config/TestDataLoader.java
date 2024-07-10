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

        /*-------------------------------------------------Quests----------------------------------------------*/
        // Funktional
        Quest quest1 = new Quest();
        quest1.setDescription("Schreibe 100 Nachrichten.");
        quest1.setNeededAmount(100);
        quest1.setXpForUser(10);
        questService.save(quest1);

        //Funktional
        Quest quest2 = new Quest();
        quest2.setDescription("Erhalte insgesammt 50 Erfahrungs Punkte.");
        quest2.setNeededAmount(50);
        quest2.setXpForUser(5);
        questService.save(quest2);

        Quest quest3 = new Quest();
        quest3.setDescription("Kaufe eine Pflanze.");
        quest3.setNeededAmount(1);
        quest3.setXpForUser(2);
        questService.save(quest3);

        //Funktional
        Quest quest4 = new Quest();
        quest4.setDescription("Favorisiere 5 Pflanzen");
        quest4.setNeededAmount(5);
        quest4.setXpForUser(1);
        questService.save(quest4);

        //Funktional
        Quest quest5 = new Quest();
        quest5.setDescription("Erhöhe dein Level ein mal");
        quest5.setNeededAmount(1);
        quest5.setXpForUser(2);
        questService.save(quest5);

        // Funktional
        Quest quest6 = new Quest();
        quest6.setDescription("Schreibe 50 Nachrichten");
        quest6.setNeededAmount(50);
        quest6.setXpForUser(5);
        questService.save(quest6);

        //Funktional
        Quest quest7 = new Quest();
        quest7.setDescription("Erhalte insgesammt 25 Erfahrungs Punkte.");
        quest7.setNeededAmount(25);
        quest7.setXpForUser(3);
        questService.save(quest7);

        Quest questForTest2 = new Quest();
        questForTest2.setDescription("Schreibe 2 Nachrichten");
        questForTest2.setNeededAmount(2);
        questForTest2.setXpForUser(1);
        questService.save(questForTest2);

        //Funktional
        Quest questForTest = new Quest();
        questForTest.setDescription("Erhalte insgesammt 5 Erfahrungs Punkte.");
        questForTest.setNeededAmount(5);
        questForTest.setXpForUser(1);
        questService.save(questForTest);



        /*-------------------------------------------------User----------------------------------------------*/

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
        normalUser.setLevel(1);
        userService.saveUser(normalUser);

        if (normalUser.getId() == null) {
            logger.error("Fehler beim Speichern des Benutzers 'user'");
        } else {
            logger.info("Benutzer 'user' erfolgreich gespeichert mit ID: " + normalUser.getId());
        }


        Benutzer debbyUser = new Benutzer();

        UserQuest debbyQuest1 = new UserQuest();
        UserQuest debbyQuest2 = new UserQuest();
        UserQuest debbyQuest3 = new UserQuest();
        UserQuest debbyQuest4 = new UserQuest();
        UserQuest debbyQuest5 = new UserQuest();
        debbyQuest1.setUser(debbyUser);
        debbyQuest1.setQuest(quest5);
        debbyQuest2.setUser(debbyUser);
        debbyQuest2.setQuest(questForTest2);
        debbyQuest3.setUser(debbyUser);
        debbyQuest3.setQuest(questForTest);
        debbyQuest4.setUser(debbyUser);
        debbyQuest4.setQuest(quest4);
        debbyQuest5.setUser(debbyUser);
        debbyQuest5.setQuest(quest7);


        HashSet<UserQuest> debbyUserQuest = new HashSet<UserQuest>();
        debbyUserQuest.add(debbyQuest1);
        debbyUserQuest.add(debbyQuest2);
        debbyUserQuest.add(debbyQuest3);
        debbyUserQuest.add(debbyQuest4);
        debbyUserQuest.add(debbyQuest5);


        debbyUser.setUsername("debby");
        debbyUser.setPassword("1234");
        debbyUser.setFirstName("Debby");
        debbyUser.setLastName("Arueyingho");
        debbyUser.setEmail("debby.arueyingho@gmail.com");
        debbyUser.setLevel(5);
        debbyUser.setExperiencePoints(8);
        debbyUser.setRoles(userRolles);
        debbyUser.setNumberOfQuestsCompleted(2);
        debbyUser.setUserQuests(debbyUserQuest);
        userService.saveUser(debbyUser);

        if (debbyUser.getId() == null) {
            logger.error("Fehler beim Speichern des Benutzers 'Debby'");
        } else {
            logger.info("Benutzer 'Debby' erfolgreich gespeichert mit ID: " + debbyUser.getId());
        }

        Benutzer admin = new Benutzer();
        admin.setNumberOfBoosts(1);
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setExperiencePoints(9);
        admin.setLevel(1);
        admin.setRoles(adminRolles);
        userService.saveUser(admin);

        if (admin.getId() == null) {
            logger.error("Fehler beim Speichern des Benutzers 'admin'");
        } else {
            logger.info("Benutzer 'admin' erfolgreich gespeichert mit ID: " + admin.getId());
        }

        Benutzer isikUser = new Benutzer();
        isikUser.setUsername("isik");
        isikUser.setPassword("1234");
        isikUser.setRoles(userRolles);
        isikUser.setExperiencePoints(8);
        isikUser.setLevel(3);
        isikUser.setNumberOfQuestsCompleted(2);
        userService.saveUser(isikUser);

        if (isikUser.getId() == null) {
            logger.error("Fehler beim Speichern des Benutzers 'user'");
        } else {
            logger.info("Benutzer 'user' erfolgreich gespeichert mit ID: " + isikUser.getId());
        }

        Benutzer bastiUser = new Benutzer();
        bastiUser.setUsername("basti");
        bastiUser.setPassword("1234");
        bastiUser.setRoles(userRolles);
        bastiUser.setExperiencePoints(8);
        bastiUser.setLevel(6);
        bastiUser.setNumberOfQuestsCompleted(5);
        userService.saveUser(bastiUser);

        if (bastiUser.getId() == null) {
            logger.error("Fehler beim Speichern des Benutzers 'user'");
        } else {
            logger.info("Benutzer 'user' erfolgreich gespeichert mit ID: " + bastiUser.getId());
        }

        Benutzer yannicUser = new Benutzer();
        yannicUser.setUsername("yannic");
        yannicUser.setPassword("1234");
        yannicUser.setRoles(userRolles);
        yannicUser.setExperiencePoints(8);
        yannicUser.setLevel(4);
        yannicUser.setNumberOfQuestsCompleted(6);
        userService.saveUser(yannicUser);

        if (yannicUser.getId() == null) {
            logger.error("Fehler beim Speichern des Benutzers 'user'");
        } else {
            logger.info("Benutzer 'user' erfolgreich gespeichert mit ID: " + yannicUser.getId());
        }

        Benutzer phillipUser = new Benutzer();
        phillipUser.setUsername("phillip");
        phillipUser.setPassword("1234");
        phillipUser.setRoles(userRolles);
        phillipUser.setExperiencePoints(8);
        phillipUser.setLevel(5);
        phillipUser.setNumberOfQuestsCompleted(4);
        userService.saveUser(phillipUser);

        if (phillipUser.getId() == null) {
            logger.error("Fehler beim Speichern des Benutzers 'user'");
        } else {
            logger.info("Benutzer 'user' erfolgreich gespeichert mit ID: " + phillipUser.getId());
        }

        /*-------------------------------------------------Products----------------------------------------------*/

        Product product1 = new Product();
        product1.setName("Clivia Mininata");
        product1.setPrice(6.99);
        product1.setHeight(40);
        product1.setOverPot(false);
        product1.setDescription("Die Pflanze wurde regelmäßig gegossen und ist in einem guten Zustand. Bei Fragen gerne eine Nachricht an mich.");
        product1.setVerkaufer(yannicUser);
        product1.setCategory("Zimmerpflanze");
        product1.setImageUrls(Arrays.asList("https://eastgate.megapaints.co.za/wp-content/uploads/671826-1.jpg"));
        product1.setMainImageUrl("https://eastgate.megapaints.co.za/wp-content/uploads/671826-1.jpg");
        productService.saveProduct(product1);

        Product product6 = new Product();
        product6.setName("Blauregen");
        product6.setPrice(25.99);
        product6.setHeight(80);
        product6.setOverPot(true);
        product6.setDescription("Wunderschöner Blauregen, winterfest. Nur Abhohlung");
        product6.setImageUrls(Arrays.asList("https://www.poetschke.de/out/pictures/master/product/1/generated/560__/206586-00-GPIE.jpg.webp"));
        product6.setVerkaufer(debbyUser);
        product6.setCategory("Baum");
        product6.setMainImageUrl("https://www.poetschke.de/out/pictures/master/product/1/generated/560__/206586-00-GPIE.jpg.webp");
        productService.saveProduct(product6);

        Product product2 = new Product();
        product2.setName("Pachira aquatica");
        product2.setPrice(65.99);
        product2.setHeight(60);
        product2.setOverPot(false);
        product2.setDescription("Großer Pachira aquatica Baum in gutem Zustand. Perfekt für Ihr Zuhause oder Büro.");
        product2.setVerkaufer(bastiUser);
        product2.setCategory("Zimmerpflanze");
        product2.setImageUrls(Arrays.asList("https://cf.ltkcdn.net/feng-shui/images/std-xs/270900-340x340-money-tree-feng-shui.jpg"));
        product2.setMainImageUrl("https://cf.ltkcdn.net/feng-shui/images/std-xs/270900-340x340-money-tree-feng-shui.jpg");
        productService.saveProduct(product2);

        Product product7 = new Product();
        product7.setName("Lavendel");
        product7.setPrice(25.99);
        product7.setHeight(80);
        product7.setOverPot(true);
        product7.setDescription("Gut duftender Lavendel, stets gut gepflegt.");
        product7.setImageUrls(Arrays.asList("https://garten-von-ehren.imgbolt.de/thumbnail/56/38/68/1675700630/lavandula-angustifolia_1920x1920.jpg"));
        product7.setVerkaufer(debbyUser);
        product7.setCategory("Gartenpflanze");
        product7.setMainImageUrl("https://garten-von-ehren.imgbolt.de/thumbnail/56/38/68/1675700630/lavandula-angustifolia_1920x1920.jpg");
        productService.saveProduct(product7);

        Product product3 = new Product();
        product3.setName("Palme");
        product3.setPrice(30.00);
        product3.setHeight(150);
        product3.setOverPot(false);
        product3.setDescription("Schöne Palme, ideal für den Innenbereich. Bei Rückfragen stehe ich per Nachricht zur Verfügung.");
        product3.setVerkaufer(isikUser);
        product3.setCategory("Baum");
        product3.setImageUrls(Arrays.asList("https://img.kleinanzeigen.de/api/v1/prod-ads/images/78/78ce54d4-f1b0-48c4-9299-325d63c9d880?rule=$_59.JPG"));
        product3.setMainImageUrl("https://img.kleinanzeigen.de/api/v1/prod-ads/images/78/78ce54d4-f1b0-48c4-9299-325d63c9d880?rule=$_59.JPG");
        productService.saveProduct(product3);

        Product product4 = new Product();
        product4.setName("Orchidee");
        product4.setPrice(10.00);
        product4.setHeight(90);
        product4.setOverPot(true);
        product4.setDescription("Wunderschöne Orchidee mit lila Blüten. Mein Übertopf ist im Preis natürlich enthalten.");
        product4.setVerkaufer(debbyUser);
        product4.setCategory("Zimmerpflanze");
        product4.setImageUrls(Arrays.asList("https://cdn.mdr.de/mdr-garten/pflanzen/phalaenopsis-orchideen-zimmerpflanze-bluete-102_v-variantBig1x1_w-1280_zc-3061602c.jpg?version=58136", "https://cdn.pflanzen-koelle.de/media/26/20/17/1683680685/0215152627-Phalaenopsis-Midi-Dark-Pink-2R-12cmT-H65cm2_124057.jpg?width=520&quality=80"));
        product4.setMainImageUrl("https://cdn.mdr.de/mdr-garten/pflanzen/phalaenopsis-orchideen-zimmerpflanze-bluete-102_v-variantBig1x1_w-1280_zc-3061602c.jpg?version=58136");
        productService.saveProduct(product4);

        Product product5 = new Product();
        product5.setName("Bonsai Baum");
        product5.setPrice(80.00);
        product5.setHeight(50);
        product5.setOverPot(true);
        product5.setDescription("Regelmäßig gepflegter Bonsai Baum, perfekt fürs Büro.");
        product5.setImageUrls(Arrays.asList("https://www.bonsai.saarland/images/product_images/info_images/DSC_0262.JPG", "https://www.bonsai.saarland/images/product_images/info_images/DSC_0263.JPG"));
        product5.setVerkaufer(bastiUser);
        product5.setCategory("Baum");
        product5.setMainImageUrl("https://www.bonsai.saarland/images/product_images/info_images/DSC_0263.JPG");
        productService.saveProduct(product5);

        Product product8 = new Product();
        product8.setName("Schnittlauch");
        product8.setPrice(8.99);
        product8.setHeight(30);
        product8.setOverPot(false);
        product8.setDescription("Gut gewachsener Schnittlauch. Muss leider wegen Platzmangel meinerseits verkauft werden.");
        product8.setImageUrls(Arrays.asList("https://www.gwenys-haus.de/media/image/product/5262/lg/kunstpflanze-schnittlauch-at-topf.webp"));
        product8.setVerkaufer(phillipUser);
        product8.setCategory("Kräuter");
        product8.setMainImageUrl("https://www.gwenys-haus.de/media/image/product/5262/lg/kunstpflanze-schnittlauch-at-topf.webp");
        productService.saveProduct(product8);

    }
}
