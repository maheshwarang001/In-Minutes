package org.example.inventory_write_service.repository;

import org.example.inventory_write_service.entity.inventory.product.Product;
import org.example.inventory_write_service.entity.store.DarkStore;
import org.example.inventory_write_service.entity.store.StoreProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProduct, UUID> {

    @Query("SELECT ps.product FROM StoreProduct ps WHERE ps.store = :darkStore")
    Optional<Set<Product>> getAllProductJointsFromDarkStore(@Param("darkStore") DarkStore darkStore);


    @Query("SELECT ps FROM StoreProduct ps WHERE ps.store.store_ID = :darkStoreId AND ps.product.product_id = :productId")
    Optional<StoreProduct> findStoreProductByProductAndAndStore(@Param("productId")UUID productId , @Param("darkStoreId")UUID darkStoreId);

    @Query("SELECT COUNT(ps) > 0 FROM StoreProduct ps WHERE ps.store.store_ID = :darkStoreId AND ps.product.product_id = :productId")
    boolean findIfProductExistForDarkStore(@Param("productId")UUID productId , @Param("darkStoreId")UUID darkStoreId);

}
