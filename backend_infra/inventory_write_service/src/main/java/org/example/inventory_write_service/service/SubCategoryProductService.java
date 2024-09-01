package org.example.inventory_write_service.service;

import jakarta.ws.rs.ForbiddenException;
import lombok.extern.slf4j.Slf4j;
import org.example.inventory_write_service.Producer.KafkaMessageProducer;
import org.example.inventory_write_service.configuration.Topics;
import org.example.inventory_write_service.dao.ProductDao;
import org.example.inventory_write_service.dao.SubProductCategoryDao;
import org.example.inventory_write_service.dto.SubProductCategoryC;
import org.example.inventory_write_service.dto.SubProductCategoryDto;
import org.example.inventory_write_service.entity.inventory.subProductCategory.SubProductCategory;
import org.example.inventory_write_service.dto.Event;
import org.example.inventory_write_service.dto.EventType;
import org.example.inventory_write_service.entity.inventory.subcategory.SubCategory;
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
public class SubCategoryProductService {

    final static String subCategoryProductEntity = "sub-product-category";

    @Autowired
    private SubProductCategoryDao subProductCategoryDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private SubCategoryService subCategoryService;

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;



    @Transactional
    public void createSubProductCategory(SubProductCategoryC subProductCategoryC) throws Exception {

        try {

            SubProductCategory subProductCategory = helperCreateSubProductCategory(subProductCategoryC);

            SubProductCategoryDto subProductCategoryDto = SubProductCategoryDto.generateSubProductDtoFromObject(subProductCategory);

            kafkaMessageProducer.sendMessage(
                    Event.builder()
                            .eventType(EventType.CREATE )
                            .entity(subCategoryProductEntity)
                            .object(serialize(subProductCategoryDto))
                            .timeStamp(LocalDateTime.now())
                            .build()
                    ,
                    Topics.TOPIC_INVENTORY
            );
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }

    }

    private SubProductCategory helperCreateSubProductCategory(SubProductCategoryC subProductCategoryC) throws Exception{

        SubProductCategoryC.validSubProductCategoryCreate(subProductCategoryC);

        if(subProductCategoryDao.findByName(subProductCategoryC.getSubProductCategoryName()).isPresent()) throw new FileAlreadyExistsException("Already Exist");
        Optional<SubCategory> subCategoryFind = subCategoryService.subCategoryFindById(subProductCategoryC.getSubCategoryId());

        if(subCategoryFind.isEmpty())throw new NoSuchElementException("SubCategory Error");

        SubProductCategory subProductCategory = new SubProductCategory
                (
                        subProductCategoryC.getSubProductCategoryName(),
                        subProductCategoryC.getSubProductCategoryImage(),
                        subProductCategoryC.isSubProductCategoryActive(),
                        subCategoryFind.get()
                );

        subProductCategoryDao.save(subProductCategory);
        return subProductCategory;

    }

    @Transactional
    public void updateSubCategoryProduct(SubProductCategoryC subProductCategoryC , UUID subCategoryProductId)throws Exception{

        try {
            if(subCategoryProductId == null || subProductCategoryC == null) throw new NullPointerException();

            SubProductCategory subProductCategory = helperUpdateSubProductCategory(subProductCategoryC, subCategoryProductId);

            SubProductCategoryDto subProductCategoryDto =  SubProductCategoryDto.generateSubProductDtoFromObject(subProductCategory);

            kafkaMessageProducer.sendMessage(
                    Event.builder()
                            .eventType(EventType.UPDATE)
                            .entity(subCategoryProductEntity)
                            .object(serialize(subProductCategoryDto))
                            .timeStamp(LocalDateTime.now())
                            .build()
                    ,
                    Topics.TOPIC_INVENTORY
            );


        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    private SubProductCategory helperUpdateSubProductCategory(SubProductCategoryC subProductCategoryC , UUID subProductCategoryId) throws Exception {

        Optional<SubProductCategory> existingSubProductCategory = subProductCategoryDao.findSubProductCategoryByID(subProductCategoryId);

        if(existingSubProductCategory.isPresent()){


            SubProductCategory newSubProductCategory = existingSubProductCategory.get();

            Optional<SubCategory> newCategoryOpt = (subProductCategoryC.getSubCategoryId() != null) ? subCategoryService.subCategoryFindById(subProductCategoryC.getSubCategoryId()) : Optional.empty();

            newSubProductCategory.updateSubCategory(subProductCategoryC,newCategoryOpt);

            return existingSubProductCategory.get();
        }else{
            throw new NoSuchElementException();
        }

    }

    public void deleteSubProductCategory(UUID sbId){
        try {

            if (sbId == null) throw new NullPointerException();

            Optional<SubProductCategory> subProductCategory = subProductCategoryDao.findSubProductCategoryByID(sbId);

            if(subProductCategory.isPresent()){

                int count  = productDao.subProductCategoryDependency(sbId);

                if(count > 0){
                    throw new ForbiddenException("Dependency Found");
                }
                else{
                    subProductCategoryDao.deleteSubProductCategory(sbId);


                    kafkaMessageProducer.sendMessage(
                            Event.builder()
                                    .eventType(EventType.DELETE)
                                    .entity(subCategoryProductEntity)
                                    .object(serialize(sbId))
                                    .timeStamp(LocalDateTime.now())
                                    .build()
                            ,
                            Topics.TOPIC_INVENTORY
                    );
                }

            }else{
                throw new NoSuchElementException(     );
            }
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }
    public Optional<SubProductCategory> findSubProductCategoryById(UUID sbId){
        return subProductCategoryDao.findSubProductCategoryByID(sbId);
    }
}
