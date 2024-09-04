package org.example.inventory_read_service.controller;

import org.example.inventory_read_service.dto.CartOrderCheckList;
import org.example.inventory_read_service.dto.CartOrderResponseDto;
import org.example.inventory_read_service.dto.EstimateCheckCartDto;
import org.example.inventory_read_service.dto.QueryProduct;
import org.example.inventory_read_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/inventory-read/check/v1")
public class RealTimeController {

    @Autowired
    private ProductService productService;

    @PostMapping("/cart/final")
    public CompletableFuture<ResponseEntity<EstimateCheckCartDto>> provideRealTimeCartValue(@RequestBody QueryProduct QueryProduct) {
        return productService.checkProduct(QueryProduct)
                .thenApply(cartOrderResponseDto -> ResponseEntity.ok().body(cartOrderResponseDto))
                .exceptionally(ex -> ResponseEntity.badRequest().body(null));
    }

}
