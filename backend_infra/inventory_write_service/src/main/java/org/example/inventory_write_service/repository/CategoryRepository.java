package org.example.inventory_write_service.repository;

import org.example.inventory_write_service.entity.inventory.category.Category;
import org.example.inventory_write_service.entity.inventory.category.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, UUID> {


    @Query("SELECT ds FROM Category ds WHERE ds.category_name = :categoryName")
    Optional<Category> findByCategoryName(@Param("categoryName") CategoryEnum CategoryEnum);


    @Query("SELECT ds FROM Category ds WHERE ds.category_id = :categoryID")
    Optional<Category> findByCategoryId(@Param("categoryID") UUID categoryID);





}
