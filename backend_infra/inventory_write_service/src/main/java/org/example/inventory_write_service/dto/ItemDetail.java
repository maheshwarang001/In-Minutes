package org.example.inventory_write_service.dto;

import lombok.Data;

@Data
public class ItemDetail{

    private String productDescription;

    private String unit;

    private String metaData;

   private String productCountryOfOrigin;
}
