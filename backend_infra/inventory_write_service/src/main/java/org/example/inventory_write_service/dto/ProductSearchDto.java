package org.example.inventory_write_service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
public class ProductSearchDto {

    private UUID productId;

    private String productName;

    private String productDescription;

    private String productCategory;

    private double cost;

    private String productSubCategory;

    private String productSubProductCategory;

    private String manufacturerName;

    private boolean active;

    @Override
    public String toString() {
        return "ProductSearchDto{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", cost=" + cost +
                ", productSubCategory='" + productSubCategory + '\'' +
                ", productSubProductCategory='" + productSubProductCategory + '\'' +
                ", manufacturerName='" + manufacturerName + '\'' +
                ", active=" + active +
                '}';
    }
}
