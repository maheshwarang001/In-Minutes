package org.example.inventory_read_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_read_service.dao.StoreProductDao;
import org.example.inventory_read_service.dto.StoreProductDto;
import org.example.inventory_read_service.entity.store.StoreProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StoreProductService {

    @Autowired
    private StoreProductDao storeProductDao;

    public void createStoreProduct(StoreProductDto storeProductDto)throws Exception{
        StoreProduct storeProduct = StoreProductDto.generateStoreProductFromDto(storeProductDto);
        storeProductDao.saveStoreProduct(storeProduct);
    }
}
