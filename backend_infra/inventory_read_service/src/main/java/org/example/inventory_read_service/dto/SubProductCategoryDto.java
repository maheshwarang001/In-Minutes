package org.example.inventory_read_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.inventory_read_service.entity.inventory.subProductCategory.SubProductCategory;
import org.example.inventory_read_service.entity.inventory.subcategory.SubCategory;

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

    public static SubProductCategory generateSubProductCategoryFromDto(SubProductCategoryDto subProductCategoryDto){
        return SubProductCategory
                .builder()
                .subproductcategory_id(subProductCategoryDto.getSubProductCategoryId())
                .subproductcategory_name(subProductCategoryDto.getSubProductCategoryName().trim().toLowerCase())
                .subproductcategory_image(subProductCategoryDto.getSubProductCategoryImage())
                .subproductcategory_active(subProductCategoryDto.isSubProductCategoryActive())
                .subcategory(subProductCategoryDto.getSubCategory())
                .build();
    }

}
