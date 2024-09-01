package org.example.inventory_write_service.dao;

import org.example.inventory_write_service.dto.CategoryC;
import org.example.inventory_write_service.entity.inventory.category.Category;
import org.example.inventory_write_service.entity.inventory.category.CategoryEnum;
import org.example.inventory_write_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.FileAlreadyExistsException;
import java.util.Optional;
import java.util.UUID;

@Component
public class CategoryDao {

    @Autowired
    private CategoryRepository categoryRepository;



    public void save(Category category) throws  Exception{
        try {
            categoryRepository.save(category);
        }catch (Exception e){
            throw  e;
        }
    }

//    public Category createCategory(CategoryC categoryC) throws Exception{
//
//    }

    public Optional<Category> provideCategory(CategoryEnum category){

       return categoryRepository.findByCategoryName(category);

    }

    public Optional<Category> provideCategoryId(UUID  id){

        return categoryRepository.findByCategoryId(id);

    }

    public void deleteByID(UUID id){
        categoryRepository.deleteById(id);
    }

}
