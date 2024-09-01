package org.example.otp_service.Controller;

import org.example.otp_service.Service.ProducerOtp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/otp-service/v1")
public class MainController {

   @Autowired
    ProducerOtp producerOtp;


    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyOtp(@RequestParam("email")String email, @RequestParam("otp")String otp){
        boolean response = producerOtp.verifyOtpEmail(email,otp);
        if(response) return ResponseEntity.ok().body(true);
        else return ResponseEntity.badRequest().body(false);
    }

}
