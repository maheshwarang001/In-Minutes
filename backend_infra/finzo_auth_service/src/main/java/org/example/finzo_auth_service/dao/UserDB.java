package org.example.finzo_auth_service.dao;

import org.example.finzo_auth_service.entity.Roles;
import org.example.finzo_auth_service.entity.User;
import org.example.finzo_auth_service.model.TokenInfo;
import org.example.finzo_auth_service.model.UserCredential;

public interface UserDB {

    void save(User user);
    boolean existByEmail(String email);

    UserCredential userCredential(String email);

    User findByExist(String email);

    TokenInfo getRole(String email);

}
