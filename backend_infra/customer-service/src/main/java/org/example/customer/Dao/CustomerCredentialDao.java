package org.example.customer.Dao;

import org.example.customer.Entity.CustomerAccountDate;
import org.example.customer.Entity.CustomerCredential;
import org.example.customer.Entity.CustomerDetails;
import org.example.customer.Repository.CustomerCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class CustomerCredentialDao implements CustomerCredentailInterface {

    @Autowired
    CustomerCredentialRepository repository;

    @Override
    public UUID createUser(String email, String pin) throws Exception {
        if(email == null || email.isEmpty() || pin == null ||  pin.length()!=6) throw new Exception("Invalid input");

        CustomerCredential customerCredential = new CustomerCredential();
        CustomerAccountDate accountDate = new CustomerAccountDate();
        accountDate.setDoj(LocalDate.now());
        accountDate.setLastActive(LocalDate.now());
        customerCredential.setEmail(email);
        customerCredential.setPin(pin);
        customerCredential.setCustomerAccountDate(accountDate);


        // Save CustomerCredential to the repository
        customerCredential = repository.save(customerCredential);

        // Return the UUID of the saved CustomerCredential
        return customerCredential.getCustomer_credential_uuid();

    }


    @Override
    public void updateUserWithBiometric(UUID id, byte[] fingerprint) throws Exception {

    }

    @Override
    public void updateUserWithDetails(UUID id, CustomerDetails customerDetails) throws Exception {

    }

    @Override
    public boolean checkEmailExist(String email) {

        //return repository.existsByEmail(email);
        return false;
    }
}
