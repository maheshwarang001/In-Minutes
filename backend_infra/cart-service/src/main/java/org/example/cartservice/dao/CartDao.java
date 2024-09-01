package org.example.cartservice.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.cartservice.entity.Cart;
import org.example.cartservice.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class CartDao {

    @Autowired
    private CartRepository cartRepository;

    public CompletableFuture<Optional<Cart>> findCartByUserId(UUID uid){
        return CompletableFuture.supplyAsync(()->cartRepository.findCartByUserId(uid));
    }

    public void saveCart(Cart cart){
        cartRepository.save(cart);
    }

    public void deleteCart(UUID cartId){
        cartRepository.deleteCartByCartID(cartId);
    }

}
