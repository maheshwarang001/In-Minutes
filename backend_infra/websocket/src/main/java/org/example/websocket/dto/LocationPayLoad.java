package org.example.websocket.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class LocationPayLoad implements Serializable {

    private String userId;
    private byte[] location;
    private LocalDateTime localDateTime;

    public LocationPayLoad(String userId, byte[] location, LocalDateTime localDateTime){
        this.userId = userId;
        this.location = location;
        this.localDateTime = localDateTime;
    }

}
