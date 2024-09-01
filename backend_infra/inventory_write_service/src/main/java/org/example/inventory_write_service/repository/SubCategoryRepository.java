package org.example.inventory_write_service.repository;

import org.example.inventory_write_service.entity.inventory.subcategory.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {

    @Query("SELECT sb FROM SubCategory sb WHERE sb.subcategory_name = :subCategoryName")
    Optional<SubCategory> findBySubCategoryName(@Param("subCategoryName") String subCategoryEnum);

    @Query("SELECT sb FROM SubCategory sb WHERE sb.subcategory_id = :subCategoryId")
    Optional<SubCategory> findBySubCategoryId(@Param("subCategoryId") UUID subCategoryId);

    @Query("SELECT COUNT(sb) FROM SubCategory sb WHERE sb.category.category_id = :categoryId")
    Integer numberOfSubCategoriesDependOnCategory(@Param("categoryId") UUID categoryId);


}
