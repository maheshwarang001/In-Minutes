package org.example.inventory_write_service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SubProductCategoryC {
    private String subProductCategoryName;
    private String subProductCategoryImage;
    private boolean subProductCategoryActive;
    private UUID subCategoryId;

    public SubProductCategoryC(String subProductCategoryName,String subProductCategoryImage, boolean subProductCategoryActive, UUID subCategoryEnum) {
        this.subProductCategoryName = subProductCategoryName;
        this.subProductCategoryImage = subProductCategoryImage;
        this.subProductCategoryActive = subProductCategoryActive;
        this.subCategoryId = subCategoryEnum;
    }

    public static void validSubProductCategoryCreate(SubProductCategoryC subProductCategoryC){
        if(subProductCategoryC == null || subProductCategoryC.getSubProductCategoryName() == null || subProductCategoryC.getSubProductCategoryImage() == null || subProductCategoryC.getSubProductCategoryImage().isEmpty() ) throw new NullPointerException("Sub Product category");

    }



}
