package org.example.inventory_read_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_read_service.dao.ProductDao;
import org.example.inventory_read_service.dao.StoreProductDao;
import org.example.inventory_read_service.dto.*;
import org.example.inventory_read_service.entity.inventory.product.Product;
import org.example.inventory_read_service.entity.store.StoreProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductDao productDao;


    @Autowired
    private StoreProductDao storeProductDao;


    @Transactional
    public void createProduct(ProductDto productDto)throws Exception{
        try {
            Product p = new ProductDto().generateProductFromDto(productDto);
            productDao.addProductToInventory(p);
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @Transactional
    public void updateProduct(ProductDto productDto)throws Exception{
        try {

            Product p = new ProductDto().generateProductFromDto(productDto);
            productDao.updateProduct(p);

        }catch (Exception e){
            log.error(e.getMessage());
            throw e;

        }
    }


    @Async
    public CompletableFuture<CartOrderResponseDto> checkProduct(CartOrderCheckList cartOrderCheckList) {

        AtomicReference<Double> totalCost = new AtomicReference<>(0.0);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        CopyOnWriteArrayList<CartProductResponseDto> responseDto = new CopyOnWriteArrayList<>();

        // Process each item concurrently
        for (CartOrderItemDto itemDto : cartOrderCheckList.getCartOrderItemDtoList()) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                StoreProduct storeProduct = storeProductDao.getProductFromDarkStore(cartOrderCheckList.getStoreId(), itemDto.getProductId());

                if (storeProduct != null) {
                    double cost = storeProduct.getProduct().getProduct_cost().getMrp() * Math.min(storeProduct.getQuantity(), itemDto.getQty());
                    totalCost.accumulateAndGet(cost, Double::sum);

                    CartProductResponseDto cartProductResponseDto = CartProductResponseDto.builder()
                            .id(storeProduct.getProduct().getProduct_id())
                            .qty(Math.min(storeProduct.getQuantity(), itemDto.getQty()))
                            .cost(storeProduct.getProduct().getProduct_cost().getMrp())
                            .build();

                    responseDto.add(cartProductResponseDto);
                }
            });
            futures.add(future);
        }

        // Wait for all tasks to complete
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        return allOf.thenApply(v -> CartOrderResponseDto.builder()
                .responseDto(responseDto)
                .totalCost(totalCost.get())
                .build());
    }




}
