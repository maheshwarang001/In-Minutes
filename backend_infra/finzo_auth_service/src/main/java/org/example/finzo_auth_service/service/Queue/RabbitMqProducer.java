package org.example.finzo_auth_service.service.Queue;


public interface RabbitMqProducer {

    void sendMessageToQueue1(String message);
    void sendMessageToQueue2(String message);
}
