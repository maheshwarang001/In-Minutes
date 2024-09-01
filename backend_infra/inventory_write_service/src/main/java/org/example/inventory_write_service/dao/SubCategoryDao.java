package org.example.inventory_write_service.dao;

import org.example.inventory_write_service.entity.inventory.category.Category;
import org.example.inventory_write_service.entity.inventory.subcategory.SubCategory;
import org.example.inventory_write_service.dto.SubCategoryC;
import org.example.inventory_write_service.repository.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.FileAlreadyExistsException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
public class SubCategoryDao {

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public void saveSubCategory(SubCategory subCategory){
        subCategoryRepository.save(subCategory);
    }

    public void save(SubCategory subCategory){
        subCategoryRepository.save(subCategory);
    }

    public Optional<SubCategory> findSubCategoryByName(String subCategoryEnum){
        return subCategoryRepository.findBySubCategoryName(subCategoryEnum);
    }

    public Optional<SubCategory> findSubCategoryById(UUID id){
        return subCategoryRepository.findBySubCategoryId(id);
    }


    public int numberOfSubCategoryDependentOnCategory(UUID id){
        return subCategoryRepository.numberOfSubCategoriesDependOnCategory(id);
    }
    public void deleteSubCategory(UUID id){
        subCategoryRepository.deleteById(id);
    }



}
