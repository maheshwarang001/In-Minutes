package org.example.elasticsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.elasticsearch.entity.Product;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private UUID productId;

    private String productName;

    private String productDescription;

    private String productCategory;

    private int cost;

    private String productSubCategory;

    private String productSubProductCategory;

    private String manufacturerName;

    private boolean active;




    public Product getProduct(ProductDto productDto){

        return new Product(
                productDto.getProductId(),
                productDto.getProductName(),
                productDto.getProductDescription(),
                productDto.getProductCategory(),
                productDto.getCost(),
                productDto.getProductSubCategory(),
                productDto.getProductSubProductCategory(),
                productDto.getManufacturerName(),
                productDto.isActive()
        );

    }

}
