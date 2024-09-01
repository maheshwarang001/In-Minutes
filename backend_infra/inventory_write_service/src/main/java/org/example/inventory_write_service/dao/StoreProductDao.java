package org.example.inventory_write_service.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_write_service.entity.inventory.product.Product;
import org.example.inventory_write_service.entity.store.DarkStore;
import org.example.inventory_write_service.entity.store.StoreProduct;
import org.example.inventory_write_service.repository.StoreProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Component
@Slf4j
public class StoreProductDao {

    @Autowired
    private StoreProductRepository storeProductRepository;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private DarkStoreDao darkStoreDao;



    public void save(StoreProduct storeProduct){
        storeProductRepository.save(storeProduct);
    }


    public Optional<StoreProduct> findByProductAndStore(UUID productId, UUID storeId){
        return storeProductRepository.findStoreProductByProductAndAndStore(productId,storeId);
    }


    public boolean checkIfDarkStoreContainsProduct(UUID darkStoreId , UUID productId){
        return storeProductRepository.findIfProductExistForDarkStore(productId,darkStoreId);
    }





    public Optional<Set<Product>> getAllProductFromDarkStore(DarkStore darkStore) {
        return storeProductRepository.getAllProductJointsFromDarkStore(darkStore);
    }
}
