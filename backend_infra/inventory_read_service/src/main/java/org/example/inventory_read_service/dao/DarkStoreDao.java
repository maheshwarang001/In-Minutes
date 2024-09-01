package org.example.inventory_read_service.dao;

import org.example.inventory_read_service.entity.store.DarkStore;
import org.example.inventory_read_service.repository.DarkStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class DarkStoreDao{

    @Autowired
    private DarkStoreRepository darkStoreRepository;




    public void saveDarkStore(DarkStore darkStore){
        darkStoreRepository.save(darkStore);
    }

    public Optional<DarkStore> findStoreByName(String storename) {
        return darkStoreRepository.findByStoreName(storename);
    }
    public Optional<DarkStore> findStoreById(UUID storeId) {
        return darkStoreRepository.findByStoreID(storeId);
    }

    public List<DarkStore> getAllDarkStore(){
        return darkStoreRepository.getAllDarkStore();
    }

    public void updateStoreIDWithLatestCatlog(UUID storeId){

        try {

            if(darkStoreRepository.findByStoreID(storeId).isEmpty()){
                throw new NoSuchElementException("Store not found");
            }

        }
        catch (Exception e){

        }

    }
}
