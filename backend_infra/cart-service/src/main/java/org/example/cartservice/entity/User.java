package org.example.cartservice.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Entity
@Builder
public class User {

    @Id
    private UUID userId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    private UUID allotDarkStore;

    @Version
    private long version;


    public User(){

    }
}
