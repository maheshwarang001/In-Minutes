package org.example.customer.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FinzoRabbitMqProducer implements RabbitMqProducer {


    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public FinzoRabbitMqProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMessageToQueue(String message) {
        log.info(String.format("Message sent to " + exchange + " -> %s", message ));
        rabbitTemplate.convertAndSend(exchange,routingKey,message);
    }
}
