package org.example.inventory_read_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_read_service.dao.ManufacturerDao;
import org.example.inventory_read_service.dto.ManufacturerDto;
import org.example.inventory_read_service.entity.inventory.product.Manufacturer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ManufacturerService {

    @Autowired
    private ManufacturerDao manufacturerDao;


    @Transactional
    public void createManufacturer(ManufacturerDto manufacturerDto) throws Exception {
        try{
        Manufacturer manufacturer =  Manufacturer.generateManufacturerFromDto(manufacturerDto);
        helperCreateManufacturer(manufacturer);
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw  e;
        }
    }
    public void helperCreateManufacturer(Manufacturer manufacturerC) throws Exception{

        Manufacturer.validManufacturerCreate(manufacturerC);

        if(manufacturerDao.findManufacturerById(manufacturerC.getManufacturer_id()).isPresent()){
            helperUpdateManufacturer(manufacturerC);
        }else {
            Manufacturer manufacturer = new Manufacturer(manufacturerC.getManufacturer_id(),manufacturerC.getManufacturer_name(),manufacturerC.getManufacturer_address());
            manufacturerDao.saveManufacturer(manufacturer);
        }
    }


    @Transactional
    public void updateManufacturer(ManufacturerDto manufacturerDto) throws Exception{
        try{
            Manufacturer manufacturer =  Manufacturer.generateManufacturerFromDto(manufacturerDto);
            helperUpdateManufacturer(manufacturer);
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw  e;
        }
    }

    public void helperUpdateManufacturer(Manufacturer manufacturerC){

        if(manufacturerC == null || manufacturerC.getManufacturer_id() == null) throw new NullPointerException();

        Optional<Manufacturer> existingManufacturer =  manufacturerDao.findManufacturerById(manufacturerC.getManufacturer_id());

        if(existingManufacturer.isPresent()){

            Manufacturer newManufacturer = existingManufacturer.get();
            newManufacturer.updateManufacturer(manufacturerC);

            manufacturerDao.saveManufacturer(newManufacturer);
        }else{
            throw new NoSuchElementException();
        }

    }
    public void deleteManufacturer(UUID id)throws Exception{
        try{

        }
        catch (Exception e){
            throw e;
        }
    }

    public Optional<Manufacturer> findById(UUID id){
        return manufacturerDao.findManufacturerById(id);
    }
}
