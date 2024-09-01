package org.example.cartservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Builder
public class Product {

    @Id
    private UUID productId;

    private String productName;

    private String productImage;

    @Version
    private long version;

    public Product(){

    }
}
