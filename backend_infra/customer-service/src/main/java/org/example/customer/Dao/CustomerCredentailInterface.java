package org.example.customer.Dao;

import org.example.customer.Entity.CustomerDetails;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;
@NoRepositoryBean
public interface CustomerCredentailInterface {


    UUID createUser(String email, String pin) throws Exception;

    void updateUserWithBiometric(UUID id, byte[] fingerprint) throws Exception;

    void updateUserWithDetails(UUID id, CustomerDetails customerDetails)throws Exception;

    boolean checkEmailExist(String email);


}
