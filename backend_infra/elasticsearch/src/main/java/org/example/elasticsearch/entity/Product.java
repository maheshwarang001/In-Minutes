package org.example.elasticsearch.entity;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Document(indexName = "product_index")
@NoArgsConstructor
public class Product implements Serializable {

    @Id
    @NotNull
    private UUID productId;

    private String productName;

    private String productDescription;

    private String productCategory;

    private int cost;

    private String productSubCategory;

    private String productSubProductCategory;

    private String manufacturerName;

    private boolean active;



    public Product(UUID productId, String productName, String productDescription, String productCategory, int cost, String productSubCategory, String productSubProductCategory, String manufacturerName, boolean active) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productCategory = productCategory;
        this.cost = cost;
        this.productSubCategory = productSubCategory;
        this.productSubProductCategory = productSubProductCategory;
        this.manufacturerName = manufacturerName;
        this.active = active;
    }
}
