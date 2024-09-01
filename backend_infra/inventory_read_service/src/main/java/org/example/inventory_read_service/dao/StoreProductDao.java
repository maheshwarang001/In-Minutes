package org.example.inventory_read_service.dao;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Store;
import org.example.inventory_read_service.entity.inventory.product.Product;
import org.example.inventory_read_service.entity.store.DarkStore;
import org.example.inventory_read_service.entity.store.StoreProduct;
import org.example.inventory_read_service.repository.StoreProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class StoreProductDao {

    @Autowired
    private StoreProductRepository storeProductRepository;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private DarkStoreDao darkStoreDao;


    public void saveStoreProduct( StoreProduct storeProduct){


    }


    public void addStoreFoSingleProduct(UUID productId, List<UUID> connectStoreId) throws Exception{

        Optional<StoreProduct> existingStoreProduct = storeProductRepository.getStoreProductByProductId(productId);
        if(existingStoreProduct.isEmpty())throw new NoSuchElementException();


        Set<UUID> existingConnectedStoreId = existingStoreProduct.stream()
                .map(storeProduct -> storeProduct.getStore().getStore_ID())
                .collect(Collectors.toSet());


        for (UUID uuid : connectStoreId) {

            if (!existingConnectedStoreId.contains(uuid)) {
                Optional<DarkStore> optionalDarkStore = darkStoreDao.findStoreById(uuid);
                if (optionalDarkStore.isEmpty()) throw new NoSuchElementException();

                existingStoreProduct.get().setStore(optionalDarkStore.get());
            }
        }

        storeProductRepository.save(existingStoreProduct.get());

    }



    public Set<UUID> setOfStoreIdForProduct(UUID productId){
        return storeProductRepository.getAllStoreIdForProduct(productId).get();
    }




    public void updateStoreWithAllInventoryIfNotExist(DarkStore darkStore) {
        try {


            if (darkStore == null ) {
                throw new NullPointerException("DarkStore is null");
            }

            if(darkStoreDao.findStoreById(darkStore.getStore_ID()).isEmpty()) throw new NoSuchElementException();

            Optional<Set<Product>> getProductsFromDarkStore = getAllProductFromDarkStore(darkStore);

            List<Product> getAllProductFromInventory = productDao.getAllProducts();

            if (getAllProductFromInventory.isEmpty()) {
                log.error("No products to add");
                return;
            }

            int defaultQty = 0;

//            if (getProductsFromDarkStore.isEmpty()) {
//                log.info(darkStore.getStore_ID() + " Product doesn't exist, creating from scratch");
//
//                for (Product product : getAllProductFromInventory) {
//                    updateItemToStore(darkStore,product,defaultQty);
//                }
//            } else {

                Set<Product> existingProducts = getProductsFromDarkStore.get();

                for (Product product : getAllProductFromInventory) {
                    if (!existingProducts.contains(product)) {
                        updateItemToStore(darkStore,product,defaultQty);
                    }
//                }

            }
        }catch (Exception e){
            log.error(e.getLocalizedMessage());
            throw e;
        }
    }

    private void updateItemToStore(DarkStore darkStore, Product product, int qty){
        StoreProduct storeProduct = new StoreProduct(darkStore, product, qty);
        storeProductRepository.save(storeProduct);
    }

    public void updateExistingProductToStoreQtyManualIntervention(UUID storeProductId , int updatedQty){
        Optional<StoreProduct> existingStoreProduct = storeProductRepository.getStoreProduct(storeProductId);

        if(existingStoreProduct.isPresent()){

            existingStoreProduct.get().setQuantity(updatedQty);
            storeProductRepository.save(existingStoreProduct.get());

        }
    }


    public void updateExistingProductToStoreQty(UUID storeProductId , int updatedQty){
        Optional<StoreProduct> existingStoreProduct = storeProductRepository.getStoreProduct(storeProductId);

        if(existingStoreProduct.isPresent()){

            if(existingStoreProduct.get().getQuantity()+updatedQty < 0) throw new InvalidParameterException();

            existingStoreProduct.get().setQuantity(existingStoreProduct.get().getQuantity()+updatedQty);

            storeProductRepository.save(existingStoreProduct.get());

        }
    }


    public void storeProduct(List<DarkStore> darkStore, Product product, int qty){
        for(DarkStore ds : darkStore) {
            updateItemToStore(ds,product,qty);
        }
    }

    public StoreProduct getProductFromDarkStore(UUID darkStoreId, UUID pid){

        Optional<StoreProduct> storeProduct = storeProductRepository.getProductByDarkStore(darkStoreId,pid);
        return storeProduct.orElse(null);
    }


    public Optional<Set<Product>> getAllProductFromDarkStore(DarkStore darkStore) {
        return storeProductRepository.getAllProductJointsFromDarkStore(darkStore);
    }
}
