package com.example.pflanzenhandel.service;

import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingOrDescriptionContaining(query, query);
    }
}