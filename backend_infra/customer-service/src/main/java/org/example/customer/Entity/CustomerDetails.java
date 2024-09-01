package org.example.customer.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "customer_details")
public class CustomerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_details_uuid")
    private UUID customer_details_uuid;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "address",nullable = false)
    private String address;

    @Column(name = "phone",nullable = false,unique = true)
    private String phone;

    @Column(name = "dob",nullable = false,updatable = false)
    private LocalDate dob;


}
