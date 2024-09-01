package org.example.inventory_read_service.model;


import lombok.*;
import org.springframework.core.serializer.Deserializer;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event implements Serializable {

    private EventType eventType;
    private String entity;
    private byte[] object;
    private LocalDateTime timeStamp;

}
