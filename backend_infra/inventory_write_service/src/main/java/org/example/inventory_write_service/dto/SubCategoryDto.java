package org.example.inventory_write_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inventory_write_service.entity.inventory.category.Category;
import org.example.inventory_write_service.entity.inventory.subcategory.SubCategory;

import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubCategoryDto {

    private UUID subcategoryId;


    private String subcategoryName;

    private String subcategoryImage;

    private boolean subcategoryActive;


    private Category category;

    public SubCategory generateSubCategoryFromDtoForCreation(SubCategoryDto subCategoryDto){
        return SubCategory
                .builder()
                .subcategory_name(subCategoryDto.getSubcategoryName())
                .subcategory_image(subCategoryDto.getSubcategoryImage())
                .subcategory_active(subCategoryDto.isSubcategoryActive())
                .category(subCategoryDto.getCategory())
                .build();
    }

    public SubCategory generateSubCategoryFromDtoForUpdate(SubCategoryDto subCategoryDto){
        return SubCategory
                .builder()
                .subcategory_id(subCategoryDto.getSubcategoryId())
                .subcategory_name(subCategoryDto.getSubcategoryName())
                .subcategory_image(subCategoryDto.getSubcategoryImage())
                .subcategory_active(subCategoryDto.isSubcategoryActive())
                .category(subCategoryDto.getCategory())
                .build();
    }

    public SubCategoryDto generateSubCategoryDtoFromSubCategory(SubCategory subCategory){
        return SubCategoryDto
                .builder()
                .subcategoryId(subCategory.getSubcategory_id())
                .subcategoryName(subCategory.getSubcategory_name())
                .subcategoryImage(subCategory.getSubcategory_image())
                .subcategoryActive(subCategory.isSubcategory_active())
                .category(subCategory.getCategory())
                .build();
    }

    @Override
    public String toString() {
        return "SubCategoryDto{" +
                "subcategoryId=" + subcategoryId +
                ", subcategoryName='" + subcategoryName + '\'' +
                ", subcategoryImage='" + subcategoryImage + '\'' +
                ", subcategoryActive=" + subcategoryActive +
                ", category=" + category +
                '}';
    }
}
