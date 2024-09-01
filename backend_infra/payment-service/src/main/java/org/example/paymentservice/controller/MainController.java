package org.example.paymentservice.controller;


import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;
import org.example.paymentservice.dto.CartOrderRequest;
import org.example.paymentservice.dto.CreatePaymentResponse;
import org.example.paymentservice.service.StripeService;
import org.example.paymentservice.service.StripeWebHook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment-service/v1")
public class MainController {


    @Autowired
    private StripeWebHook stripeWebHook;

    @Autowired
    private StripeService stripeService;


    @Value("${stripe.webHookSecret}")
    private String webhookSecret;



    @PostMapping("/payment-intent/stipe")
    public ResponseEntity<CreatePaymentResponse> getPaymentIntent(@RequestBody CartOrderRequest cartOrderRequest){
        try {

            CreatePaymentResponse ca = stripeService.paymentIntent(cartOrderRequest).get();
            return ResponseEntity.ok().body(ca);

        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }



    @PostMapping("stripe/event-listener")
    public String handleWebhookEvent(String payload, String sigHeader) {
        Event event = null;

        try {
            event = Webhook.constructEvent(
                    payload, sigHeader, webhookSecret
            );
        } catch (SignatureVerificationException e) {
            return "Webhook signature verification failed";
        }

        stripeWebHook.processEventAsync(event);

        return "Success";
    }



}
