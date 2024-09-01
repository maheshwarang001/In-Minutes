package org.example.otp_service.Entity;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisEntity {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public boolean redisCallBack(String email, String otp) {
        if (email == null || otp == null || email.isEmpty() || otp.isEmpty()) {
            throw new IllegalArgumentException("Email or OTP cannot be null or empty");
        }

        try {

            if (redisTemplate.opsForValue().get(email) != null) {
                redisTemplate.delete(email);
            }

            // Set the TTL for Redis to 5 minutes
            redisTemplate.opsForValue().set(email, otp, 5, TimeUnit.MINUTES);

            String otpValue = redisTemplate.opsForValue().get(email);
            log.info("Redis OTP set -> {}", otpValue);

        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
        return true;
    }

    public boolean redisCheck(String email, String otp) {
        if (email == null || otp == null || email.isEmpty() || otp.isEmpty()) {
            throw new IllegalArgumentException("Email or OTP cannot be null or empty");
        }

        try {
            String storedOtp = redisTemplate.opsForValue().get(email);
            if (storedOtp == null || storedOtp.isEmpty()) {
                return false;
            } else {
                return storedOtp.equals(otp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
