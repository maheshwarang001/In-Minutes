package org.example.inventory_write_service.dao;

import org.example.inventory_write_service.entity.store.DarkStore;
import org.example.inventory_write_service.dto.StoreC;
import org.example.inventory_write_service.repository.DarkStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class DarkStoreDao{

    @Autowired
    private DarkStoreRepository darkStoreRepository;



    public void saveStore(DarkStore darkStore){
        darkStoreRepository.save(darkStore);
    }

    public Optional<DarkStore> findStoreByName(String storeName) {
        return darkStoreRepository.findByStoreName(storeName);
    }
    public Optional<DarkStore> findStoreById(UUID storeId) {
        return darkStoreRepository.findByStoreID(storeId);
    }

    public List<DarkStore> getAllDarkStore(){
        return darkStoreRepository.getAllDarkStore();
    }

}
