package org.example.inventory_write_service.dto;
import lombok.Data;

import java.util.UUID;

@Data
public class ItemC {

    private String productName;

    private String productPicture;

    private ItemDetail itemDetail;

    private ItemCostC itemCost;

    private UUID productManufacturer_id;

    private UUID subProductCategoryId;


}
