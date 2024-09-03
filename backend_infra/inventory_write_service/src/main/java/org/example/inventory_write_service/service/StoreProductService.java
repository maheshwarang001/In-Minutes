package org.example.inventory_write_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_write_service.Producer.KafkaMessageProducer;
import org.example.inventory_write_service.configuration.Topics;
import org.example.inventory_write_service.dao.DarkStoreDao;
import org.example.inventory_write_service.dao.ProductDao;
import org.example.inventory_write_service.dao.StoreProductDao;
import org.example.inventory_write_service.dto.DarkStoreProductDto;
import org.example.inventory_write_service.dto.Event;
import org.example.inventory_write_service.dto.EventType;
import org.example.inventory_write_service.entity.inventory.product.Product;
import org.example.inventory_write_service.entity.store.DarkStore;
import org.example.inventory_write_service.entity.store.StoreProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.*;

import static org.example.inventory_write_service.service.InventoryService.serialize;

@Service
@Slf4j
public class StoreProductService {

    final static String storeProductEntity = "store-product";

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;

    @Autowired
    private StoreProductDao storeProductDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private DarkStoreDao darkStoreDao;


    public void updateStoreWithAllInventory(UUID darkStoreId){

        if (darkStoreId == null) throw new NullPointerException();

        Optional<DarkStore> darkStoreQuery = darkStoreDao.findStoreById(darkStoreId);

        if(darkStoreQuery.isPresent()){
            updateStoreWithAllInventoryIfNotExistFirstTime(darkStoreQuery.get());
        }else throw new NoSuchElementException();

    }

    public void updateInventorySpecificToDarkStoreFirstTime(UUID darkStoreId, UUID productId, int qty){

        if (darkStoreId == null) throw new NullPointerException();
        if(productId == null) throw new NullPointerException();
        if(qty < 0) throw new InvalidParameterException();


        Optional<DarkStore> darkStoreQuery = darkStoreDao.findStoreById(darkStoreId);
        Optional<Product> productQuery = productDao.findProductById(productId);


        if(darkStoreQuery.isPresent() && productQuery.isPresent()){

            if(!storeProductDao.checkIfDarkStoreContainsProduct(darkStoreId,productId)) {

                boolean inStock = qty > 0;
                updateItemToStore(darkStoreQuery.get(), productQuery.get(), qty, inStock);

            }

        }else throw new NoSuchElementException();
    }


    private void updateStoreWithAllInventoryIfNotExistFirstTime(DarkStore darkStore) {
        try {


            if (darkStore == null) {
                throw new NullPointerException("DarkStore is null");
            }

            Optional<Set<Product>> getProductsFromDarkStore = storeProductDao.getAllProductFromDarkStore(darkStore);

            List<Product> getAllProductFromInventory = productDao.getAllProducts();

            if (getAllProductFromInventory.isEmpty()) {
                log.error("No products to add");
                return;
            }

            int defaultQty = 0;

            Set<Product> existingProducts = getProductsFromDarkStore.get();

            for (Product product : getAllProductFromInventory) {
                if (!existingProducts.contains(product)) {
                    updateItemToStore(darkStore,product,defaultQty,false);
                }
            }


        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Transactional
    protected void updateItemToStore(DarkStore darkStore, Product product, int qty, boolean inStock){
        StoreProduct storeProduct = new StoreProduct(darkStore, product, qty, inStock);
        storeProductDao.save(storeProduct);
        sendMessage(darkStore.getStore_ID(),product.getProduct_id(),qty);
    }

    private void sendMessage(UUID darkStoreId, UUID productId, int qty){

        byte[] s = serialize(
                DarkStoreProductDto
                        .builder()
                        .darkStore(darkStoreId)
                        .product(productId)
                        .qty(qty).build()
        );

        kafkaMessageProducer.sendMessage(
                Event.builder()
                        .eventType(EventType.CREATE )
                        .entity(storeProductEntity)
                        .object(s)
                        .timeStamp(LocalDateTime.now())
                        .build()
                ,
                Topics.TOPIC_INVENTORY
        );
    }
    public void updateQtyAddStock(UUID storeId, UUID productId, int qty) {
        try {

            Optional<StoreProduct> optionalStoreProduct = storeProductDao.findByProductAndStore(productId, storeId);

            if (optionalStoreProduct.isPresent()) {

                int currentQty = optionalStoreProduct.get().getQuantity();

                if (currentQty + qty >= 0) {
                    optionalStoreProduct.get().setQuantity(currentQty + qty);
                }else{
                    throw new InvalidParameterException();
                }

                storeProductDao.save(optionalStoreProduct.get());
            } else {
                throw new NoSuchElementException();
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public void updateQtyAfterTransaction(UUID storeId, UUID productId, int qty){
        try {

            Optional<StoreProduct> optionalStoreProduct = storeProductDao.findByProductAndStore(productId,storeId);

            if(optionalStoreProduct.isPresent()){

                int currentQty = optionalStoreProduct.get().getQuantity();


                if(currentQty - qty >= 0){
                    optionalStoreProduct.get().setQuantity(currentQty - qty);
                }
                else{
                    throw new InvalidParameterException();
                }

                storeProductDao.save(optionalStoreProduct.get());
            }else{
                throw new NoSuchElementException();

            }

        }catch (Exception e){
            throw e;
        }
    }



//    public void storeProduct(List<DarkStore> darkStore, Product product, int qty){
//        for(DarkStore ds : darkStore) {
//            StoreProduct storeProduct = updateItemToStore(ds,product,qty);
//
//        }
//    }

}
