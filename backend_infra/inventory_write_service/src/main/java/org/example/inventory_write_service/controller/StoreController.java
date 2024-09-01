package org.example.inventory_write_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_write_service.dao.StoreProductDao;
import org.example.inventory_write_service.dto.StoreC;
import org.example.inventory_write_service.service.StoreProductService;
import org.example.inventory_write_service.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/inventory-service/store/v1")
@Slf4j
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreProductService storeProductService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    @PostMapping("/create-store")
    public ResponseEntity<String> createStore(@RequestBody StoreC store) {
        try {
            storeService.createStoreService(store);
            return ResponseEntity.status(HttpStatus.OK).body("Store created successfully.");
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating store: " + e.getMessage());
        }
    }

    @GetMapping("/update-store-sync/inventory")
    public ResponseEntity<String> updateStoreInventory(@RequestParam UUID storeId) {
        try {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    storeService.updateStoreWithCatalog(storeId);
                } catch (Exception e) {
                    log.error("Error updating store with catalog: ", e);
                    throw new RuntimeException(e);
                }
            }, executorService);

            future.join(); // Wait for the task to complete

            return ResponseEntity.status(HttpStatus.OK).body("Store inventory update completed successfully.");
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating store: " + e.getMessage());
        }
    }

    @GetMapping("/update-store/manual/inventory")
    public ResponseEntity<String> manualCreateProductToDarkStore(@RequestParam UUID storeId , @RequestParam UUID productId, @RequestParam int qty){
        try {

            storeProductService.updateInventorySpecificToDarkStoreFirstTime(storeId,productId,qty);

            return ResponseEntity.status(HttpStatus.OK).body("Store inventory update completed successfully.");
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error updating store: " + e.getMessage());
        }
    }

}
