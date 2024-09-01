package org.example.inventory_write_service.repository;

import org.example.inventory_write_service.entity.inventory.product.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, UUID> {


    @Query("SELECT man FROM Manufacturer man WHERE man.manufacturer_name = :manufacturer_name")
    Optional<Manufacturer> findManufacturerByManufacturer_name(@Param("manufacturer_name") String manufacturer_name);

    @Query("SELECT man FROM Manufacturer man WHERE man.manufacturer_id = :manufacturer_id")
    Optional<Manufacturer> findManufacturerByManufacturer_id(@Param("manufacturer_id") UUID manufacturer_id);


}
