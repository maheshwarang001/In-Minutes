package org.example.finzo_auth_service.service;

import lombok.extern.slf4j.Slf4j;
import org.example.finzo_auth_service.dao.UserDao;
import org.example.finzo_auth_service.service.Queue.FinzoRabbitMqProducer;
import org.example.finzo_auth_service.service.Queue.RabbitMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
@Slf4j
public class OtpService {

    @Autowired
    FinzoRabbitMqProducer rabbitMqProducer;

    @Autowired
    UserDao userDao;

    public boolean passStringToQueueForOTP(String email, int i) {

        if( email == null || email.isEmpty() || !isValid(email)){
            log.error("passStringToQueueForOTP email found either null , empty or invalid ");
            return false;
        }

        if(i == 0 && userDao.existByEmail(email)){
            return false;
        }

        StringBuilder str = new StringBuilder("emailservice " + email);
        rabbitMqProducer.sendMessageToQueue1(str.toString());
        return true;
    }

    private static boolean isValid (String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}
