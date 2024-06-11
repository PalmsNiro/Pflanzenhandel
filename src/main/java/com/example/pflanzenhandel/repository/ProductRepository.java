package com.example.pflanzenhandel.repository;

import com.example.pflanzenhandel.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);
    Product save(Product product);
}