package org.example.cartservice.Producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.cartservice.configuration.Topics;
import org.example.cartservice.dto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class KafkaMessageProducer {

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    public void sendMessage(Event message, Topics topics){

        kafkaTemplate.send(topics.name(),message);
    }

    public static byte[] serialize(final Object obj) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(obj).getBytes();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
