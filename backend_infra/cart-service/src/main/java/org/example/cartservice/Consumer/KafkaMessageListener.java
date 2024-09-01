package org.example.cartservice.Consumer;

import lombok.extern.slf4j.Slf4j;
import org.example.cartservice.dto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class KafkaMessageListener {


    @Autowired
    private MessageConsumer messageConsumer;



    @KafkaListener(id="cart-service-level", topics = "CART_TOPIC")
    public void listen(Event event) {

        log.info("Received event: {}",event.getTimeStamp());
        // Process the event
        try {

            Object object = event.getObject();
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
