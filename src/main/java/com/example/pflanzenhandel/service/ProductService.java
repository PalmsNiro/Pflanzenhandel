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

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingOrDescriptionContaining(query, query);


    }

    public void updateProduct(Product product) {
        Product existingProduct = productRepository.findById(product.getId()).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setHeight(product.getHeight());
            existingProduct.setOverPot(product.getOverPot());
            existingProduct.setShippingCosts(product.getShippingCosts());
            existingProduct.setImageUrl(product.getImageUrl());
            existingProduct.setVerkaufer(product.getVerkaufer());
            productRepository.save(existingProduct);
        }
    }
}