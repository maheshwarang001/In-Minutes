package org.example.inventory_write_service.service;

import jakarta.ws.rs.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.example.inventory_write_service.Producer.KafkaMessageProducer;
import org.example.inventory_write_service.configuration.Topics;
import org.example.inventory_write_service.dao.CategoryDao;
import org.example.inventory_write_service.dao.SubCategoryDao;
import org.example.inventory_write_service.dto.CategoryC;
import org.example.inventory_write_service.dto.CategoryDto;
import org.example.inventory_write_service.entity.inventory.category.Category;
import org.example.inventory_write_service.dto.Event;
import org.example.inventory_write_service.dto.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.example.inventory_write_service.service.InventoryService.serialize;

@Service
@Slf4j
public class CategoryService {

    @Autowired
    private CategoryDao categoryDao;


    @Autowired
    private SubCategoryDao subCategoryDao;

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;



    @Transactional
    public void createCategory(CategoryC categoryC) throws Exception{

        try {

            //dto converted to category

            Category category =checkBeforeCreation(categoryC);

            //convert the category to dto

            CategoryDto conversion = new CategoryDto().generateCategoryDtoFromCategory(category);

            byte[] s = serialize(conversion);

            kafkaMessageProducer.sendMessage(
                    Event.builder()
                            .eventType(EventType.CREATE )
                            .entity("category")
                            .object(s)
                            .timeStamp(LocalDateTime.now())
                            .build()
                    ,
                    Topics.TOPIC_INVENTORY
            );

        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    private Category checkBeforeCreation(CategoryC categoryC) throws Exception {

        Category.validateCategoryBeforeCreation(categoryC);
        if(categoryDao.provideCategory(categoryC.getCategoryEnum()).isPresent()) throw new FileAlreadyExistsException("Category Exists");

        Category category = new Category(categoryC.getCategoryEnum(),categoryC.getCategoryImage(),categoryC.isActive());
        categoryDao.save(category);
        return category;
    }

    @Transactional
    public void updateCategory(CategoryDto categoryDto) throws  Exception{
        try {

            if(categoryDto.getCategoryId() == null) throw new NullPointerException();

            Optional<Category> categoryOptional = categoryDao.provideCategoryId(categoryDto.getCategoryId());

            log.info(categoryOptional.isEmpty() + " Ans");


            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
                category.updateCategory(categoryDto);
                categoryDao.save(category);


            kafkaMessageProducer.sendMessage(
                        Event.builder()
                                .eventType(EventType.UPDATE )
                                .entity("category")
                                .object(serialize(categoryDto))
                                .timeStamp(LocalDateTime.now())
                                .build()
                        ,
                        Topics.TOPIC_INVENTORY
                );
            }
        }catch (Exception e){
            throw  e;
        }

    }



    @Transactional
    public void deleteCategoryCondition(UUID categoryId){

        //only delete if no sub category is joint with category

        Optional<Category> categoryExist = categoryDao.provideCategoryId(categoryId);

        if(categoryExist.isPresent()){


            int count  = subCategoryDao.numberOfSubCategoryDependentOnCategory(categoryId);

            if(count > 0){
                throw new ForbiddenException("Cannot delete category with joints");
            }
            else{

                categoryDao.deleteByID(categoryId);

                byte [] s = serialize(categoryId);

                kafkaMessageProducer.sendMessage(
                        Event.builder()
                                .eventType(EventType.DELETE )
                                .entity("category")
                                .object(s)
                                .timeStamp(LocalDateTime.now())
                                .build()
                        ,
                        Topics.TOPIC_INVENTORY
                );

            }

        }else{
            throw new NoSuchElementException();
        }


    }


    Optional<Category> findCategoryById(UUID cId){
        return categoryDao.provideCategoryId(cId);
    }
}
