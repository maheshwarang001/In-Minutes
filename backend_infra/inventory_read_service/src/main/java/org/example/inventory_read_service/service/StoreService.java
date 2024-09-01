package org.example.inventory_read_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_read_service.dao.DarkStoreDao;
import org.example.inventory_read_service.dto.DarkStoreDto;
import org.example.inventory_read_service.entity.store.DarkStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class StoreService {


    @Autowired
    private DarkStoreDao darkStoreDao;

    public void createDarkStore(DarkStoreDto darkStoreDto)throws Exception{
        try{

            DarkStore storeC = DarkStore.generateDarkStoreByDto(darkStoreDto);

            if(storeC == null || storeC.getStore_name() == null || storeC.getStore_name().isEmpty() )throw new NullPointerException();

            Optional<DarkStore> existingStoreOpt = darkStoreDao.findStoreById(storeC.getStore_ID());

            if (existingStoreOpt.isPresent()) {
                DarkStore existingStore = existingStoreOpt.get();
                existingStore.setLatitude(storeC.getLatitude());
                existingStore.setLongitude(storeC.getLongitude());
                existingStore.setActive(storeC.isActive());

                darkStoreDao.saveDarkStore(existingStore);

            } else {
                // Save new store
                storeC.setDoj(LocalDateTime.now());
                darkStoreDao.saveDarkStore(storeC);
            }


        }catch (Exception e){
            throw  e;
        }
    }
}
