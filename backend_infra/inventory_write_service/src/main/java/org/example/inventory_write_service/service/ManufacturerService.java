package org.example.inventory_write_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_write_service.Producer.KafkaMessageProducer;
import org.example.inventory_write_service.configuration.Topics;
import org.example.inventory_write_service.dao.ManufacturerDao;
import org.example.inventory_write_service.dto.ManufacturerDto;
import org.example.inventory_write_service.entity.inventory.product.Manufacturer;
import org.example.inventory_write_service.dto.Event;
import org.example.inventory_write_service.dto.EventType;
import org.example.inventory_write_service.dto.ManufacturerC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.example.inventory_write_service.service.InventoryService.serialize;

@Service
@Slf4j
public class ManufacturerService {

    final static String manufacturerEntity = "manufacturer";


    @Autowired
    private ManufacturerDao manufacturerDao;

    @Autowired
    private KafkaMessageProducer kafkaMessageProducer;




    public void createManufacturer(ManufacturerC manufacturerC) throws Exception {
        try {

            Manufacturer manufacturer = helperCreateManufacturer(manufacturerC);

            ManufacturerDto manufacturerDto = ManufacturerDto.generateManufacturerDtoFromObject(manufacturer);

            kafkaMessageProducer.sendMessage(
                    Event.builder()
                            .eventType(EventType.CREATE )
                            .entity(manufacturerEntity)
                            .object(serialize(manufacturerDto))
                            .timeStamp(LocalDateTime.now())
                            .build()
                    ,
                    Topics.TOPIC_INVENTORY
            );


        }catch (Exception e){
            throw e;
        }
    }

    public Manufacturer helperCreateManufacturer(ManufacturerC manufacturerC) throws Exception{

        if(manufacturerC.getManufacturer_name() == null || manufacturerC.getManufacturer_address() == null || manufacturerC.getManufacturer_address().isEmpty() || manufacturerC.getManufacturer_name().isEmpty() ){
            throw new NullPointerException("Manufacturer");
        }

        if(manufacturerDao.findManufacturerByName(manufacturerC.getManufacturer_name()).isPresent()) throw new FileAlreadyExistsException("Already Exists");

        Manufacturer manufacturer = new Manufacturer(manufacturerC.getManufacturer_name(),manufacturerC.getManufacturer_address());
        manufacturerDao.saveManufacturer(manufacturer);
        return manufacturer;
    }

    public void updateManufacturer(ManufacturerDto manufacturerDto){
        try {

            Manufacturer manufacturer = helperUpdateManufacturer(manufacturerDto);

            manufacturerDto = ManufacturerDto.generateManufacturerDtoFromObject(manufacturer);

            kafkaMessageProducer.sendMessage(
                    Event.builder()
                            .eventType(EventType.UPDATE )
                            .entity(manufacturerEntity)
                            .object(serialize(manufacturerDto))
                            .timeStamp(LocalDateTime.now())
                            .build()
                    ,
                    Topics.TOPIC_INVENTORY
            );



        }catch (Exception e){
            throw e;
        }
    }

    public Manufacturer helperUpdateManufacturer(ManufacturerDto manufacturerDto){

        Optional<Manufacturer> existingManufacturer =  manufacturerDao.findManufacturerById(manufacturerDto.getManufacturerId());

        if(existingManufacturer.isPresent()){

            if(manufacturerDto.getManufacturerName() != null && !manufacturerDto.getManufacturerName().isEmpty()){
                existingManufacturer.get().setManufacturer_name(manufacturerDto.getManufacturerName());
            }
            if(manufacturerDto.getManufacturerAddress() != null && !manufacturerDto.getManufacturerAddress().isEmpty()){
                existingManufacturer.get().setManufacturer_address(manufacturerDto.getManufacturerAddress());
            }

            manufacturerDao.saveManufacturer(existingManufacturer.get());

            return existingManufacturer.get();
        }

        else throw new NoSuchElementException();

    }

    public Optional<Manufacturer> findManufactureById(UUID id){
        return manufacturerDao.findManufacturerById(id);
    }



}
