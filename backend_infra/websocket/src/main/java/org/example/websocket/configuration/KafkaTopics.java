package org.example.websocket.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopics {

    @Bean
    public NewTopic topicCategory(){
        return TopicBuilder.name("LOCATION_TOPIC").partitions(4).replicas(1).build();
    }


}
