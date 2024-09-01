package org.example.finzo_auth_service.dao;

import lombok.extern.slf4j.Slf4j;
import org.example.finzo_auth_service.entity.Roles;
import org.example.finzo_auth_service.entity.User;
import org.example.finzo_auth_service.model.TokenInfo;
import org.example.finzo_auth_service.model.UserCredential;
import org.example.finzo_auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserDao implements UserDB{

    @Autowired
    UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserCredential userCredential(String email) {

        log.info(email);
        User user = findByExist(email);

        return new UserCredential(user.getEmail(),user.getPin());

    }

    @Override
    public User findByExist(String email) {
        return userRepository.findByEmail(email);
    }

    public TokenInfo getRole(String email){
        User u =  findByExist(email);
        return new TokenInfo(u.getUser_uuid(),u.getEmail(),u.getRole());
    }



}
