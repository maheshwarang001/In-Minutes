package org.example.inventory_read_service.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_read_service.entity.inventory.category.Category;
import org.example.inventory_read_service.entity.inventory.category.CategoryEnum;
import org.example.inventory_read_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
@Slf4j
public class CategoryDao {

    @Autowired
    private CategoryRepository categoryRepository;


    public Optional<Category> provideCategoryByName(CategoryEnum category){

        return categoryRepository.findByCategoryName(category);

    }

    public void deleteCategoryById(UUID uuid){

        categoryRepository.deleteById(uuid);
    }

    public void save(Category category){
        categoryRepository.save(category);
    }


    public Optional<Category> provideCategoryById(UUID category_id){

       return categoryRepository.findByCategoryId(category_id);

    }

    public List<Category> getAllCategoryOrder(){
        return categoryRepository.getAllCategoryOrder();
    }



}
