package org.example.finzo_auth_service.entity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_activity")
public class UserActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "activity_uuid",updatable = false)
    private UUID activity_uuid;


    @Column(name = "date_of_joining",updatable = false,nullable = false)
    private LocalDateTime dateOfJoining;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

//    @Column(name = "actions")
//    private List<UserAction> actions = new ArrayList<>();

    @Column(name = "total_login")
    private int total_login;

    @Column(name = "total_purchase")
    private int total_purchase;

}
