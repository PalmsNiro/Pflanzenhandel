package com.example.pflanzenhandel.service;

import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.entity.Benutzer;
import com.example.pflanzenhandel.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
    public List<Product> getAllUnsoldProducts() {
        return productRepository.findAllUnsoldProducts();
    }
    public void markAsSold(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setIsSold(true);
            productRepository.save(product);
        }
    }

    public void requestPurchase(Long id, Benutzer buyer) {
        Product product = productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
        product.setBuyer(buyer);
        product.setPurchaseRequested(true);
        productRepository.save(product);
    }

    public List<Product> findProductsByVerkaufer(Benutzer verkaufer) {
        return productRepository.findByVerkaufer(verkaufer);
    }


    public List<Product> filterProducts(List<Product> products, String category, Double minPrice, Double maxPrice, Boolean hasUebertopf, Double minHeight, Double maxHeight) {
        if (category != null && !category.isEmpty()) {
            products = products.stream()
                    .filter(p -> p.getCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }
        if (minPrice != null) {
            products = products.stream()
                    .filter(p -> p.getPrice() >= minPrice)
                    .collect(Collectors.toList());
        }
        if (maxPrice != null) {
            products = products.stream()
                    .filter(p -> p.getPrice() <= maxPrice)
                    .collect(Collectors.toList());
        }
        if (hasUebertopf != null) {
            products = products.stream()
                    .filter(p -> p.getOverPot() == hasUebertopf)
                    .collect(Collectors.toList());
        }
        if (minHeight != null) {
            products = products.stream()
                    .filter(p -> p.getHeight() >= minHeight)
                    .collect(Collectors.toList());
        }
        if (maxHeight != null) {
            products = products.stream()
                    .filter(p -> p.getHeight() <= maxHeight)
                    .collect(Collectors.toList());
        }
        return products;
    }

    public List<Product> sortProducts(List<Product> products, String sort, String sortBy) {
        if (sort != null && sortBy != null) {
            Comparator<Product> comparator = null;
            switch (sortBy) {
                case "price":
                    comparator = Comparator.comparing(Product::getPrice);
                    break;
                case "height":
                    comparator = Comparator.comparing(Product::getHeight);
                    break;
                default:
                    comparator = Comparator.comparing(Product::getName);
                    break;
            }

            if ("desc".equals(sort)) {
                comparator = comparator.reversed();
            }

            products.sort(comparator);
        }
        return products;
    }

    public List<Product> filterAndSortProducts(String searchQuery, String category, Double minPrice, Double maxPrice, Boolean hasUebertopf, Double minHeight, Double maxHeight, String sort, String sortBy) {
        List<Product> products;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            products = searchProducts(searchQuery);
        } else {
            products = findAll();
        }

        products = filterProducts(products, category, minPrice, maxPrice, hasUebertopf, minHeight, maxHeight);
        products = sortProducts(products, sort, sortBy);

        return products;
    }

    public List<Product> findPurchasedProducts(Benutzer buyer) {
        return productRepository.findByBuyerAndConfirmedPurchase(buyer, true);
    }

    public void boostProduct(Product product) {
        product.setBoosted(true);
        productRepository.save(product);
    }
}