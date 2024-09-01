package org.example.inventory_write_service.Producer;

import lombok.extern.slf4j.Slf4j;
import org.example.inventory_write_service.configuration.Topics;
import org.example.inventory_write_service.dto.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

@Component
@Slf4j
public class KafkaMessageProducer {

    @Autowired
    private KafkaTemplate<String, Event> kafkaTemplate;

    public void sendMessage(Event message, Topics topics){


        kafkaTemplate.send(topics.name(),message);
    }

    static byte[] serialize(final Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(obj);
            out.flush();
            return bos.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
