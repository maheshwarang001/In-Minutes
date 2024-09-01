package org.example.inventory_read_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_read_service.dao.SubCategoryDao;
import org.example.inventory_read_service.dto.CategoryDto;
import org.example.inventory_read_service.dto.SubCategoryDto;
import org.example.inventory_read_service.entity.inventory.category.Category;
import org.example.inventory_read_service.entity.inventory.subcategory.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class SubCategoryService {


    @Autowired
    private SubCategoryDao subCategoryDao;

    @Autowired
    private CategoryService categoryService;

    @Transactional
    public void updateSubCategory(SubCategoryDto subCategoryDto){

        try {
            log.info(subCategoryDto.toString());

            if (subCategoryDto.getSubcategoryId() == null) throw new NullPointerException();

            SubCategory subCategory = SubCategoryDto.generateSubCategoryFromDto(subCategoryDto);

            updateSubCategory(subCategory);

        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private void updateSubCategory(SubCategory newSubCategoryData) {
        Optional<SubCategory> existingSubCategoryOpt = subCategoryDao.findSubCategoryById(newSubCategoryData.getSubcategory_id());

        if (existingSubCategoryOpt.isPresent()) {

            SubCategory existingSubCategory = existingSubCategoryOpt.get();

            Optional<Category> newCategoryOpt = (newSubCategoryData.getCategory() != null) ? categoryService.findCategoryById(newSubCategoryData.getCategory().getCategory_id()) : Optional.empty();

            existingSubCategory.updateSubCategory(newSubCategoryData, newCategoryOpt);

            subCategoryDao.saveSubCategory(existingSubCategory);
        } else {
            throw new NoSuchElementException("SubCategory not found");
        }
    }

    @Transactional
    public void createSubCategory(SubCategoryDto subCategoryDto)throws Exception{
        try {

            SubCategory.validSubCategoryCreate(subCategoryDto);

            SubCategory subCategory = SubCategoryDto.generateSubCategoryFromDto(subCategoryDto);
            log.info(subCategory.toString());
            helperCreateSubCategory(subCategory);

        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

     public void helperCreateSubCategory(SubCategory subCategoryC)throws Exception{

        try {

            Optional<SubCategory> existingSubCategory = subCategoryDao.findSubCategoryById(subCategoryC.getSubcategory_id());

            if (existingSubCategory.isPresent()) {
                log.info("SUB CATEGORY EXIST -> {}", "UPDATE");
                //updateSubCategory(subCategoryC, existingSubCategory);
                return;
            }

            Optional<Category> category = categoryService.findCategoryById(subCategoryC.getCategory().getCategory_id());

            if (category.isEmpty()) {

                categoryService.createCategory(CategoryDto.getCategoryDtoFromCategory(subCategoryC.getCategory()));

            } else {
                subCategoryDao.saveSubCategory(subCategoryC);
            }

        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public void deleteSubCategory(UUID subCategoryId){
        try {
            subCategoryDao.delete(subCategoryId);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    Optional<SubCategory > findSubCategoryById(UUID scId){
        return subCategoryDao.findSubCategoryById(scId);
    }

}
