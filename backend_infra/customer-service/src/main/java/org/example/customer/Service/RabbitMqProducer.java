package org.example.customer.Service;


public interface RabbitMqProducer {

    void sendMessageToQueue(String message);

}
