package org.example.inventory_write_service.dto;

import lombok.Data;
import org.example.inventory_write_service.entity.inventory.category.CategoryEnum;

import java.util.UUID;

@Data
public class SubCategoryC {

    private String subcategory_name;
    private String subcategory_image;
    private boolean subcategory_active;
    private UUID categoryId;
}
