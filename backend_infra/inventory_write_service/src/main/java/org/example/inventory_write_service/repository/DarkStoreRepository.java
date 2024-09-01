package org.example.inventory_write_service.repository;

import org.example.inventory_write_service.entity.inventory.product.Product;
import org.example.inventory_write_service.entity.store.DarkStore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface DarkStoreRepository extends JpaRepository<DarkStore, UUID>{


    @Query("SELECT ds FROM DarkStore ds WHERE ds.store_name = :storeName")
    Optional<DarkStore> findByStoreName(@Param("storeName") String storeName);

    @Query("SELECT ds FROM DarkStore ds WHERE ds.store_ID = :storeID ")
    Optional<DarkStore> findByStoreID(@Param("storeID") UUID storeID);


    @Query("SELECT ds FROM DarkStore ds")
    List<DarkStore> getAllDarkStore();


}
