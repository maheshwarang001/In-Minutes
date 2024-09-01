package org.example.inventory_read_service.repository;

import org.example.inventory_read_service.entity.inventory.subProductCategory.SubProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubProductCategoryRepository extends JpaRepository<SubProductCategory, UUID> {

    @Query("SELECT sp FROM SubProductCategory sp WHERE sp.subproductcategory_id = :id")
    Optional<SubProductCategory> findSubProductCategoryByName(@Param("id")UUID id);

}
