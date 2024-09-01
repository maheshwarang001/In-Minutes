package org.example.cartuserservice.entity;

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


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public User(){

    }


}
