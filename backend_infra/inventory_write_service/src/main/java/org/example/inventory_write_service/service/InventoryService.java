package org.example.inventory_write_service.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.inventory_write_service.Producer.KafkaMessageProducer;
import org.example.inventory_write_service.configuration.Topics;
import org.example.inventory_write_service.dao.*;
import org.example.inventory_write_service.dto.*;
import org.example.inventory_write_service.entity.inventory.category.Category;
import org.example.inventory_write_service.entity.inventory.category.CategoryEnum;
import org.example.inventory_write_service.entity.inventory.product.Manufacturer;
import org.example.inventory_write_service.entity.inventory.product.Product;
import org.example.inventory_write_service.entity.inventory.subProductCategory.SubProductCategory;
import org.example.inventory_write_service.entity.store.DarkStore;
import org.example.inventory_write_service.entity.store.StoreProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class InventoryService {


//    @Autowired
//    private CategoryDao categoryDao;
//
//    @Autowired
//    private SubCategoryDao subCategoryDao;
//
//    @Autowired
//    private ManufacturerDao manufacturerDao;
//
//    @Autowired
//    private ProductDao productDao;
//
//    @Autowired
//    private StoreProductDao storeProductDao;
//
//    @Autowired
//    private DarkStoreDao darkStoreDao;
//
//    @Autowired
//    private SubProductCategoryDao subProductCategoryDao;
//
//    @Autowired
//    private KafkaMessageProducer kafkaMessageProducer;

    public static byte[] serialize(final Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj).getBytes();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


//
//
//
//    @Transactional
//    public void createSubProductCategory(SubProductCategoryC subProductCategoryC){
//
//        try {
//
//            SubProductCategory subProductCategory = subProductCategoryDao.createSubProductCategory(subProductCategoryC);
//
//            kafkaMessageProducer.sendMessage(
//                    Event.builder()
//                            .eventType(EventType.CREATE )
//                            .entity("sub-product-category")
//                            .object(serialize(subProductCategory))
//                            .timeStamp(LocalDateTime.now())
//                            .build()
//                    ,
//                    Topics.TOPIC_INVENTORY
//            );
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }
//
//    }
//
//    @Transactional
//    public void createManufacturer(ManufacturerC manufacturerC){
//
//        try {
//
//            Manufacturer manufacturer = manufacturerDao.createManufacturer(manufacturerC);
//
//            kafkaMessageProducer.sendMessage(
//                    Event.builder()
//                            .eventType(EventType.CREATE )
//                            .entity("manufacturer")
//                            .object(serialize(manufacturer))
//                            .timeStamp(LocalDateTime.now())
//                            .build()
//                    ,
//                    Topics.TOPIC_INVENTORY
//            );
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }
//        //kafkaMessageProducer.sendMessage(new Event(EventType.CREATE, "manufacturer",  manufacturer),Topics.TOPIC_INVENTORY);
//    }
//
//
//    @Transactional
//    public void addItemToInventoryDb(ItemC itemC)  {
//
//        try {
//
//            Product product = productDao.addProductToInventory(itemC);
//
//            kafkaMessageProducer.sendMessage(
//                    Event.builder()
//                            .eventType(EventType.CREATE)
//                            .entity("product")
//                            .object(serialize(product))
//                            .timeStamp(LocalDateTime.now())
//                            .build()
//                    ,
//                    Topics.TOPIC_INVENTORY
//            );
//
//            List<DarkStore> darkStoreList = (darkStoreDao.getAllDarkStore());
//
//            if (!darkStoreList.isEmpty()) {
//                //storeProduct(darkStoreList, product, 0);
//            }
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }
//    }
//
////    private void storeProduct(List<DarkStore> darkStore, Product product, int qty){
////
////        for (DarkStore ds : darkStore) {
////            StoreProduct storeProduct = storeProductDao.updateItemToStore(ds, product, qty);
////
////            kafkaMessageProducer.sendMessage(
////                    Event.builder()
////                            .eventType(EventType.CREATE)
////                            .entity("storeProduct")
////                            .object(serialize(storeProduct))
////                            .timeStamp(LocalDateTime.now())
////                            .build()
////                    ,
////                    Topics.TOPIC_INVENTORY
////            );
////        }
////    }
//
//    public Optional<Category> getCategory(CategoryEnum categoryEnum){
//        return categoryDao.provideCategory(categoryEnum);
//    }


}
