package org.example.customer.Service;

import lombok.extern.slf4j.Slf4j;
import org.example.customer.Configuration.JwtComponent;
import org.example.customer.Dao.CustomerCredentialDao;
import org.example.customer.Model.PinSetter;
import org.example.customer.Model.UserReplica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@Slf4j
public class MainService implements CustomerService{


    @Autowired
    private FinzoRabbitMqProducer rabbitMqProducer;

    @Autowired
    private CustomerCredentialDao credentialDao;

    @Autowired
    private JwtComponent jwtComponent;


    @Override
    public boolean passStringToQueueForOTP(String email) {

        if( credentialDao.checkEmailExist(email) ||email == null || email.isEmpty() || !isValid(email)){
            log.error("passStringToQueueForOTP email found either null , empty or invalid ");
            return false;
        }

        StringBuilder str = new StringBuilder("emailservice " + email);
        rabbitMqProducer.sendMessageToQueue(str.toString());
        return true;
    }

    @Transactional
    @Override
    public String verifyOtpAndGenerateToken(PinSetter pinSetter) throws Exception {

        if(pinSetter == null || credentialDao.checkEmailExist(pinSetter.getEmail())   || pinSetter.getEmail().isEmpty() || pinSetter.getOtp().isEmpty() || pinSetter.getPin().isEmpty()){
            throw new NullPointerException();
        }

        boolean isOtpVerified = otpVerification(pinSetter.getEmail(), pinSetter.getOtp());

        if(!isOtpVerified) throw new Exception();

        UUID id = saveUser(pinSetter.getEmail(),pinSetter.getPin());
        passUserService(pinSetter.getEmail(),String.valueOf(id));

        return token(pinSetter.getEmail(), id);
    }

    private void passUserService(String email, String uuid){

        try {

            RestTemplate restTemplate = new RestTemplate();
            String url = UriComponentsBuilder.fromHttpUrl("http://localhost:8002/user-service/v1/user-init")
                    .toUriString();
            UserReplica body = new UserReplica(uuid,email);

            HttpEntity<UserReplica> requestEntity = new HttpEntity<>(body);

            // Send the POST request and receive the response
            ResponseEntity<Boolean> responseEntity = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    requestEntity,
                    Boolean.class
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


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


    private UUID saveUser(String email, String pin) throws Exception {
        try {
            return credentialDao.createUser(email,pin);
        }catch (Exception e){
            log.error(e.toString());
            throw e;
        }
    }

    private String token(String email, UUID id) {
        return jwtComponent.generateToken(String.valueOf(id),email);
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
