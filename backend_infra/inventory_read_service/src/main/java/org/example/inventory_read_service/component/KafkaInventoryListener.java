package org.example.inventory_read_service.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.inventory_read_service.configuration.Topics;
import org.example.inventory_read_service.entity.inventory.subcategory.SubCategory;
import org.example.inventory_read_service.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Map;

@Component
@Slf4j
public class KafkaInventoryListener {

    @Autowired
    KafkaDistributorConsumer kafkaDistributorConsumer;


    @KafkaListener(id="inventory-service-level", topics = "TOPIC_INVENTORY"
            //properties = {"spring.json.value.default.type=org.example.inventory_read_service.model.Event"}

    )
    public void listen(Event event) {

        log.info("Received event: {}",event.getTimeStamp());
        // Process the event
        try {

            Object object = event.getObject();
            if(object!= null && event.getEntity() != null){
                kafkaDistributorConsumer.aggregate(event);
            }else{
                throw new NullPointerException();
            }

        }catch (Exception e){
            log.error(e.getMessage());
        }


    }





}
