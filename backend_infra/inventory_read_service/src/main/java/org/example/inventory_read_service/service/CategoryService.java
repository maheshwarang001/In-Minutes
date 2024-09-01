package org.example.inventory_read_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_read_service.dao.CategoryDao;
import org.example.inventory_read_service.dto.CategoryDto;
import org.example.inventory_read_service.entity.inventory.category.Category;
import org.example.inventory_read_service.entity.inventory.category.CategoryEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;




    @Transactional
    public void updateCategory(CategoryDto categoryDto) throws  Exception{



        try {

            Category.validateCategoryUpdate(categoryDto);

            Category category = Category.fromCategoryDto(categoryDto);
            Optional<Category> existingCategoryOpt = categoryDao.provideCategoryById(category.getCategory_id());
            saveCategory(category,existingCategoryOpt);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private void saveCategory(Category category,Optional<Category> existingCategoryOpt) {

        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }


        if (existingCategoryOpt.isPresent()) {

            Category updateCategory = existingCategoryOpt.get();
            updateCategory.updateCategory(category);
            categoryDao.save(updateCategory);

        } else {
            throw new NoSuchElementException();
        }
    }

    @Transactional
    public void createCategory(CategoryDto categoryDto) throws Exception{
        try {
            Category.validateCategoryCreate(categoryDto);
            Category category = Category.fromCategoryDto(categoryDto);
            checkAndCreateCategory(category);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }


    private void checkAndCreateCategory(Category category) throws Exception{


        Optional<Category> existingCategoryOpt = categoryDao.provideCategoryById(category.getCategory_id());
        if(existingCategoryOpt.isPresent()){

            log.info("Category Exists -> {}" , "UPDATE");
            saveCategory(category,existingCategoryOpt);

        }else {

            categoryDao.save(category);

        }


    }

    @Transactional
    public void deleteCategory(UUID categoryId)throws Exception{
        try {
           categoryDao.deleteCategoryById(categoryId);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    public List<CategoryDto> readCategoryList(){
        try {

            List<Category> categories = categoryDao.getAllCategoryOrder();

            List<CategoryDto> categoryDto = categories.stream()
                    .map(category -> {
                        new CategoryDto();
                        return CategoryDto.getCategoryDtoFromCategory(category);
                    })
                    .toList();

            return categoryDto;

        }catch (Exception e){
            throw e;
        }
    }

    public Optional<Category> findCategoryById(UUID categoryId){
        return categoryDao.provideCategoryById(categoryId);
    }



}
