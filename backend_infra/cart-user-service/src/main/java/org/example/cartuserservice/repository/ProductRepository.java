package org.example.cartuserservice.repository;

import org.example.cartuserservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {


    @Query("SELECT ps FROM Product ps WHERE ps.cart.cartId = :cartId")
    List<Product> findProductByCart(@Param("cartId")long cartId);


    @Query("SELECT ps FROM Product ps WHERE ps.cart.cartId = :cartId AND ps.productId = :productId")
    Product findProductByCartAndProductId(@Param("cartId")long cartId,  @Param("productId") UUID productId);





}
