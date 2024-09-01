package org.example.otp_service.Service;

import lombok.extern.slf4j.Slf4j;
import org.example.otp_service.Entity.RedisEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@Slf4j
public class ProducerOtp implements Otp{

    // Function to generate a One Time Password (OTP) using the secret key

    @Autowired
    RedisEntity redis;
    @Override
    public String generateOtp(String secretKey, int length) {

        String allowedCharacters = "0123456789";
        StringBuilder otp = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(allowedCharacters.length());
            otp.append(allowedCharacters.charAt(randomIndex));
        }

        return otp.toString();

    }

    // Function to generate a random secret key
    @Override
    public String generateSecretKey(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[16];

        secureRandom.nextBytes(bytes);
        StringBuilder secretKey = new StringBuilder();

        for (byte b : bytes)secretKey.append(String.format("%02x", b));

        return secretKey.toString();
    }

    public boolean verifyOtpEmail(String email, String otp){
        try{
            boolean check =  redis.redisCheck(email,otp);
            log.info("Redis otp -> {}",check);
            return check;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }
}
