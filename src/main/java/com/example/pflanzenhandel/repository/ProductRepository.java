package com.example.pflanzenhandel.repository;

import com.example.pflanzenhandel.entity.Product;
import com.example.pflanzenhandel.entity.Benutzer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingOrDescriptionContaining(String name, String description);
    Product save(Product product);
    List<Product> findByBuyer(Benutzer buyer);

    List<Product> findByMarked(boolean marked);
    List<Product> findByIsSold(boolean isSold);


    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% ORDER BY p.name ASC")
    List<Product> searchAndSortByKeywordAsc(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword% ORDER BY p.name DESC")
    List<Product> searchAndSortByKeywordDesc(@Param("keyword") String keyword);

    @Query("SELECT p FROM Product p ORDER BY p.name ASC")
    List<Product> sortByNameAsc();

    @Query("SELECT p FROM Product p ORDER BY p.name DESC")
    List<Product> sortByNameDesc();

    @Query("SELECT p FROM Product p WHERE p.isSold = false")
    List<Product> findAllUnsoldProducts();
}
