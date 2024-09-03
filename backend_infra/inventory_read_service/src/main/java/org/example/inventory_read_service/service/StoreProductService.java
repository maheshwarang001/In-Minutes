package org.example.inventory_read_service.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.concurrent.CompletedFuture;
import org.example.inventory_read_service.dao.StoreProductDao;
import org.example.inventory_read_service.dto.DarkStoreProductDto;
import org.example.inventory_read_service.dto.StoreProductDto;
import org.example.inventory_read_service.entity.inventory.product.Product;
import org.example.inventory_read_service.entity.store.DarkStore;
import org.example.inventory_read_service.entity.store.StoreProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
public class StoreProductService {

    @Autowired
    private StoreProductDao storeProductDao;


    @Autowired
    private StoreService storeService;

    @Autowired
    private ProductService productService;





    @Transactional
    public void updateProductInStore(DarkStoreProductDto productDto) {
        try {
            // Fetch dark store and product asynchronously
            CompletableFuture<Optional<DarkStore>> darkStoreFuture = CompletableFuture
                    .supplyAsync(() -> storeService.findDarkStoreById(productDto.getDarkStore()));

            CompletableFuture<Optional<Product>> productFuture = CompletableFuture
                    .supplyAsync(() -> productService.findProduct(productDto.getProduct()));

            // Wait for both futures to complete
            CompletableFuture.allOf(darkStoreFuture, productFuture).join();

            // Extract results from futures
            DarkStore darkStore = darkStoreFuture.get().orElseThrow(() -> new NoSuchElementException("Dark store not found"));
            Product product = productFuture.get().orElseThrow(() -> new NoSuchElementException("Product not found"));

            // Save store product
            StoreProduct storeProduct = StoreProduct.builder()
                    .product(product)
                    .store(darkStore)
                    .quantity(productDto.getQty())
                    .build();

            storeProductDao.saveStoreProduct(storeProduct);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Interrupted while waiting for CompletableFutures to complete", e);
        } catch (ExecutionException e) {
            log.error("Execution error occurred while fetching data", e);
        } catch (Exception e) {
            log.error("An error occurred while updating product in store", e);
        }
    }

}
