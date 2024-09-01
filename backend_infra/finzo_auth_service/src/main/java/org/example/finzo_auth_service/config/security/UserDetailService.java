package org.example.finzo_auth_service.config.security;

import org.example.finzo_auth_service.dao.UserDao;
import org.example.finzo_auth_service.model.UserCredential;
import org.example.finzo_auth_service.service.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailService implements UserDetailsService {

    @Autowired
    RegisterUser registerUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserCredential> data = Optional.ofNullable(
                registerUser.userCredential(username)
        );
        return data.map(UserDetailObject::new).orElseThrow(()->
                new UsernameNotFoundException("USER NOT FOUND"));
    }
}