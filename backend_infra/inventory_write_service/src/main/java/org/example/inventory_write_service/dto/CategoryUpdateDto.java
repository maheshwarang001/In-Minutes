package org.example.inventory_write_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inventory_write_service.entity.inventory.category.CategoryEnum;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateDto {


    private UUID categoryId;

    private CategoryEnum categoryName;
    private String categoryImage;

    private boolean active;





}
