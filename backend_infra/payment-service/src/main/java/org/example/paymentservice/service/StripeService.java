package org.example.paymentservice.service;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import com.stripe.param.EphemeralKeyCreateParams;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.example.paymentservice.dto.CartOrderRequest;
import org.example.paymentservice.dto.CreatePaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class StripeService {

    @Value("${stripe.apiKey}")
    private String stripeApikey;

    @Value("${stripe.publishableKey}")
    private String publishableKey;

    private final String successUrl = "http://localhost:9050/payment-made/orderId?success=true";
    private final String failureUrl = "http://localhost:9050/payment-made/orderId?success=false";

    public Customer createOrRetrieveCustomer(CartOrderRequest cartOrderRequest) throws StripeException {
        List<Customer> customers = Customer.list(CustomerListParams.builder()
                .setEmail(cartOrderRequest.getEmailId())
                .putAllExtraParam(Map.of("userId",cartOrderRequest.getUserId().toString()))
                .setLimit(1L)
                .build()).getData();

        Customer customer;
        if (!customers.isEmpty()) {
            customer = customers.get(0);
            log.info("Customer found: " + customer.getId());
        } else {

            CustomerCreateParams customerParams = CustomerCreateParams.builder()
                    .setEmail(cartOrderRequest.getEmailId())
                    .setName(cartOrderRequest.getFirstName() + " " + cartOrderRequest.getLastName())
                    .setMetadata(Map.of(
                            "userId", cartOrderRequest.getUserId().toString())
                    )
                    .setAddress(CustomerCreateParams.Address
                            .builder()
                            .setLine1(cartOrderRequest.getAddress().getAddress())
                            .setCity(cartOrderRequest.getAddress().getCity())
                            .setCountry("United Kingdom")
                            .setPostalCode(cartOrderRequest.getAddress().getPostcode())
                            .putAllExtraParam(Map.of(
                                    "addressId",String.valueOf(cartOrderRequest.getAddress().getAddressId()),
                                    "lat",String.valueOf(cartOrderRequest.getAddress().getLat()),
                                    "log",String.valueOf(cartOrderRequest.getAddress().getLog())
                            ))
                            .build())
                    .build();
            customer = Customer.create(customerParams);
            log.info("New customer created: " + customer.getId());

        }
        return customer;
    }

    @Async
    public CompletableFuture<CreatePaymentResponse> paymentIntent(CartOrderRequest cartOrderRequest) throws StripeException {

        return CompletableFuture.supplyAsync(() -> {
            Customer customer = null;
            try {
                customer = createOrRetrieveCustomer(cartOrderRequest);


            EphemeralKeyCreateParams ephemeralKeyParams =
                            EphemeralKeyCreateParams.builder()
                                    .setStripeVersion("v1")
                                    .setCustomer(customer.getId())
                                    .build();

                    EphemeralKey ephemeralKey = EphemeralKey.create(ephemeralKeyParams);

                    PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                            .setCustomer(customer.getId())
                            .setAmount((long) cartOrderRequest.getPayable())
                            .setCurrency("gbp")
                            .setDescription(cartOrderRequest.getUserId().toString() + " time: "+ LocalDateTime.now())
                            .setAutomaticPaymentMethods(PaymentIntentCreateParams.AutomaticPaymentMethods.builder().build())
                            .setOffSession(true)
                            .setReceiptEmail(cartOrderRequest.getEmailId())
                            .setShipping(

                                    PaymentIntentCreateParams
                                            .Shipping.builder()
                                            .setAddress(
                                                    PaymentIntentCreateParams
                                                            .Shipping
                                                            .Address
                                                            .builder()
                                                            .setLine1(cartOrderRequest.getAddress().getAddress())
                                                            .setCity(cartOrderRequest.getAddress().getCity())
                                                            .setCountry("United Kingdom")
                                                            .setPostalCode(cartOrderRequest.getAddress().getPostcode())
                                                            .putAllExtraParam(Map.of(
                                                                    "addressId",String.valueOf(cartOrderRequest.getAddress().getAddressId()),
                                                                    "lat",String.valueOf(cartOrderRequest.getAddress().getLat()),
                                                                    "log",String.valueOf(cartOrderRequest.getAddress().getLog())
                                                            )
                                                            )
                                                            .build()
                                            )
                                            .build()
                            )
                            .setConfirm(true)
                            .build();

                    PaymentIntent paymentIntent = PaymentIntent.create(params);

                    return new CreatePaymentResponse(
                            paymentIntent.getClientSecret(),
                            ephemeralKey.getId(),
                            customer.getId(),
                            publishableKey
                    );

            } catch (StripeException e) {
                throw new RuntimeException(e);
            }
        }
        );
    }
}
