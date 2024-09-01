package org.example.inventory_write_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inventory_write_service.entity.inventory.subProductCategory.SubProductCategory;
import org.example.inventory_write_service.entity.inventory.subcategory.SubCategory;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubProductCategoryDto {


    private UUID subProductCategoryId;

    private String subProductCategoryName;

    private String subProductCategoryImage;

    private boolean subProductCategoryActive;

    private SubCategory subCategory;


    public SubProductCategory generateSubProductCategoryFromDtoForCreation(SubProductCategoryDto subProductCategoryDto){
        return SubProductCategory
                .builder()
                .subproductcategory_name(subProductCategoryDto.getSubProductCategoryName())
                .subproductcategory_image(subProductCategoryDto.getSubProductCategoryImage())
                .subproductcategory_active(subProductCategoryDto.isSubProductCategoryActive())
                .subcategory(subProductCategoryDto.getSubCategory())
                .build();
    }


    public SubProductCategory generateSubProductCategoryFromDtoForUpdate(SubProductCategoryDto subProductCategoryDto){
        return SubProductCategory
                .builder()
                .subproductcategory_id(subProductCategoryDto.getSubProductCategoryId())
                .subproductcategory_name(subProductCategoryDto.getSubProductCategoryName())
                .subproductcategory_image(subProductCategoryDto.getSubProductCategoryImage())
                .subproductcategory_active(subProductCategoryDto.isSubProductCategoryActive())
                .subcategory(subProductCategoryDto.getSubCategory())
                .build();
    }

    public static  SubProductCategoryDto generateSubProductDtoFromObject(SubProductCategory subProductCategory){
        return SubProductCategoryDto
                .builder()
                .subProductCategoryId(subProductCategory.getSubproductcategory_id())
                .subProductCategoryName(subProductCategory.getSubproductcategory_name())
                .subProductCategoryImage(subProductCategory.getSubproductcategory_image())
                .subProductCategoryActive(subProductCategory.isSubproductcategory_active())
                .subCategory(subProductCategory.getSubcategory())
                .build();
    }

}
