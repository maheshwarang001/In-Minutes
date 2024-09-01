package org.example.inventory_write_service.repository;

import org.example.inventory_write_service.entity.inventory.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {


//    Optional<Product> findProductByProductName()

    @Query("SELECT p FROM Product p")
    List<Product> getAllProduct();




    @Query("SELECT COUNT(p) FROM Product p WHERE p.subProductCategory.subproductcategory_id = :sbId")
    int findSubProductCategoryDependency(@Param("sbId") UUID sbId);


    @Query("SELECT p FROM Product p WHERE p.product_id = :pId")
    Optional<Product> findProductById(@Param("pId")UUID pId);


}
