package org.example.inventory_write_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inventory_write_service.entity.inventory.category.Category;
import org.example.inventory_write_service.entity.inventory.category.CategoryEnum;


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


    public Category getCategoryFromCategoryDtoForUpdate(CategoryDto categoryDto){

        return Category
                .builder()
                .category_id(categoryDto.getCategoryId())
                .category_name(categoryDto.getCategoryName())
                .category_image(categoryDto.getCategoryImage())
                .active(categoryDto.isActive())
                .build();
    }

    public Category getCategoryFromCategoryDtoForCreation(CategoryDto categoryDto){

        return Category
                .builder()
                .category_name(categoryDto.getCategoryName())
                .category_image(categoryDto.getCategoryImage())
                .active(categoryDto.isActive())
                .build();
    }

    public CategoryDto generateCategoryDtoFromCategory(Category category){

        return CategoryDto
                .builder()
                .categoryId(category.getCategory_id())
                .categoryName(category.getCategory_name())
                .categoryImage(category.getCategory_image())
                .active(category.isActive())
                .build();
    }
}
