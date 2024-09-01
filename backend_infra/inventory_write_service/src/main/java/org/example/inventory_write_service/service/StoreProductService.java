package org.example.inventory_write_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_write_service.dao.DarkStoreDao;
import org.example.inventory_write_service.dao.ProductDao;
import org.example.inventory_write_service.dao.StoreProductDao;
import org.example.inventory_write_service.entity.inventory.product.Product;
import org.example.inventory_write_service.entity.store.DarkStore;
import org.example.inventory_write_service.entity.store.StoreProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.*;

@Service
@Slf4j
public class StoreProductService {

    final static String storeProductEntity = "store-product";

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

    private void updateItemToStore(DarkStore darkStore, Product product, int qty, boolean inStock){
        StoreProduct storeProduct = new StoreProduct(darkStore, product, qty, inStock);
        storeProductDao.save(storeProduct);
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
