package org.example.inventory_write_service.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopics {

    @Bean
    public NewTopic topicCategory(){
        return TopicBuilder.name("TOPIC_INVENTORY").partitions(2).replicas(2).build();
    }



}
