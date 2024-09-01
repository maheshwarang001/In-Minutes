package org.example.inventory_write_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_write_service.Producer.KafkaMessageProducer;
import org.example.inventory_write_service.configuration.Topics;
import org.example.inventory_write_service.dao.DarkStoreDao;
import org.example.inventory_write_service.dao.ProductDao;
import org.example.inventory_write_service.dao.StoreProductDao;
import org.example.inventory_write_service.dto.DarkStoreDto;
import org.example.inventory_write_service.dto.Event;
import org.example.inventory_write_service.dto.EventType;
import org.example.inventory_write_service.entity.inventory.product.Product;
import org.example.inventory_write_service.entity.store.DarkStore;
import org.example.inventory_write_service.dto.StoreC;
import org.example.inventory_write_service.entity.store.StoreProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.DuplicateFormatFlagsException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.example.inventory_write_service.service.InventoryService.serialize;

@Service
@Slf4j
public class StoreService {

    private static final String storeEntityName = "store";
    @Autowired
    private DarkStoreDao darkStoreDao;

    @Autowired
    private StoreProductService storeProductService;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private StoreProductDao storeProductDao;

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;


    @Transactional
    public void createStoreService(StoreC storeC)throws Exception{
        DarkStore darkStore = helperDarkStoreCreate(storeC);
        DarkStoreDto darkStoreDto = DarkStore.generateDtoByObject(darkStore);

        kafkaMessageProducer.sendMessage(
                Event.builder()
                        .eventType(EventType.CREATE )
                        .entity(storeEntityName)
                        .object(serialize(darkStoreDto))
                        .timeStamp(LocalDateTime.now())
                        .build()
                ,
                Topics.TOPIC_INVENTORY
        );
    }
    public DarkStore helperDarkStoreCreate(StoreC storeC)throws Exception{
        try{

            if(storeC == null || storeC.getStore_name() == null || storeC.getStore_name().isEmpty() )throw new NullPointerException();
            if(darkStoreDao.findStoreByName(storeC.getStore_name()).isPresent()) throw new DuplicateFormatFlagsException("Already exist");

            DarkStore darkStore = new DarkStore();
            darkStore.setStore_name(storeC.getStore_name());
            darkStore.setLatitude(storeC.getLatitude());
            darkStore.setLongitude(storeC.getLongitude());
            darkStore.setDoj(LocalDateTime.now());
            darkStore.setActive(false);

            darkStoreDao.saveStore(darkStore);
            return darkStore;

        }catch (Exception e){
            throw  e;
        }
    }

    @Transactional
    public void updateStoreWithCatalog(UUID store_id)throws Exception{
        try {

            storeProductService.updateStoreWithAllInventory(store_id);
           // kafkaMessageProducer.sendMessage(new Event(EventType.UPDATE, "darkstore-inventory-all",  store_id),Topics.TOPIC_INVENTORY);


        }catch (Exception e){
            throw e;
        }
    }

    public void updateStoreWithSpecificCatalog(UUID storeId, UUID productId,int qty){
        try {

            storeProductService.updateInventorySpecificToDarkStoreFirstTime(storeId,productId,qty);
            // kafkaMessageProducer.sendMessage(new Event(EventType.UPDATE, "darkstore-inventory-all",  store_id),Topics.TOPIC_INVENTORY);


        }catch (Exception e){
            throw e;
        }

    }


}
