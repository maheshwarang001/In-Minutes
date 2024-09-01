package org.example.rider_service.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
public class Event implements Serializable {

    private String name;

    private byte[] message;

    private LocalDateTime timeStamp;


}
