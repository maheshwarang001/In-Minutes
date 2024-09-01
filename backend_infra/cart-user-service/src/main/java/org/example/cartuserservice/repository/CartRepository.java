package org.example.cartuserservice.repository;


import org.example.cartuserservice.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {



}
