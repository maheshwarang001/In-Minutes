package org.example.inventory_write_service.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_write_service.entity.inventory.subProductCategory.SubProductCategory;
import org.example.inventory_write_service.entity.inventory.subcategory.SubCategory;
import org.example.inventory_write_service.dto.SubProductCategoryC;
import org.example.inventory_write_service.repository.SubProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class SubProductCategoryDao {

    @Autowired
    private SubProductCategoryRepository subProductCategoryRepository;



    public Optional<SubProductCategory> findSubProductCategoryByID(UUID sbId){
        return subProductCategoryRepository.findSubProductCategoriesById(sbId);
    }


    public Optional<SubProductCategory> findByName(String subProductCategoryEnum){
        return subProductCategoryRepository.findSubProductCategoryByName(subProductCategoryEnum);
    }

    public int countDependencies(UUID subcategoryId){
        return subProductCategoryRepository.numberOfSubCategoryJoints(subcategoryId);
    }
    public void deleteSubProductCategory(UUID subProductCategoryId){
        subProductCategoryRepository.deleteById(subProductCategoryId);
    }
    public void save(SubProductCategory subProductCategory){
        subProductCategoryRepository.save(subProductCategory);
    }
}
