package org.example.finzo_auth_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_uuid",unique = true)
    private UUID user_uuid;

    @Column(name = "email",unique = true,updatable = false, nullable = false)
    private String email;

    @Column(name = "pin", nullable = false)
    private String pin;

    @Column(name = "role",nullable = false)
    private Roles role;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_activity", referencedColumnName = "activity_uuid")
    private UserActivity userActivity;


}
