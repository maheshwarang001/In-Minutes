package org.example.customer.Entity;


import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "customer_biometric")
public class CustomerBiometric {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_biometric_uuid")
    private UUID customer_biometric_uuid;

    @Column(name = "fingerprint")
    private byte[] fingerprint;

}
