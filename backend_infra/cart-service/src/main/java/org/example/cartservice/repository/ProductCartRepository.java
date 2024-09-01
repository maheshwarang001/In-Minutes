package org.example.cartservice.repository;

import org.example.cartservice.entity.Cart;
import org.example.cartservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProductCartRepository extends JpaRepository<Product , UUID> {

    @Query("SELECT ps FROM Product ps WHERE ps.productId = :pid")
    Optional<Product> findProductById(@Param("pid")UUID pid);





}
