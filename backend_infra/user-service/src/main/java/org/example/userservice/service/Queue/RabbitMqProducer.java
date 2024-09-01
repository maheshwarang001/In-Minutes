package org.example.userservice.service.Queue;


public interface RabbitMqProducer {

    void sendMessageToQueue(String message);

}
