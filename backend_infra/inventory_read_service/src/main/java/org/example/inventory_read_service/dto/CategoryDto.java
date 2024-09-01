package org.example.inventory_read_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inventory_read_service.entity.inventory.category.Category;
import org.example.inventory_read_service.entity.inventory.category.CategoryEnum;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {
    private UUID categoryId;
    private CategoryEnum categoryName;
    private String categoryImage;
    private boolean active;


    public static CategoryDto getCategoryDtoFromCategory(Category category){

        return CategoryDto
                .builder()
                .categoryId(category.getCategory_id())
                .categoryName(category.getCategory_name())
                .categoryImage(category.getCategory_image())
                .active(category.isActive())
                .build();
    }

}
