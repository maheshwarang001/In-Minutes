package org.example.websocket.Producer;

import lombok.extern.slf4j.Slf4j;

import org.example.websocket.configuration.Topics;
import org.example.websocket.dto.LocationPayLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

@Component
@Slf4j
public class KafkaMessageProducer {

    @Autowired
    private KafkaTemplate<String, LocationPayLoad> kafkaTemplate;

    public void sendMessage(LocationPayLoad message, Topics topics){

        kafkaTemplate.send(topics.name(),message);
    }
}
