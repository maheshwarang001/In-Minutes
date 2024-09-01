package org.example.inventory_read_service.repository;

import org.example.inventory_read_service.entity.inventory.product.Product;
import org.example.inventory_read_service.entity.store.DarkStore;
import org.example.inventory_read_service.entity.store.StoreProduct;
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


    @Query("SELECT ps FROM StoreProduct ps WHERE ps.product.product_id = : productId")
    Optional<StoreProduct> getStoreProductByProductId(@Param("productId") UUID productId);


    @Query("SELECT ps.store.store_ID FROM StoreProduct ps WHERE ps.product.product_id = : productId")
    Optional<Set<UUID>> getAllStoreIdForProduct(@Param("productId") UUID productId);

    @Query("SELECT ps FROM StoreProduct ps WHERE ps.storeProduct_id = : storeProductId")
    Optional<StoreProduct> getStoreProduct(@Param("storeProductId") UUID storeProductId);



    @Query("SELECT ps FROM StoreProduct  ps WHERE  ps.store.store_ID = :darkStoreId AND ps.product.product_id = :productId")
    Optional<StoreProduct> getProductByDarkStore(@Param("darkStoreId") UUID darkStoreId , @Param("productId") UUID productId );




}
