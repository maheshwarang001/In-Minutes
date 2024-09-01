package org.example.inventory_write_service.dao;

import org.example.inventory_write_service.dto.ManufacturerDto;
import org.example.inventory_write_service.entity.inventory.product.Manufacturer;
import org.example.inventory_write_service.dto.ManufacturerC;
import org.example.inventory_write_service.repository.ManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.FileAlreadyExistsException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Component
public class ManufacturerDao {

    @Autowired
    private ManufacturerRepository manufacturerRepository;



    public Optional<Manufacturer> findManufacturerByName(String name){
        return manufacturerRepository.findManufacturerByManufacturer_name(name);
    }

    public Optional<Manufacturer> findManufacturerById(UUID id){
        return manufacturerRepository.findManufacturerByManufacturer_id(id);
    }

    public void saveManufacturer(Manufacturer manufacturer) {
        manufacturerRepository.save(manufacturer);
    }
}
