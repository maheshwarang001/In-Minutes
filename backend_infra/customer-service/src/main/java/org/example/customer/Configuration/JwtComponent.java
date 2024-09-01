package org.example.customer.Configuration;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtComponent implements Jwt{

    public static final String SECRET = "357538782F413F4428472B4B6250655368566D59703373367639792442264529W5P";

    long thirtyDaysInMillis = 30L * 24L * 60L * 60L * 1000L;
    @Override
    public String generateToken(String user_id, String email) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,user_id,email);
    }

    @Override
    public String createToken(Map<String, Object> claims, String user_id, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .claim("user_id",user_id)
                .claim("email", email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + thirtyDaysInMillis))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
