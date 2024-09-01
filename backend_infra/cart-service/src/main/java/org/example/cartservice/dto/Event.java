package org.example.cartservice.dto;

import lombok.*;

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
