package org.example.cartservice.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.cartservice.entity.User;
import org.example.cartservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class UserDao {


    @Autowired
    private UserRepository userRepository;


    public User findUserById(UUID userId){
        return userRepository.findUserById(userId);
    }

}
