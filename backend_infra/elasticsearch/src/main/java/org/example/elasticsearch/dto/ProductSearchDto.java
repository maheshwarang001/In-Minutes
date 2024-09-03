package org.example.elasticsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchDto  {

    private UUID productId;

    private String productName;

    private String productDescription;

    private String productCategory;

    private double cost;

    private String productSubCategory;

    private String productSubProductCategory;

    private String manufacturerName;

    private boolean active;
}
