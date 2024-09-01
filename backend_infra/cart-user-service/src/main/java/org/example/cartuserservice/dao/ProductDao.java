package org.example.cartuserservice.dao;


import lombok.extern.slf4j.Slf4j;
import org.example.cartuserservice.entity.Product;
import org.example.cartuserservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class ProductDao {


    @Autowired
    private ProductRepository productRepository;

    public void saveProduct(Product product){
        productRepository.save(product);
    }


    public List<Product> productListForCart(long cartId){
        return productRepository.findProductByCart(cartId);
    }

    public Product productListForCartAndPid(long cartId, UUID pId){
        return productRepository.findProductByCartAndProductId(cartId,pId);
    }

    public void delete(long id){
        productRepository.deleteById(id);
    }





}
