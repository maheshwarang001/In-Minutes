package org.example.finzo_auth_service.service;


import org.example.finzo_auth_service.config.jwt.JwtComponent;
import org.example.finzo_auth_service.dao.UserDao;
import org.example.finzo_auth_service.entity.Roles;
import org.example.finzo_auth_service.model.TokenInfo;
import org.example.finzo_auth_service.model.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserDao userDao;

    @Autowired
    JwtComponent jwtComponent;

    public Authentication authentication(UserCredential userCredential) throws Exception{

        return authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userCredential.getEmail(),
                                userCredential.getPin()
                        )
                );
    }

    public String tokenGenerate(String email) throws Exception{
        TokenInfo userDetails = userDao.getRole(email);
       return jwtComponent.generateToken(userDetails);
    }


}
