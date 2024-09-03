package org.example.elasticsearch.component;

import lombok.extern.slf4j.Slf4j;
import org.example.elasticsearch.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaInventoryListener {

    @Autowired
    KafkaDistributorConsumer kafkaDistributorConsumer;

    @KafkaListener(
            id = "inventory-service-level",
            topicPartitions = @TopicPartition(topic = "TOPIC_INVENTORY", partitions = {"2"})
           // properties = {"spring.json.value.default.type=org.example.inventory_write_service.dto.Event"}
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
