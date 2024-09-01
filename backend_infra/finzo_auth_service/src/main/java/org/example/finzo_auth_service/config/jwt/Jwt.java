package org.example.finzo_auth_service.config.jwt;

import org.example.finzo_auth_service.model.TokenInfo;

import java.security.Key;
import java.util.Map;

public interface Jwt {

    String generateToken  (TokenInfo tokenInfo);
    String createToken(Map<String,Object> claims,TokenInfo tokenInfo);

    Key getSignKey();
}
