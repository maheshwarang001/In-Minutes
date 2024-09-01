package org.example.inventory_read_service.service;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.example.inventory_read_service.dao.SubProductCategoryDao;
import org.example.inventory_read_service.dto.SubProductCategoryDto;
import org.example.inventory_read_service.entity.inventory.subProductCategory.SubProductCategory;
import org.example.inventory_read_service.entity.inventory.subcategory.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class SubProductCategoryService {

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private SubProductCategoryDao subProductCategoryDao;

    

    @Transactional
    public void createSubProductCategory(SubProductCategoryDto subProductCategoryDto)throws Exception{
        try {
            SubProductCategory subProductCategory = SubProductCategoryDto.generateSubProductCategoryFromDto(subProductCategoryDto);
            helperCreateSubProductCategory(subProductCategory);
        }catch (Exception e){
            throw e;
        }
    }

    public void helperCreateSubProductCategory(SubProductCategory subProductCategoryC) throws Exception{

        SubProductCategory.validateSubProductCategory(subProductCategoryC);

        Optional<SubProductCategory> existingSubProductCategory = subProductCategoryDao.findBySubProductCategoryId(subProductCategoryC.getSubproductcategory_id());

        if(existingSubProductCategory.isPresent()){
            helperUpdateSubProductCategory(subProductCategoryC,existingSubProductCategory);
            return;
        }

        Optional<SubCategory> subCategoryFind = subCategoryService.findSubCategoryById(subProductCategoryC.getSubcategory().getSubcategory_id());
        if(subCategoryFind.isEmpty()){
            subCategoryService.helperCreateSubCategory(subProductCategoryC.getSubcategory());
        }

        subProductCategoryDao.save(subProductCategoryC);

    }

    @Transactional
    public void updateSubProductCategory(SubProductCategoryDto  subProductCategoryDto)throws Exception{
        try {
            SubProductCategory subProductCategory = SubProductCategoryDto.generateSubProductCategoryFromDto(subProductCategoryDto);
            helperUpdateSubProductCategory(subProductCategory,subProductCategoryDao.findBySubProductCategoryId(subProductCategory.getSubproductcategory_id()));
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void deleteSubProductCategory(UUID subProductCategoryId)throws Exception{
        try {
            if(subProductCategoryId == null) throw new NullPointerException();
            subProductCategoryDao.deleteById(subProductCategoryId);
        }catch (Exception e){
            throw e;
        }
    }

    public void helperUpdateSubProductCategory(SubProductCategory subProductCategory,Optional<SubProductCategory> existingSubProductCategory)throws Exception{

        if(subProductCategory == null || subProductCategory.getSubproductcategory_id() == null) throw new NullPointerException();

        if(existingSubProductCategory.isPresent()){

            SubProductCategory newSubProductCategory = existingSubProductCategory.get();

            Optional<SubCategory> subCategoryOpt = (subProductCategory.getSubcategory() != null) ? subCategoryService.findSubCategoryById(subProductCategory.getSubcategory().getSubcategory_id()) : Optional.empty();

            newSubProductCategory.updateSubProductCategory(subProductCategory,subCategoryOpt);

            subProductCategoryDao.save(newSubProductCategory);


        }else{
            log.error("No Such subProductCategory found, creating...");
            helperCreateSubProductCategory(subProductCategory);
        }
    }
}
