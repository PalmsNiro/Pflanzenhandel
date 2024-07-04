package com.example.pflanzenhandel.service;

import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingOrDescriptionContaining(query, query);
    }
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> sortProductsByName(boolean ascending) {
        return ascending ? productRepository.sortByNameAsc() : productRepository.sortByNameDesc();
    }

    public void markProduct(Long productId, boolean marked) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NoSuchElementException("Product not found"));
        product.setMarked(marked);
        productRepository.save(product);
    }
    public List<Product> findMarkedProducts() {
        return productRepository.findByMarked(true);
    }



}