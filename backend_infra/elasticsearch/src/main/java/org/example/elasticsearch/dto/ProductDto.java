package org.example.elasticsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.elasticsearch.entity.Product;
import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {



    private String productName;

    private String productDescription;

    private String productCategory;

    private int cost;

    private String productSubCategory;

    private String productSubProductCategory;

    private String manufacturerName;

    private boolean active;

    private List<UUID> storeInfoList;


    public Product getProduct(ProductDto productDto){

        return new Product(

                productDto.getProductName(),
                productDto.getProductDescription(),
                productDto.getProductCategory(),
                productDto.cost,
                productDto.getProductSubCategory(),
                productDto.getProductSubProductCategory(),
                productDto.getManufacturerName(),
                productDto.isActive(),
                productDto.storeInfoList
        );

    }

}
