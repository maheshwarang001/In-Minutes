package org.example.finzo_auth_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.finzo_auth_service.model.UserCredential;
import org.example.finzo_auth_service.model.UserModelC;
import org.example.finzo_auth_service.service.AuthorizationService;
import org.example.finzo_auth_service.service.OtpService;
import org.example.finzo_auth_service.service.RegisterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/finzo_auth/v1")
@Slf4j
public class UserController implements Controller{


    @Autowired
    OtpService otpService;

    @Autowired
    RegisterUser registerUser;

    @Autowired
    AuthorizationService authorizationService;

    /**
     * Sign up Request
     * Requires email verification using OTP
     * @PARAM email takes users email ID
     * */

    @Override
    @GetMapping("/res/request-otp")
    public ResponseEntity<Boolean> requestOtp(@RequestParam String email) {
        boolean response = otpService.passStringToQueueForOTP(email, 1);
        if(response) return ResponseEntity.ok().body(true);
        else return ResponseEntity.badRequest().body(false);
    }

    @Override
    @GetMapping("/res/request-otp-signup")
    public ResponseEntity<Boolean> requestOtpSignUp(@RequestParam String email) {
        boolean response = otpService.passStringToQueueForOTP(email, 0);
        if(response) return ResponseEntity.ok().body(true);
        else return ResponseEntity.badRequest().body(false);
    }

    /**
     * Otp Verification
     * Requires email, opt , 6 digit pin & app name
     * */

    @Override
    @PostMapping("/res/register-user")
    public ResponseEntity<Map<String, String>> register(
            @RequestBody UserModelC userModelC,
            @RequestParam String app

    ) {

        Map<String, String> result = new HashMap<>();
        try {

            if (userModelC.getOtp() == null || userModelC.getOtp().isBlank() ||
                    userModelC.getEmail() == null || userModelC.getEmail().isBlank() ||
                    userModelC.getPin() == null || userModelC.getPin().isBlank()) {
                throw new NullPointerException();
            }

            registerUser.registerUser(userModelC,app.equals("1")?1:0);

            result.put("result", "true");
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            result.put("result", "false");
            return ResponseEntity.badRequest().body(result);
        }
    }

    @Override
    @PostMapping("/res/generate-token")
    public ResponseEntity<Map<String, String>> generateToken(@RequestBody UserCredential userCredential) {

        Map<String, String> result = new HashMap<>();
        try {

            Authentication authentication = authorizationService.authentication(userCredential);

            if(authentication.isAuthenticated()){

                String token = authorizationService.tokenGenerate(userCredential.getEmail());
                result.put("result", token);
                return ResponseEntity.ok().body(result);

            }else

                return ResponseEntity.notFound().build();

        } catch (Exception e) {
            log.error(e.toString());
            result.put("result", null);
            return ResponseEntity.badRequest().body(result);
        }
    }


}
