package org.example.order_service.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePaymentResponse {


    private String clientSecret;
    private String ephemeralKey;
    private String customerId;
    private String publishableKey;

    public CreatePaymentResponse(String clientSecret , String ephemeralKey, String customerId, String publishableKey){

        this.clientSecret = clientSecret;
        this.ephemeralKey = ephemeralKey;
        this.customerId = customerId;
        this.publishableKey = publishableKey;
    }
}
