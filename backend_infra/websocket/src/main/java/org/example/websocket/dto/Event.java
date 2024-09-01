package org.example.websocket.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event implements Serializable {

    private String name;

    private byte[] message;

    private LocalDateTime timeStamp;


}
