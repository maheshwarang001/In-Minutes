package org.example.cartuserservice.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.cartuserservice.entity.User;
import org.example.cartuserservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class UserDao {


    @Autowired
    private UserRepository userRepository;


    public void saveUser(User user){
        userRepository.save(user);
    }


    public User findUserById(UUID userId){
        return userRepository.findUserByUserId(userId);
    }
}
