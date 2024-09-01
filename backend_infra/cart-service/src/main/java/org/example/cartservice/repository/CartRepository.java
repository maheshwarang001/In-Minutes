package org.example.cartservice.repository;

import org.example.cartservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {

    @Query("SELECT ps FROM Cart ps WHERE ps.customerId = :userId")
    Optional<Cart> findCartByUserId(@Param("userId") UUID userId);



    @Query("DELETE FROM Cart c WHERE c.cartId = :id")
    void deleteCartByCartID(UUID id);

}
