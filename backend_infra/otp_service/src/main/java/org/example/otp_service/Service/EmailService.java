package org.example.otp_service.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String email, String otp){

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("Verification Code");
            mailMessage.setText("Dear finzo user, \nPlease Note your OTP below valid for 5 mins \n" + otp);
            mailMessage.setSentDate(new Date());
            mailSender.send(mailMessage);
        }catch (Exception e){
            log.error(e.toString());
        }
    }




}
