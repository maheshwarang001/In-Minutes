package org.example.customer.Repository;

import org.example.customer.Entity.CustomerCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface CustomerCredentialRepository extends JpaRepository<CustomerCredential, UUID> {

    boolean existsByEmail(String email);
}
