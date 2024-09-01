package com.swipe.apigateway.util;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtUtil {

    public static final String SECRET = "357538782F413F4428472B4B6250655368566D59703373367639792442264529W5P";



    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserId(String token){

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token);

        Claims claims = claimsJws.getBody();

        return (String) claims.get("user_id");

    }


    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);

            Claims claims = claimsJws.getBody();

            Date currentDate = new Date();
            Date issuedDate = claims.getIssuedAt();
            Date expirationDate = claims.getExpiration();

            // Check for specific claims
            String userIdClaim = (String) claims.get("user_id");
            String userNameClaim = (String) claims.get("email");

            if (userIdClaim == null || userNameClaim == null) {
                System.out.println("Missing user_id or user_name claim");
                return false;
            }

            // Additional validation logic based on your requirements

            return !currentDate.before(issuedDate) && !currentDate.after(expirationDate);
        } catch (ExpiredJwtException e) {
            // Token has expired
            System.out.println("Token has expired: " + e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            // Token is malformed or invalid
            System.out.println("Malformed or invalid token: " + e.getMessage());
            return false;
        } catch (Exception e) {
            // Other exceptions
            System.out.println("Error parsing or validating token: " + e.getMessage());
            return false;
        }
    }


}



