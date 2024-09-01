package org.example.inventory_read_service.dto;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inventory_read_service.entity.inventory.product.Manufacturer;
import org.example.inventory_read_service.entity.inventory.product.Product;
import org.example.inventory_read_service.entity.inventory.product.ProductCost;
import org.example.inventory_read_service.entity.inventory.product.ProductDetails;
import org.example.inventory_read_service.entity.inventory.subProductCategory.SubProductCategory;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private UUID productId;


    private String productName;

    private String productPicture;

    private ProductDetails productDetails;

    private ProductCost productCost;

    private boolean isActive;

    private Manufacturer productManufacturer;


    private SubProductCategory subProductCategory;


    public Product generateProductFromDto(ProductDto productDto){

        return Product
                .builder()
                .product_id(productDto.getProductId())
                .product_name(productDto.getProductName())
                .product_picture(productDto.getProductPicture())
                .product_details(productDto.getProductDetails())
                .product_cost(productDto.getProductCost())
                .isActive(productDto.isActive())
                .product_manufacturer(productDto.getProductManufacturer())
                .subProductCategory(productDto.getSubProductCategory())
                .build();
    }

}
