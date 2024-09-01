package org.example.customer.Configuration;

import java.security.Key;
import java.util.Map;

public interface Jwt {

    String generateToken  (String user_id, String email);
    String createToken(Map<String,Object> claims,String user_id, String email);

    Key getSignKey();
}
