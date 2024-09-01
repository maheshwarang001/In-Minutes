package org.example.websocket.Consumer;

import lombok.extern.slf4j.Slf4j;
import org.example.websocket.dto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class KafkaMessageListener {


    @Autowired
    private MessageConsumer messageConsumer;



    @KafkaListener(id="rider-service-level", topics = "RIDER_TOPIC")
    public void listen(Event event) {

        log.info("Received event: {}",event.getTimeStamp());
        // Process the event
        try {

            Object object = event.getMessage();
            if(object != null){

                messageConsumer.aggregate(event);

            }else{
                throw new NullPointerException();
            }

        }catch (Exception e){
            log.error(e.getMessage());
        }


    }





}
