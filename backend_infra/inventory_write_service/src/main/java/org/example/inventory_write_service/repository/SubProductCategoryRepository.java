package org.example.inventory_write_service.repository;

import org.example.inventory_write_service.entity.inventory.subProductCategory.SubProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubProductCategoryRepository extends JpaRepository<SubProductCategory, UUID> {

    @Query("SELECT sp FROM SubProductCategory sp WHERE sp.subproductcategory_name = :name")
    Optional<SubProductCategory> findSubProductCategoryByName(@Param("name")String name);

    @Query("SELECT sp FROM SubProductCategory  sp WHERE sp.subproductcategory_id = :id")
    Optional<SubProductCategory> findSubProductCategoriesById(@Param("id")UUID id);


    @Query("SELECT COUNT(sp) FROM SubProductCategory sp WHERE sp.subcategory.subcategory_id = :subcategoryId")
    int numberOfSubCategoryJoints(@Param("subcategoryId") UUID subcategoryId);

}
