package com.example.pflanzenhandel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * The main entry point for the Pflanzenhandel Spring Boot application.
 */
@SpringBootApplication
@EnableScheduling
public class PflanzenhandelApplication {
    public static void main(String[] args) {
        SpringApplication.run(PflanzenhandelApplication.class, args);
    }
}
