package org.example.order_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Builder
public class Rider {

    @Id
    private UUID riderId;

    private String riderFirstName;

    private String riderLastName;

    private String riderPhoneNumber;

    public Rider() {

    }
}
