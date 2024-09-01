package org.example.customer.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "customer_account_date")
public class CustomerAccountDate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_account_date_uuid")
    private UUID customer_account_date_uuid;


    @Column(name = "doj", nullable = false)
    private LocalDate doj;

    @Column(name = "last_active")
    private LocalDate lastActive;
}
