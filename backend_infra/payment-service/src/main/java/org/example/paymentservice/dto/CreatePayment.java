package org.example.paymentservice.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePayment {

    CreatePaymentItem[] items;
}
