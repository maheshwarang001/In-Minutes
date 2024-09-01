package org.example.inventory_read_service.repository;

import org.example.inventory_read_service.entity.inventory.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {


//    Optional<Product> findProductByProductName()

    @Query("SELECT p FROM Product p")
    List<Product> getAllProduct();


    @Query("SELECT ps FROM Product ps  WHERE ps.product_id = : id ")
    Optional<Product> getProduct(@Param("id")UUID id);



}
