package org.example.inventory_write_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    private EventType eventType;
    private String entity;
    private byte[] object;
    private LocalDateTime timeStamp;


}
