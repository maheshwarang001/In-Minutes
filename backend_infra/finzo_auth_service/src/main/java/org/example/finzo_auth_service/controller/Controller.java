package org.example.finzo_auth_service.controller;

import org.example.finzo_auth_service.model.UserCredential;
import org.example.finzo_auth_service.model.UserModelC;
import org.springframework.http.ResponseEntity;


import java.util.Map;

public interface Controller {


    ResponseEntity<Boolean> requestOtp(String email);

    ResponseEntity<Boolean> requestOtpSignUp(String email);

    ResponseEntity<Map<String, String>> register(UserModelC userModelC, String app);

    ResponseEntity<Map<String, String>> generateToken(UserCredential userCredential);
}
