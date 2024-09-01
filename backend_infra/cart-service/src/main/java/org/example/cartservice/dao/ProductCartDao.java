package org.example.cartservice.dao;


import lombok.extern.slf4j.Slf4j;
import org.example.cartservice.entity.Product;
import org.example.cartservice.repository.ProductCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class ProductCartDao {

    @Autowired
    private ProductCartRepository productCartRepository;


    public void save(Product product){
        try {
            productCartRepository.save(product);
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
        }
    }

    public CompletableFuture<Optional<Product>> findProductByOptional(UUID id) {
        return CompletableFuture.supplyAsync(() -> productCartRepository.findProductById(id));
    }



}
