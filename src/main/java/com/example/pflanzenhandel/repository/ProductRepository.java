package com.example.pflanzenhandel.repository;

import com.example.pflanzenhandel.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}