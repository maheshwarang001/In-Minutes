package org.example.inventory_read_service.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_read_service.dto.CategoryDto;
import org.example.inventory_read_service.entity.inventory.category.Category;
import org.example.inventory_read_service.entity.inventory.subcategory.SubCategory;
import org.example.inventory_read_service.repository.SubCategoryRepository;
import org.example.inventory_read_service.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class SubCategoryDao {


    @Autowired
    private SubCategoryRepository subCategoryRepository;


    public Optional<SubCategory> findSubCategory(UUID subcategoryId){
        return findSubCategoryById(subcategoryId);
    }

    public void delete(UUID id){
        subCategoryRepository.deleteById(id);
    }


    public void saveSubCategory(SubCategory subCategory){
        subCategoryRepository.save(subCategory);
    }

    public Optional<SubCategory> findSubCategoryById(UUID subCategoryId){
        return subCategoryRepository.findBySubCategoryId(subCategoryId);
    }

}
