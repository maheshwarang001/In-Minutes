package org.example.paymentservice.service;

import com.stripe.model.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StripeWebHook {



    @Async
    public void processEventAsync(Event event) {
        switch (event.getType()) {
            case "payment_intent.succeeded":
                handlePaymentIntentSucceeded(event);
                break;
            case "payment_intent.failed":
                handlePaymentIntentFailed(event);
                break;
            // Add other cases as needed
            default:
                log.warn("Unhandled event type: {}", event.getType());
        }
    }
    private void handlePaymentIntentSucceeded(Event event) {
        log.info("Handled payment_intent.succeeded asynchronously.");
    }

    private void handlePaymentIntentFailed(Event event) {
        log.info("Handled payment_intent.failed asynchronously.");
    }



}
