package org.example.inventory_write_service.dto;

import lombok.Data;
import org.example.inventory_write_service.entity.inventory.category.CategoryEnum;

@Data
public class CategoryC {

    private CategoryEnum categoryEnum;
    private String categoryImage;

    private boolean active;

}
