package org.example.inventory_write_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inventory_write_service.entity.inventory.product.Product;
import org.example.inventory_write_service.entity.store.DarkStore;
import org.example.inventory_write_service.entity.store.StoreProduct;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreProductDto {

    private UUID storeProductId;
    private DarkStore store;

    private Product product;

    private int quantity;

    public StoreProduct generateStoreProductFromDto(StoreProductDto storeProductDto){
        return StoreProduct
                .builder()
                .storeProduct_id(storeProductDto.getStoreProductId())
                .store(storeProductDto.getStore())
                .product(storeProductDto.getProduct())
                .quantity(storeProductDto.getQuantity())
                .build();
    }
}
