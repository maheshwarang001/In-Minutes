package org.example.cartservice.service;

import org.example.cartservice.dto.ProductCartDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    @Autowired
    private ProductUtilityService productUtilityService;

    @Async
    public void createProduct(ProductCartDto productCartDto){
        productUtilityService.createProduct(productCartDto);
    }

    @Async
    public void updateProduct(ProductCartDto productCartDto){
        productUtilityService.updateProduct(productCartDto);
    }


}
