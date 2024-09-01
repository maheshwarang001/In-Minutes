package org.example.customer.Controller;


import jakarta.ws.rs.POST;
import lombok.extern.slf4j.Slf4j;
import org.example.customer.Model.PinSetter;
import org.example.customer.Service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController()
@RequestMapping("/customer-service/v1")
public class MainController {

    @Autowired
    MainService mainService;

    /**
     * Endpoint to check the health of the service
     * */
    @GetMapping("/status")
    public String statusService(){
        return "true";
    }



    /**
     * Sign up Request
     * Requires email verification using OTP
     * @PARAM email takes users email ID
     * */

    @GetMapping("/email-verification")
    public ResponseEntity<Boolean> emailVerificationOTP(@RequestParam String email){
         boolean ans = mainService.passStringToQueueForOTP(email);
         return ResponseEntity.ok(ans);
    }

    /**
     * Otp Verification
     * Requires email, opt and 6 digit pin
     * returns jwt token if successful
     * */
    @PostMapping("/verify-otp-token")
    public ResponseEntity<String> otpVerification(@RequestBody PinSetter pinSetter){

        try{
            String token = mainService.verifyOtpAndGenerateToken(pinSetter);

            if(token == null || token.isEmpty()){
                return ResponseEntity.internalServerError().body("");
            }else{
                return ResponseEntity.ok().body(token);
            }
        }catch (Exception e){
            log.error(e.toString());
            return ResponseEntity.badRequest().body("false");
        }
    }

}
