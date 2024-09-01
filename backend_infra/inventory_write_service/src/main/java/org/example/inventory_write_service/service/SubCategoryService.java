package org.example.inventory_write_service.service;

import jakarta.ws.rs.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.example.inventory_write_service.Producer.KafkaMessageProducer;
import org.example.inventory_write_service.configuration.Topics;
import org.example.inventory_write_service.dao.CategoryDao;
import org.example.inventory_write_service.dao.SubCategoryDao;
import org.example.inventory_write_service.dao.SubProductCategoryDao;
import org.example.inventory_write_service.dto.SubCategoryDto;
import org.example.inventory_write_service.entity.inventory.category.Category;
import org.example.inventory_write_service.entity.inventory.subcategory.SubCategory;
import org.example.inventory_write_service.dto.Event;
import org.example.inventory_write_service.dto.EventType;
import org.example.inventory_write_service.dto.SubCategoryC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.example.inventory_write_service.service.InventoryService.serialize;

@Slf4j
@Service
public class SubCategoryService {

    final static String subCategoryEntityName = "sub-category";


    @Autowired
    private SubCategoryDao subCategoryDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private SubProductCategoryDao subProductCategoryDao;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;



    @Transactional
    public void createSubCategory(SubCategoryC subCategoryC){


        try {

            SubCategory subCategory = checkerBeforeCreating(subCategoryC);
            log.info(subCategory.getCategory().getCategory_image());


            //subCategoryDto

            SubCategoryDto subCategoryDto = new SubCategoryDto().generateSubCategoryDtoFromSubCategory(subCategory);

            log.info(subCategoryDto.toString());

            kafkaMessageProducer.sendMessage(
                    Event.builder()
                            .eventType(EventType.CREATE)
                            .entity(subCategoryEntityName)
                            .object(serialize(subCategoryDto))
                            .timeStamp(LocalDateTime.now())
                            .build()
                    ,
                    Topics.TOPIC_INVENTORY
            );
        }catch (Exception e){
            log.error(e.getMessage());
        }


    }

    private SubCategory checkerBeforeCreating(SubCategoryC subCategoryC)throws Exception{

        if(subCategoryC.getSubcategory_name() == null || subCategoryC.getSubcategory_name().isEmpty() || subCategoryC.getSubcategory_image() == null || subCategoryC.getSubcategory_image().isEmpty()) throw new NullPointerException();
        if(subCategoryDao.findSubCategoryByName(subCategoryC.getSubcategory_name()).isPresent())throw new FileAlreadyExistsException("Sub Category Already Exists");

        Optional<Category> category = categoryDao.provideCategoryId(subCategoryC.getCategoryId());

        if(category.isEmpty())throw new NoSuchElementException("Category Doesn't Exist");

        SubCategory subCategory = new SubCategory(
                subCategoryC.getSubcategory_name(),
                subCategoryC.getSubcategory_image(),
                subCategoryC.isSubcategory_active(),
                category.get()
        );

        subCategoryDao.save(subCategory);
        return subCategory;
    }


    @Transactional
    public void updateSubCategory(SubCategoryC subCategoryC, UUID subId)throws Exception{

        try {

            if(subId == null) throw new NullPointerException();

            Optional<SubCategory> subCategory = subCategoryDao.findSubCategoryById(subId);

            log.info(subCategory.isPresent() + " Log");



            if(subCategory.isPresent()){

                SubCategory existingSubCategory = subCategory.get();

                Optional<Category> newCategoryOpt = (subCategoryC.getCategoryId() != null) ? categoryService.findCategoryById(subCategoryC.getCategoryId()) : Optional.empty();


                existingSubCategory.updateSubCategory(subCategoryC,newCategoryOpt);


                subCategoryDao.saveSubCategory(subCategory.get());

                log.info(subCategory.get().toString());

                //subCategoryDto

                SubCategoryDto subCategoryDto = new SubCategoryDto().generateSubCategoryDtoFromSubCategory(subCategory.get());

                log.info(subCategoryDto.toString());



                kafkaMessageProducer.sendMessage(
                        Event.builder()
                                .eventType(EventType.UPDATE )
                                .entity(subCategoryEntityName)
                                .object(serialize(subCategoryDto))
                                .timeStamp(LocalDateTime.now())
                                .build()
                        ,
                        Topics.TOPIC_INVENTORY
                );

            }else{
                throw new NoSuchElementException("Invalid id");
            }

        }catch (Exception e){

            throw e;
        }


    }

    @Transactional
    public void deleteSubCategory(UUID subCategoryId)throws Exception{

        Optional<SubCategory> existSubCategory = subCategoryDao.findSubCategoryById(subCategoryId);

        if(existSubCategory.isPresent()){


            int count  = subProductCategoryDao.countDependencies(subCategoryId);

            if(count > 0){
                throw new ForbiddenException("Joint found");
            }
            else{

                subCategoryDao.deleteSubCategory(subCategoryId);

                byte [] s = serialize(subCategoryId);

                kafkaMessageProducer.sendMessage(
                        Event.builder()
                                .eventType(EventType.DELETE )
                                .entity(subCategoryEntityName)
                                .object(s)
                                .timeStamp(LocalDateTime.now())
                                .build()
                        ,
                        Topics.TOPIC_INVENTORY
                );
            }
        }
    }

    Optional<SubCategory> subCategoryFindById(UUID subCategoryId){
        return subCategoryDao.findSubCategoryById(subCategoryId);
    }



}
