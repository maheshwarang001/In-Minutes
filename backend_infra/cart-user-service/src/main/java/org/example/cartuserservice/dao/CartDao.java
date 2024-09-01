package org.example.cartuserservice.dao;

import org.example.cartuserservice.entity.Cart;
import org.example.cartuserservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartDao {

    @Autowired
    private CartRepository cartRepository;

    public void saveCart(Cart cart){
        cartRepository.save(cart);
    }
}
