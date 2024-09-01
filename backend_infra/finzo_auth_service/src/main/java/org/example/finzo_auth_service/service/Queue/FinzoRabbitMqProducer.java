package org.example.finzo_auth_service.service.Queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class FinzoRabbitMqProducer implements RabbitMqProducer {

//    @Value("${rabbitmq.exchange.name1}")
//    private String exchange1;

    @Value("${rabbitmq.routing.key1}")
    private String routingKey1;

    @Value("${rabbitmq.exchanges.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing.key2}")
    private String routingKey2;

    private final RabbitTemplate rabbitTemplate;

    public FinzoRabbitMqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMessageToQueue1(String message) {
        log.info(String.format("Message sent to %s -> %s", exchange, message));
        rabbitTemplate.convertAndSend(exchange, routingKey1, message);
    }

    @Override
    public void sendMessageToQueue2(String message) {
        log.info(String.format("Message sent to %s -> %s", exchange, message));
        rabbitTemplate.convertAndSend(exchange, routingKey2, message);
    }
}
