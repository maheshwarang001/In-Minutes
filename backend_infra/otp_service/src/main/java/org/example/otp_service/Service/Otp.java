package org.example.otp_service.Service;

public interface Otp {

    public String generateOtp(String secretKey, int length);

    public String generateSecretKey();
}
