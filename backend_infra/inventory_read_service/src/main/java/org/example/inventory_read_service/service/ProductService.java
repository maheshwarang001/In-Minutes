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

import java.util.*;
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


    public Optional<Product> findProduct(UUID id){
        return productDao.getProductById(id);
    }


    @Async
    public CompletableFuture<EstimateCheckCartDto> checkProduct(QueryProduct QueryProduct) {

        AtomicReference<Double> totalCost = new AtomicReference<>(0.0);
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        CopyOnWriteArrayList<ItemDetailsDto> responseDto = new CopyOnWriteArrayList<>();

        // Process each item concurrently
        for (Item item : QueryProduct.getItems()) {

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                StoreProduct storeProduct = storeProductDao.getProductFromDarkStore(QueryProduct.getStoreId(), item.getProductId());

                if (storeProduct != null) {

                    log.info(storeProduct.getProduct().getProduct_name());

                    double cost = storeProduct.getProduct().getProduct_cost().getMrp() * Math.min(storeProduct.getQuantity(), item.getQty());
                    totalCost.accumulateAndGet(cost, Double::sum);

                    ItemDetailsDto cartProductResponseDto = ItemDetailsDto.builder()
                            .productId(storeProduct.getProduct().getProduct_id())
                            .productName(storeProduct.getProduct().getProduct_name())
                            .productImage(storeProduct.getProduct().getProduct_picture())
                            .totalProductCost(Math.min(storeProduct.getQuantity() , item.getQty())  * storeProduct.getProduct().getProduct_cost().getMrp())
                            .productCost(storeProduct.getProduct().getProduct_cost().getMrp())
                            .qty(Math.min(storeProduct.getQuantity() , item.getQty()))
                            .build();

                    responseDto.add(cartProductResponseDto);
                }
            });
            futures.add(future);
        }

        // Wait for all tasks to complete
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        return allOf.thenApply(v ->

                EstimateCheckCartDto.
                        builder()
                        .productDetailDtoList(responseDto)
                        .invoiceProduct(InvoiceProduct.builder().totalProductCost(totalCost.get()).build())
                        .build());


//                CartOrderResponseDto.builder()
//                .responseDto(responseDto)
//                .totalCost(totalCost.get())
//                .build());
    }




}
