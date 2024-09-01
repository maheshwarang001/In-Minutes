package org.example.customer.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "customer_credential")
public class CustomerCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_credential_uuid")
    private UUID customer_credential_uuid;

    @Column(name = "email" , nullable = false, unique = true, updatable = false)
    private String email;

    @Column(name = "pin")
    private String pin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_biometric_key" , referencedColumnName = "customer_biometric_uuid")
    private CustomerBiometric customerBiometric;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_details_key", referencedColumnName = "customer_details_uuid")
    private CustomerDetails customerDetails;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_account_date_key", referencedColumnName = "customer_account_date_uuid")
    private CustomerAccountDate customerAccountDate;
}
