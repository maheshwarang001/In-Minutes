package org.example.finzo_auth_service.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    @Value("${rabbitmq.queues.queue1}")
    private String queue1;

    @Value("${rabbitmq.exchanges.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing.key1}")
    private String routingKey1;

    @Value("${rabbitmq.queues.queue2}")
    private String queue2;

//    @Value("${rabbitmq.exchanges.exchange2}")
//    private String exchange2;

    @Value("${rabbitmq.routing.key2}")
    private String routingKey2;

    @Bean
    public Queue queue1() {
        return new Queue(queue1);
    }

    @Bean
    public TopicExchange exchange1() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder
                .bind(queue1())
                .to(exchange1())
                .with(routingKey1);
    }

    @Bean
    public Queue queue2() {
        return new Queue(queue2);
    }

    @Bean
    public TopicExchange exchange2() {
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding binding2() {
        return BindingBuilder
                .bind(queue2())
                .to(exchange2())
                .with(routingKey2);
    }
}
