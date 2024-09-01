package org.example.userservice.service;

import lombok.extern.slf4j.Slf4j;
import org.example.userservice.model.UserReplica;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class QueueConsumer {

    @Autowired
    UserDetailService userDetailService;


    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void conumseFromAuth(String message){
        String[] divideMessage = message.split("\\s+");
        String email = divideMessage[1];
        String uuid = divideMessage[0];

        log.info("email -> {}" , email );
        log.info("uuid -> {}" , uuid );

        try {
            userDetailService.createUserInstance(new UserReplica(uuid, email));
        }catch (Exception e){
            log.error(e.toString());

        }

    }
}
