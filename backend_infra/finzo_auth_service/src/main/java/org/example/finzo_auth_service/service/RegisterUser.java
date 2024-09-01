package org.example.finzo_auth_service.service;


import lombok.extern.slf4j.Slf4j;
import org.example.finzo_auth_service.dao.UserDao;
import org.example.finzo_auth_service.entity.Roles;
import org.example.finzo_auth_service.entity.User;
import org.example.finzo_auth_service.entity.UserActivity;
import org.example.finzo_auth_service.model.UserCredential;
import org.example.finzo_auth_service.model.UserModelC;
import org.example.finzo_auth_service.model.UserReplica;
import org.example.finzo_auth_service.service.Queue.FinzoRabbitMqProducer;
import org.example.finzo_auth_service.service.Queue.RabbitMqProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;

@Slf4j
@Service
public class RegisterUser {

    @Autowired
    UserDao userDao;
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    FinzoRabbitMqProducer rabbitMqProducer;


    @Transactional
    public void registerUser(UserModelC userModelC, int r) throws Exception {

        //check the otp first

        boolean ans = otpVerification(userModelC.getEmail(),userModelC.getOtp());
        if(!ans) throw new Exception();


        User u = new User();
        u.setEmail(userModelC.getEmail());
        u.setPin(encoder.encode(userModelC.getPin()));
        u.setRole(r == 1 ? Roles.USER : Roles.CAPTAIN);
        UserActivity userActivity = new UserActivity();
        userActivity.setDateOfJoining(LocalDateTime.now());
        u.setUserActivity(userActivity);

        userDao.save(u);

        User user = userDao.findByExist(userModelC.getEmail());
        String format = user.getUser_uuid() + " " + user.getEmail();
        rabbitMqProducer.sendMessageToQueue2(format);



        //passUserService(u.getEmail(), String.valueOf(userDao.findByExist(u.getEmail()).getUser_uuid()));





    }

//    private void passUserService(String email, String uuid){
//
//        try {
//
//            RestTemplate restTemplate = new RestTemplate();
//            String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8030/user-service/v1/user-init")
//                    .toUriString();
//            UserReplica body = new UserReplica(uuid,email);
//
//            HttpEntity<UserReplica> requestEntity = new HttpEntity<>(body);
//
//            // Send the POST request and receive the response
//            void responseEntity = restTemplate.exchange(
//                    url,
//                    HttpMethod.POST,
//                    requestEntity,
//                    Boolean.class
//            );
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }




    private boolean otpVerification(String email, String otp) {
        try {

            RestTemplate restTemplate = new RestTemplate();
            String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8025/otp-service/v1/verify")
                    .queryParam("email", email)
                    .queryParam("otp", otp)
                    .toUriString();

            // Create an empty HttpEntity since no body is needed
            HttpEntity<Void> requestEntity = new HttpEntity<>(null);

            // Send the POST request and receive the response
            ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Boolean.class
            );

            return Boolean.TRUE.equals(responseEntity.getBody());

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserCredential userCredential(String email) {
        if (email.isEmpty()){
            log.error("email","empty");
        }
        log.info(email);
        User user = userDao.findByExist(email);

        return new UserCredential(user.getEmail(),user.getPin());

    }



}
